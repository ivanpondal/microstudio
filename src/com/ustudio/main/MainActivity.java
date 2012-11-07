package com.ustudio.main;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.ustudio.managers.SamplesManager;
import com.ustudio.managers.SceneManager;
import com.ustudio.project.Project;

import dalvik.system.VMRuntime;

public class MainActivity extends BaseGameActivity {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private ZoomCamera mCamera;
	private SceneManager mSceneManager;
	private Project mProject;
	private SamplesManager mSamplesManager;
	
	private static MainActivity mInstance;

	
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		
		CAMERA_WIDTH = this.getWindowManager().getDefaultDisplay().getWidth();
		CAMERA_HEIGHT = this.getWindowManager().getDefaultDisplay().getHeight();
		MainActivity.setInstance(this);
		this.mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		this.mSceneManager = new SceneManager();
		this.mProject = new Project();
		this.mSamplesManager = new SamplesManager();
		
		final Engine mEngine = new Engine(new EngineOptions(true,ScreenOrientation.PORTRAIT , new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT ), mCamera).setNeedsSound(true));
		try {
			if (MultiTouch.isSupported(this)) {
				mEngine.setTouchController(new MultiTouchController());
			} else {
			    //Toast.makeText(this,"Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)",Toast.LENGTH_LONG).show();
			}
		} catch (final MultiTouchException e) {
			//Toast.makeText(this,"Sorry your Android Version does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)",Toast.LENGTH_LONG).show();
		}
		
		return mEngine;
	}
	
	@Override
	public void onLoadResources() {

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		this.mSceneManager.setRecordScene(CAMERA_WIDTH,CAMERA_HEIGHT);
		
        return this.mSceneManager.getCurrentScene();
	}
    /** Called when the activity is first created. */
	 
	//SET	
	public void setSamplesManager(SamplesManager s)
	{
		this.mSamplesManager=s;
	}
	
	//GET
	public ZoomCamera getCamera() {
		return this.mCamera;
	}
    
	public SceneManager getSceneManager() {
		return this.mSceneManager;
	}
	
	public Project getProject() {
		return this.mProject;
	}
	
	public SamplesManager getSamplesManager() {
		return this.mSamplesManager;
	}
	
	public static MainActivity getInstance() {
		return mInstance;
	}

	public static void setInstance(MainActivity mInstance) {
		MainActivity.mInstance = mInstance;
	}

}