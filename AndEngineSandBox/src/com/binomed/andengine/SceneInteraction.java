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

	private BitmapTextureAtlas textureTrou, texturePersoAnimated, texturePerso;
	private TextureRegion textureRegionTrou, textureRegionPerso;
	private TiledTextureRegion textureRegionPersoAnimated;
	private Peg peg;
	private PhysicsHandler physicsHandler;
	private Rectangle rectangle;
	private Sprite holeSprite;
	private IUpdateHandler updateHandler = new IUpdateHandler() {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			if (rectangle.collidesWith(peg)) {
				float pRed = 0.5f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
			} else {
				float pRed = 0.0f;
				float pGreen = 0.5f;
				float pBlue = 0.5f;
				float pAlpha = 1.0f;
				rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
			}

		}
	};

	public SceneInteraction() {
		super();
		setBackground(new ColorBackground(0.52f, 0.75f, 0.03f));
	}

	public void loadResources(final Engine engine, Context context) {
		texturePersoAnimated = new BitmapTextureAtlas(512, 64);
		texturePerso = new BitmapTextureAtlas(512, 64);
		textureTrou = new BitmapTextureAtlas(128, 128);
		textureRegionPersoAnimated = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texturePersoAnimated, context, "soldier.png", 0, 0, 8, 1);
		textureRegionPerso = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturePerso, context, "soldier.png", 64, 0);
		textureRegionTrou = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureTrou, context, "blackhole.png", 0, 0);

		engine.getTextureManager().loadTexture(texturePersoAnimated);
		engine.getTextureManager().loadTexture(texturePerso);
		engine.getTextureManager().loadTexture(textureTrou);

		init();

	}

	private void init() {

		// Chargement d'un rectangle
		rectangle = new Rectangle(20, MonActiviteInteraction.CAMERA_HAUTEUR - 100, MonActiviteInteraction.CAMERA_LARGEUR - 50, 100);

		float pRed = 0.0f;
		float pGreen = 0.5f;
		float pBlue = 0.5f;
		float pAlpha = 1.0f;
		rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
		attachChild(rectangle);

		// Chargement d'une image
		holeSprite = new Sprite(50, 50, textureRegionTrou);
		attachChild(holeSprite);

		// Initialisation de notre tank
		peg = new Peg(0, 0, textureRegionPersoAnimated);
		// Redimensionne notre tank
		// peg.setScale(0.5f);
		final PhysicsHandler physicsHandler = new PhysicsHandler(peg);
		peg.registerUpdateHandler(physicsHandler);
		attachChild(peg);
		registerTouchArea(peg);

		registerUpdateHandler(updateHandler);
		setTouchAreaBindingEnabled(true);

	}

}
