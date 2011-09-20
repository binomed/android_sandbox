package com.binomed.andengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MonActivite extends BaseGameActivity {

	public static Camera camera;

	public static final int CAMERA_LARGEUR = 480;
	public static final int CAMERA_HAUTEUR = 320;

	private SceneJeu maScene;

	@Override
	public Engine onLoadEngine() {
		// Initialisation de la caméra
		camera = new Camera(0, 0, CAMERA_LARGEUR, CAMERA_HAUTEUR);

		maScene = new SceneJeu();
		// Retourne le moteur de jeu
		return new Engine(new EngineOptions(true,//
				ScreenOrientation.LANDSCAPE,//
				new RatioResolutionPolicy(CAMERA_LARGEUR, CAMERA_HAUTEUR),//
				camera));

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

}
