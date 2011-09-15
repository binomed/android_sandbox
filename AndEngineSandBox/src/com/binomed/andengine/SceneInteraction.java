package com.binomed.andengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

public class SceneInteraction extends Scene {

	private BitmapTextureAtlas textureTrou, textureTrouLight,
			texturePersoAnimated, texturePerso;
	private TextureRegion textureRegionTrou, textureRegionTrouLight,
			textureRegionPerso;
	private TiledTextureRegion textureRegionPersoAnimated;
	private Peg peg;
	private PhysicsHandler physicsHandler;
	private Rectangle rectangle;
	private Sprite holeSprite, holeLightSprite, perso, persoTmp;

	private MovePeg callBack;
	private IUpdateHandler updateHandler = new IUpdateHandler() {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			boolean rectangleTouch = rectangle.collidesWith(peg);
			boolean persoTouch = perso.collidesWith(peg);
			boolean holeTouch = holeSprite.collidesWith(peg);
			peg.setOnRectangle(rectangleTouch);
			if (rectangleTouch) {
				float pRed = 0.5f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
				holeLightSprite.setVisible(false);
			} else {
				float pRed = 0.0f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
				holeLightSprite.setVisible(true);
			}

			if (persoTouch && perso.getX() == peg.getX()
					&& perso.getY() == peg.getY()) {
				peg.setVisible(false);
			}
			if (holeTouch && holeSprite.getX() == peg.getX()
					&& holeSprite.getY() == peg.getY()) {
				peg.setVisible(false);
				persoTmp = new Sprite(peg.getX(), peg.getY(),
						textureRegionPerso);
				attachChild(persoTmp);
			}

		}
	};

	public SceneInteraction(MovePeg callBack) {
		super();
		this.callBack = callBack;
		setBackground(new ColorBackground(0.52f, 0.75f, 0.03f));
	}

	public void loadResources(final Engine engine, Context context) {
		texturePersoAnimated = new BitmapTextureAtlas(512, 64);
		texturePerso = new BitmapTextureAtlas(512, 64);
		textureTrou = new BitmapTextureAtlas(128, 128);
		textureTrouLight = new BitmapTextureAtlas(128, 128);
		textureRegionPersoAnimated = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(texturePersoAnimated, context,
						"soldier.png", 0, 0, 8, 1);
		textureRegionPerso = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(texturePerso, context, "personnage.png", 64, 0);
		textureRegionTrou = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(textureTrou, context, "blackhole.png", 0, 0);
		textureRegionTrouLight = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(textureTrouLight, context,
						"blackhole_light.png", 0, 0);

		engine.getTextureManager().loadTexture(texturePersoAnimated);
		engine.getTextureManager().loadTexture(texturePerso);
		engine.getTextureManager().loadTexture(textureTrou);
		engine.getTextureManager().loadTexture(textureTrouLight);

		init();

	}

	private void init() {

		// Chargement d'un rectangle
		rectangle = new Rectangle(20,
				MonActiviteInteraction.CAMERA_HAUTEUR - 100,
				MonActiviteInteraction.CAMERA_LARGEUR - 50, 100);

		float pRed = 0.0f;
		float pGreen = 0.5f;
		float pBlue = 0.5f;
		float pAlpha = 1.0f;
		rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
		attachChild(rectangle);

		// Chargement d'une image
		holeSprite = new Sprite(50, 50, textureRegionTrou);
		attachChild(holeSprite);
		holeLightSprite = new Sprite(50, 50, textureRegionTrouLight);
		attachChild(holeLightSprite);
		holeLightSprite.setVisible(false);

		// Initialisation de notre tank
		perso = new Sprite(MonActiviteInteraction.CAMERA_LARGEUR / 2,
				MonActiviteInteraction.CAMERA_HAUTEUR - 100, textureRegionPerso);
		attachChild(perso);
		peg = new Peg(MonActiviteInteraction.CAMERA_LARGEUR / 2,
				MonActiviteInteraction.CAMERA_HAUTEUR - 100,
				textureRegionPersoAnimated, callBack);
		// Redimensionne notre tank
		// peg.setScale(0.5f);
		final PhysicsHandler physicsHandler = new PhysicsHandler(peg);
		peg.registerUpdateHandler(physicsHandler);
		attachChild(peg);
		registerTouchArea(peg);
		peg.setVisible(false);

		registerUpdateHandler(updateHandler);
		setTouchAreaBindingEnabled(true);

	}

	public Sprite getPersoTmp() {
		return persoTmp;
	}

	public void removePegTmp() {
		if (persoTmp != null) {
			detachChild(persoTmp);
			persoTmp = null;
		}
	}

}
