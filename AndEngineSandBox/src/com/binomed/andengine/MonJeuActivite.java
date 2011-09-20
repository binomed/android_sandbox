package com.binomed.andengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

import android.graphics.Color;
import android.graphics.Typeface;

public class MonJeuActivite extends BaseGameActivity implements
		IScrollDetectorListener, IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	static final int CAMERA_LARGEUR = 480;
	static final int CAMERA_HAUTEUR = 320;
	static final float DEMO_VELOCITY = 100.0f;

	private static final String TAG = "AndEngineTest";

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera camera;
	private TextureRegion regionImage, personnage2;
	private Font font, fontPerso;
	private ChangeableText changeableText;
	private BitmapTextureAtlas textureSoldier, texturePersonnage,
			texturePersonnage2;
	private TiledTextureRegion soldier, personnage;
	private Sprite sprite2;

	// private ZoomCamera mCamera;
	// private BitmapTextureAtlas mTexture;
	// private TextureRegion mFaceTextureRegion;
	// private SurfaceScrollDetector mScrollDetector;
	// private TMXTiledMap mTMXTiledMap;

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
				, ScreenOrientation.LANDSCAPE //
				, new RatioResolutionPolicy(CAMERA_LARGEUR, CAMERA_HAUTEUR) //
				, camera));

	}

	@Override
	public void onLoadResources() {

		// Chargement de l'image
		final BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(
				64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		regionImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBitmapTextureAtlas, this, "andengine.png", 0, 0);

		// Prise en compte dans le moteur
		this.mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);

		// --
		// Chargement du texte
		final BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR);

		font = new Font(fontTexture, //
				Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), //
				24,//
				true,//
				Color.WHITE//
		);

		// Prise en compte dans le moteur
		this.mEngine.getTextureManager().loadTexture(fontTexture);
		this.mEngine.getFontManager().loadFont(font);

		// --
		// Chargement d'une police custo
		FontFactory.setAssetBasePath("font/");

		final BitmapTextureAtlas fontPersoTexture = new BitmapTextureAtlas(256,
				256, TextureOptions.BILINEAR);

		fontPerso = FontFactory.createFromAsset(fontPersoTexture //
				, this //
				, "exo.ttf" //
				, 24 //
				, true //
				, Color.WHITE //
				);

		this.mEngine.getTextureManager().loadTexture(fontPersoTexture);
		this.mEngine.getFontManager().loadFont(fontPerso);

		// Chargement d'une texture animée
		textureSoldier = new BitmapTextureAtlas(512, 64);
		soldier = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				textureSoldier, this, "soldier.png", 0, 0, 8, 1);

		texturePersonnage = new BitmapTextureAtlas(512, 64);
		texturePersonnage2 = new BitmapTextureAtlas(512, 64);
		personnage = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(texturePersonnage, this,
						"personnage.png", 0, 0, 1, 1);
		personnage2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				texturePersonnage2, this, "personnage.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(textureSoldier);
		this.mEngine.getTextureManager().loadTexture(texturePersonnage);
		this.mEngine.getTextureManager().loadTexture(texturePersonnage2);

	}

	@Override
	public Scene onLoadScene() {

		// Chargement de la scene
		final Scene scene = new Scene();

		// Chargement d'un rectangle
		final Rectangle rectangle = new Rectangle(CAMERA_LARGEUR / 2,
				CAMERA_HAUTEUR / 2, 100, 50);

		float pRed = 0.0f;
		float pGreen = 0.5f;
		float pBlue = 0.5f;
		float pAlpha = 1.0f;
		rectangle.setColor(pRed, pGreen, pBlue, pAlpha);
		scene.attachChild(rectangle);

		// Chargement d'une ligne
		final Line line = new Line(0, 0, CAMERA_LARGEUR, CAMERA_HAUTEUR);
		line.setColor(0.5f, 0.5f, 0.5f);
		scene.attachChild(line);

		// Chargement d'une image
		final Sprite sprite = new Sprite(50, 50, regionImage);
		scene.attachChild(sprite);

		// Chargement d'un texte
		final Text monTexte = new Text(0, 0, font, "Hello World !");
		scene.attachChild(monTexte);

		// Chargement d'un texte dynamique
		changeableText = new ChangeableText(0, 150, fontPerso,
				"Coucou tout le monde");
		changeableText.setText("Et puis non ! ");
		scene.attachChild(changeableText);

		// Animation sprite
		final AnimatedSprite animatedSprite = new AnimatedSprite(50, 100,
				soldier);
		animatedSprite.animate(25, true);

		scene.attachChild(animatedSprite);

		final Ball ball = new Ball(0, 0, personnage);
		final PhysicsHandler physicsHandler = new PhysicsHandler(ball);
		ball.registerUpdateHandler(physicsHandler);
		physicsHandler.setVelocity(DEMO_VELOCITY, DEMO_VELOCITY);

		scene.attachChild(ball);

		sprite2 = new Sprite(50, 50, personnage2);
		scene.attachChild(sprite2);

		scene.setOnSceneTouchListener(this);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {

				final Sprite face = sprite2;
				face.clearEntityModifiers();

				final float y = face.getY();
				face.setPosition(0, y);
				face.registerEntityModifier(new MoveModifier(3, 0,
						CAMERA_LARGEUR - face.getWidth(), y, y, EaseLinear
								.getInstance()));
			}
		});
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene,
			TouchEvent pSceneTouchEvent) {
		// this.mScrollDetector.onTouchEvent(pSceneTouchEvent);

		// if (pSceneTouchEvent.getX() == sprite2.getX()
		// && pSceneTouchEvent.getY() == sprite2.getY()) {
		this.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				pScene.detachChild(sprite2);

			}
		});
		// }
		return true;
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent,
			float pDistanceX, float pDistanceY) {
		// this.mCamera.offsetCenter(-pDistanceX, -pDistanceY);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private static class Ball extends AnimatedSprite {
		private final PhysicsHandler mPhysicsHandler;

		public Ball(final float pX, final float pY,
				final TiledTextureRegion pTextureRegion) {
			super(pX, pY, pTextureRegion);
			this.mPhysicsHandler = new PhysicsHandler(this);
			this.registerUpdateHandler(this.mPhysicsHandler);
		}

		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			if (this.mX < 0) {
				this.mPhysicsHandler.setVelocityX(DEMO_VELOCITY);
			} else if (this.mX + this.getWidth() > CAMERA_LARGEUR) {
				this.mPhysicsHandler.setVelocityX(-DEMO_VELOCITY);
			}

			if (this.mY < 0) {
				this.mPhysicsHandler.setVelocityY(DEMO_VELOCITY);
			} else if (this.mY + this.getHeight() > CAMERA_HAUTEUR) {
				this.mPhysicsHandler.setVelocityY(-DEMO_VELOCITY);
			}

			super.onManagedUpdate(pSecondsElapsed);
		}
	}
}