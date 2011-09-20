package com.binomed.andengine;

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

public class MonActiviteDansUneVue extends BaseGameActivity implements IScrollDetectorListener, IOnSceneTouchListener, MovePeg {

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
	private Sprite[] holeSprite, holeLightSprite;
	private Sprite perso, persoTmp;
	private boolean onRectangle;
	private int currentHole = 0;

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
					if (peg.getX() < (hole.getX() + hole.getWidth())) {
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
				for (Sprite hole : holeSprite) {
					if (hole.getX() == peg.getX() && hole.getY() == peg.getY()) {
						peg.setVisible(false);
						if (persoTmp == null) {
							persoTmp = new Sprite(peg.getX(), peg.getY(), textureRegionPerso);
							scene.attachChild(persoTmp);
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
		holeSprite = new Sprite[2];
		Sprite hole = new Sprite(50, 50, textureRegionTrou);
		scene.attachChild(hole);
		holeSprite[0] = hole;
		hole = new Sprite(200, 50, textureRegionTrou);
		scene.attachChild(hole);
		holeSprite[1] = hole;

		holeLightSprite = new Sprite[2];
		hole = new Sprite(50, 50, textureRegionTrouLight);
		scene.attachChild(hole);
		hole.setVisible(false);
		holeLightSprite[0] = hole;
		hole = new Sprite(200, 50, textureRegionTrouLight);
		scene.attachChild(hole);
		hole.setVisible(false);
		holeLightSprite[1] = hole;

		// Initialisation de notre tank
		perso = new Sprite(MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100, textureRegionPerso);
		scene.attachChild(perso);
		peg = new Pion(MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100, textureRegionPersoAnimated);
		// Redimensionne notre tank
		// peg.setScale(0.5f);
		final PhysicsHandler physicsHandler = new PhysicsHandler(peg);
		peg.registerUpdateHandler(physicsHandler);
		scene.attachChild(peg);
		scene.registerTouchArea(peg);
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

	@Override
	public void animatePeg(final AnimatedSprite sprite, final float x, final float y) {
		this.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				sprite.clearEntityModifiers();
				// final float y = sprite.getY();
				// sprite.setPosition(x, y);
				sprite.registerEntityModifier(new MoveModifier(0.50f // time
						, sprite.getX(), x // x soucre x to
						, sprite.getY(), y // y source y to
						, EaseLinear.getInstance()//
				));
				sprite.animate(50, false);

				// sprite.setPosition(0, y);
				// sprite.registerEntityModifier(new MoveModifier(3, 0,
				// CAMERA_LARGEUR - sprite.getWidth(), y, y,
				// EaseLinear.getInstance()));

			}
		});
	}

	@Override
	public void pegMove(AnimatedSprite sprite) {
		if (persoTmp != null) {
			this.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					if (persoTmp != null) {
						scene.detachChild(persoTmp);
						persoTmp = null;
					}

				}
			});
		}

	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	class Pion extends AnimatedSprite {

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
			if (pSceneTouchEvent.isActionDown()) {
				setVisible(true);
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, (pSceneTouchEvent.getY() - this.getHeight() / 2) - 50);
				pegMove(this);
			} else if (pSceneTouchEvent.isActionMove()) {
				pegMove(this);
				setVisible(true);
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, (pSceneTouchEvent.getY() - this.getHeight() / 2) - 50);
			} else if (pSceneTouchEvent.isActionUp()) {
				if (onRectangle) {
					animatePeg(this, MonActiviteInteraction.CAMERA_LARGEUR / 2, MonActiviteInteraction.CAMERA_HAUTEUR - 100);
				} else {

					animatePeg(this, holeLightSprite[currentHole].getX(), holeLightSprite[currentHole].getY());

				}
			}
			return true;
		}

	}

}