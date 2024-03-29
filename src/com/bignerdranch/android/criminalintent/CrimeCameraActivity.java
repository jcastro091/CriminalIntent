/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author John
 *
 */
public class CrimeCameraActivity extends SingleFragmentActivity {
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		//Hide the window title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Hide the statud bar and other OS-level chrome
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected Fragment createFragment() {
		return new CrimeCameraFragment();
	}

}
