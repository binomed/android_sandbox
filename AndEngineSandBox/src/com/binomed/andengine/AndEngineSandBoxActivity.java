package com.binomed.andengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class AndEngineSandBoxActivity extends BaseGameActivity implements IScrollDetectorListener, IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 320;

	private static final String TAG = "AndEngineTest";

	// ===========================================================
	// Fields
	// ===========================================================

	private ZoomCamera mCamera;
	private BitmapTextureAtlas mTexture;
	private TextureRegion mFaceTextureRegion;
	private SurfaceScrollDetector mScrollDetector;
	private TMXTiledMap mTMXTiledMap;

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
		this.mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final int alturaTotal = CAMERA_HEIGHT * 3;
		this.mCamera.setBounds(0, CAMERA_WIDTH, 0, alturaTotal);
		this.mCamera.setBoundsEnabled(true);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mTexture = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/ui_ball_1.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);

		try {
			final TMXLoader tmxLoader = new TMXLoader(this, this.mEngine.getTextureManager(), TextureOptions.NEAREST);
			this.mTMXTiledMap = tmxLoader.loadFromAsset(this, "tmx/field.tmx");
		} catch (final TMXLoadException tmxle) {
			Log.e(TAG, "error", tmxle);
		}

		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		scene.getFirstChild().attachChild(tmxLayer);
		scene.setOnAreaTouchTraversalFrontToBack();

		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mScrollDetector.setEnabled(true);

		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;

		/* Dibujamos la bola en el centro de la pantalla. */
		final Sprite ball = new Sprite(centerX, centerY, this.mFaceTextureRegion);
		scene.getLastChild().attachChild(ball);

		scene.setOnSceneTouchListener(this);
		scene.setTouchAreaBindingEnabled(true);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return true;
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
		this.mCamera.offsetCenter(-pDistanceX, -pDistanceY);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}