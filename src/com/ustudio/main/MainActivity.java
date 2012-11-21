package com.ustudio.main;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import android.hardware.usb.*;
import com.ustudio.managers.SamplesManager;
import com.ustudio.managers.SceneManager;
import com.ustudio.project.Project;
import com.ustudio.usb.USBRead;

public class MainActivity extends BaseGameActivity {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private ZoomCamera mCamera;
	private SceneManager mSceneManager;
	private Project mProject;
	private SamplesManager mSamplesManager;
	
	private PendingIntent mPermissionIntent;
	private boolean mPermissionRequestPending;
	private UsbManager mUsbManager;

	
	private Handler mDeviceHandler;
	
	private static final String ACTION_USB_PERMISSION = "com.ustudio.action.USB_PERMISSION";
	
	private UsbAccessory mAccessory;
	private ParcelFileDescriptor mFileDescriptor;
	private FileInputStream mInputStream;
	private FileOutputStream mOutputStream;
	private BroadcastReceiver mUsbReceiver=null;
	
	private static MainActivity mInstance;

	
	private void openAccessory(UsbAccessory accessory) {
		mFileDescriptor = mUsbManager.openAccessory(accessory);
		if (mFileDescriptor != null) {
			mAccessory = accessory;
			FileDescriptor fd = mFileDescriptor.getFileDescriptor();
			mInputStream = new FileInputStream(fd);
			mOutputStream = new FileOutputStream(fd);
			
			IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
			filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
			
			mUsbReceiver=new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					
					if (ACTION_USB_PERMISSION.equals(action)) {
						synchronized (this) {
							UsbAccessory accessory = (UsbAccessory)
					                   intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
							if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
								openAccessory(accessory);
							} else {

							}
							mPermissionRequestPending = false;
						}
					} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
						UsbAccessory accessory = (UsbAccessory)
				                   intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
						if (accessory != null && accessory.equals(mAccessory))
						{
							closeAccessory();
						}
					}
				}
			};
			this.registerReceiver(mUsbReceiver, filter);
			new Thread(new USBRead()).start();

		} else {
		}
	}
		 
		 
	private void closeAccessory() {
		try {
			if (mFileDescriptor != null) {
				mFileDescriptor.close();
			}
		} catch (IOException e) {
		} finally {
			mFileDescriptor = null;
			mAccessory = null;
		}
	}
	
	private void searchAccessory()
	{
		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		/*mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
		registerReceiver(mUsbReceiver, filter);
		Toast.makeText(this.getBaseContext(), "buscando", Toast.LENGTH_SHORT).show();
		if (getLastNonConfigurationInstance() != null) {
			mAccessory = (UsbAccessory) getLastNonConfigurationInstance();
			openAccessory(mAccessory);
			Toast.makeText(this.getBaseContext(), "last non conf", Toast.LENGTH_SHORT).show();
		}
		*/
		
		mAccessory = (UsbAccessory)this.getIntent().getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
		if (mAccessory != null) {
			if (mUsbManager.hasPermission(mAccessory)) {
				openAccessory(mAccessory);
			} else {
				// synchronized (mUsbReceiver) {
				// if (!mPermissionRequestPending) {
				// mUsbManager.requestPermission(accessory,
				// mPermissionIntent);
				// mPermissionRequestPending = true;
				// }
				// }
			}
		} else {
			// Log.d(TAG, "mAccessory is null");
		}
	}
	
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
		this.mDeviceHandler=new Handler();
	
		searchAccessory();
		
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
	
	@Override
	public void onDestroy() {
		if(mUsbReceiver!=null)
		{
			unregisterReceiver(mUsbReceiver);
		}
		super.onDestroy();
	}
	
	//SET	
	public void setSamplesManager(SamplesManager s)
	{
		this.mSamplesManager=s;
	}
	
	
	public void setProject(Project p) {
		this.mProject = p;
	}
	
	public static void setInstance(MainActivity mInstance) {
		MainActivity.mInstance = mInstance;
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

	public FileInputStream getFileInputStream() {
		return this.mInputStream;
	}
	
	public Handler getHandler() {
		return this.mDeviceHandler;
	}
}