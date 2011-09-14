package com.binomed.andengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

public class MonActiviteInteraction extends BaseGameActivity implements IScrollDetectorListener, IOnSceneTouchListener,MovePeg {

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
	private SceneInteraction maScene;

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
		maScene = new SceneInteraction(this);
		return new Engine(new EngineOptions(true //
				, ScreenOrientation.PORTRAIT //
				, new RatioResolutionPolicy(CAMERA_LARGEUR, CAMERA_HAUTEUR) //
				, camera));

	}

	@Override
	public void onLoadResources() {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		maScene.loadResources(mEngine, this);

	}

	@Override
	public Scene onLoadScene() {
		return maScene;
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

	public void animatePeg(final AnimatedSprite sprite, final float x, final float y){
		this.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				sprite.clearEntityModifiers();
//				final float y = sprite.getY();
//                sprite.setPosition(x, y);
                sprite.registerEntityModifier(new MoveModifier(1 // time
                		, sprite.getX(), x //x soucre x to
                		, sprite.getY(), y // y source y to
                		, EaseLinear.getInstance()//
                         ));
                
//                sprite.setPosition(0, y);
//                sprite.registerEntityModifier(new MoveModifier(3, 0,
//                                CAMERA_LARGEUR - sprite.getWidth(), y, y,
//                                EaseLinear.getInstance()));
				
			}
		});
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}