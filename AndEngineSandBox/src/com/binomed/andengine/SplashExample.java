package com.binomed.andengine;

import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.ui.activity.BaseSplashActivity;

import android.app.Activity;

public class SplashExample extends BaseSplashActivity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int SPLASH_DURATION = 3;
	private static final float SPLASH_SCALE_FROM = 1f;

	@Override
	protected ScreenOrientation getScreenOrientation() {
		return ScreenOrientation.LANDSCAPE;
	}

	@Override
	protected IBitmapTextureAtlasSource onGetSplashTextureAtlasSource() {
		return new AssetBitmapTextureAtlasSource(this, "gfx/splash.png");
	}

	@Override
	protected float getSplashDuration() {
		return SPLASH_DURATION;
	}

	@Override
	protected Class<? extends Activity> getFollowUpActivity() {
		return MonActiviteInteraction.class;
	}

	@Override
	protected float getSplashScaleFrom() {
		return SPLASH_SCALE_FROM;
	}

}
