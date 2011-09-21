package com.binomed.andengine;

import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

public class MonActiviteDansUneVue extends BaseGameActivity implements IScrollDetectorListener, IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	static final int CAMERA_LARGEUR = 320;
	static final int CAMERA_HAUTEUR = 480;
	static final float DEMO_VELOCITY = 100.0f;

	private static final String TAG = "AndEngineTest";

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera camera;
	private Scene scene;

	private BitmapTextureAtlas texture;
	private TextureRegion textureRegionTrou, textureRegionTrouLight, textureRegionPerso;
	private TiledTextureRegion textureRegionPersoAnimated;
	private Pion peg;
	private PhysicsHandler physicsHandler;
	private Rectangle rectangle;
	private StaticPion[] holeSprite, holeLightSprite;
	private Sprite perso;// , persoTmp;
	private boolean onRectangle;
	private int currentHole = 0;
	private HashMap<Long, StaticPion> mapPion = new HashMap<Long, MonActiviteDansUneVue.StaticPion>();
	private HashMap<Long, StaticPion> mapHole = new HashMap<Long, MonActiviteDansUneVue.StaticPion>();

	private IUpdateHandler updateHandler = new IUpdateHandler() {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			boolean rectangleTouch = rectangle.collidesWith(peg);
			boolean persoTouch = perso.collidesWith(peg);

			boolean holeTouch = false;
			for (Sprite hole : holeSprite) {
				holeTouch = hole.collidesWith(peg);
				if (holeTouch) {
					break;
				}
			}
			onRectangle = rectangleTouch;
			if (rectangleTouch) {
				float pRed = 0.5f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
				holeLightSprite[currentHole].setVisible(false);
			} else {
				float pRed = 0.0f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
				int i = 0;
				for (Sprite hole : holeSprite) {
					if (peg.isVisible() && peg.getX() < (hole.getX() + hole.getWidth())) {
						holeLightSprite[currentHole].setVisible(false);
						holeLightSprite[i].setVisible(true);
						currentHole = i;
						break;
					}
					i++;
				}
			}

			if (persoTouch && perso.getX() == peg.getX() && perso.getY() == peg.getY()) {
				peg.setVisible(false);
			}
			if (holeTouch) {
				for (StaticPion hole : holeSprite) {
					if (hole.getX() == peg.getX() && hole.getY() == peg.getY()) {
						peg.setVisible(false);
						if (peg.getID_Pion() == -1 && hole.getID() == -1) {
							StaticPion persoTmp = new StaticPion(peg.getX(), peg.getY(), textureRegionPerso);
							persoTmp.setID(System.currentTimeMillis());
							persoTmp.setSourcePeg(false);
							peg.setID_Pion(persoTmp.getID());
							hole.setID(persoTmp.getID());
							mapPion.put(persoTmp.getID(), persoTmp);
							mapHole.put(persoTmp.getID(), hole);
							scene.attachChild(persoTmp);
							scene.registerTouchArea(persoTmp);
						}
						break;
					}
				}
			}

		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter &amp; Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {

		camera = new Camera(0, 0, CAMERA_LARGEUR, CAMERA_HAUTEUR);
		return new Engine(new EngineOptions(true //
				, ScreenOrientation.PORTRAIT //
				, new RatioResolutionPolicy(CAMERA_LARGEUR, CAMERA_HAUTEUR) //
				, camera));

	}

	@Override
	public void onLoadResources() {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		texture = new BitmapTextureAtlas(1024, 128);

		textureRegionPersoAnimated = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, this, "soldier.png", 0, 0, 8, 1);
		textureRegionPerso = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, this, "personnage.png", 320, 0);
		textureRegionTrou = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, this, "blackhole.png", 352, 0);
		textureRegionTrouLight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, this, "blackhole_light.png", 480, 0);

		getEngine().getTextureManager().loadTexture(texture);

	}

	@Override
	public Scene onLoadScene() {
		scene = new Scene();
		scene.setBackground(new ColorBackground(0.52f, 0.75f, 0.03f));
		// Chargement d'un rectangle
		rectangle = new Rectangle(20, MonActiviteInteraction.CAMERA_HAUTEUR - 100, MonActiviteInteraction.CAMERA_LARGEUR - 50, 100);

		float pRed = 0.0f;
		float pGreen = 0.5f;
		float pBlue = 0.5f;
		float pAlpha = 1.0f;
		rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
		scene.attachChild(rectangle);

		// Chargement d'une image
		holeSprite = new StaticPion[2];
		StaticPion hole = new StaticPion(50, 50, textureRegionTrou);
		scene.attachChild(hole);
		holeSprite[0] = hole;
		hole = new StaticPion(200, 50, textureRegionTrou);
		scene.attachChild(hole);
		holeSprite[1] = hole;

		holeLightSprite = new StaticPion[2];
		hole = new StaticPion(50, 50, textureRegionTrouLight);
		scene.attachChild(hole);
		hole.setVisible(false);
		holeLightSprite[0] = hole;
		hole = new StaticPion(200, 50, textureRegionTrouLight);
		scene.attachChild(hole);
		hole.setVisible(false);
		holeLightSprite[1] = hole;

		// Initialisation de notre tank
		perso = new StaticPion(MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100, textureRegionPerso);
		scene.attachChild(perso);
		peg = new Pion(MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100, textureRegionPersoAnimated);
		// Redimensionne notre tank
		// peg.setScale(0.5f);
		final PhysicsHandler physicsHandler = new PhysicsHandler(peg);
		peg.registerUpdateHandler(physicsHandler);
		scene.attachChild(peg);
		// scene.registerTouchArea(peg);
		scene.registerTouchArea(perso);
		peg.setVisible(false);

		scene.registerUpdateHandler(updateHandler);
		scene.setTouchAreaBindingEnabled(true);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return true;
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
		// this.mCamera.offsetCenter(-pDistanceX, -pDistanceY);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void animatePeg(final AnimatedSprite sprite, final float x, final float y) {
		this.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				sprite.clearEntityModifiers();
				sprite.registerEntityModifier(new MoveModifier(0.50f // time
						, sprite.getX(), x // x soucre x to
						, sprite.getY(), y // y source y to
						, EaseLinear.getInstance()//
				));
				sprite.animate(50, false);

			}
		});
	}

	public void pegMove(final StaticPion sprite) {

		this.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				if (mapPion.containsKey(sprite.getID())) {
					peg.setID_Pion(-1l);
					mapPion.remove(sprite.getID());
					mapHole.get(sprite.getID()).setID(-1);
					mapHole.remove(sprite.getID());
					scene.unregisterTouchArea(sprite);
					scene.detachChild(sprite);
				}
			}
		});

	}

	public void delegateOnAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
			peg.setID_Pion(-1);
			peg.setVisible(true);
			peg.setPosition(pSceneTouchEvent.getX() - peg.getWidth() / 2, (pSceneTouchEvent.getY() - peg.getHeight() / 2) - 50);
		} else if (pSceneTouchEvent.isActionUp()) {
			if (onRectangle) {
				animatePeg(peg, MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100);
			} else {
				animatePeg(peg, holeLightSprite[currentHole].getX(), holeLightSprite[currentHole].getY());

			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	class Pion extends AnimatedSprite {

		private long ID_Pion = -1l;

		public long getID_Pion() {
			return ID_Pion;
		}

		public void setID_Pion(long iD_Pion) {
			ID_Pion = iD_Pion;
		}

		public Pion(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
			super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion, pRectangleVertexBuffer);
		}

		public Pion(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion) {
			super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		}

		public Pion(float pX, float pY, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
			super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		}

		public Pion(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
			super(pX, pY, pTiledTextureRegion);
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			delegateOnAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			return true;
		}

	}

	class StaticPion extends Sprite {

		private boolean sourcePeg = true;
		private long ID = -1;

		public void setSourcePeg(boolean sourcePeg) {
			this.sourcePeg = sourcePeg;
		}

		public long getID() {
			return ID;
		}

		public void setID(long iD) {
			ID = iD;
		}

		public StaticPion(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
			super(pX, pY, pWidth, pHeight, pTextureRegion, pRectangleVertexBuffer);
		}

		public StaticPion(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion) {
			super(pX, pY, pWidth, pHeight, pTextureRegion);
		}

		public StaticPion(float pX, float pY, TextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
			super(pX, pY, pTextureRegion, pRectangleVertexBuffer);
		}

		public StaticPion(float pX, float pY, TextureRegion pTextureRegion) {
			super(pX, pY, pTextureRegion);
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (pSceneTouchEvent.isActionDown() && !sourcePeg) {
				pegMove(this);
			}
			delegateOnAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			return true;
		}

	}

}