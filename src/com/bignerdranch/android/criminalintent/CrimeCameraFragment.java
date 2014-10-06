package com.bignerdranch.android.criminalintent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {

	public static final String TAG = "CrimeCameraFragment";
	public static final String EXTRA_PHOTO_FILENAME = "com.bignerdranch,android.criminalintent.photo_filename";
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;

	// ShutterCallback interface to show progres bar
	private Camera.ShutterCallback mShurtterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};

	// PictureCallback interface to save picture image to icon
	private Camera.PictureCallback mJpregCallback = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// Create file name
			String filename = UUID.randomUUID().toString() + ".jpg";
			// save jpeg data to disk
			FileOutputStream os = null;
			boolean success = true;
			try {
				os = getActivity()
						.openFileOutput(filename, Context.MODE_APPEND);
				os.write(data);
			} catch (Exception e) {
				Log.e(TAG, "Error writing to file " + filename, e);
				success = false;
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (Exception e) {
					Log.e(TAG, "Error closing file " + filename, e);
					success = false;
				}
			}
			//set the photo filename to the result intent
			if (success) {
				Intent i = new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			} else {
			getActivity().setResult(Activity.RESULT_CANCELED);
		}
			getActivity().finish();
			}
	};

	/* Simple Algorithm to find the largest size available for camera */
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {

		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;

		for (Size s : sizes) {
			int area = s.width * s.height;

			if (area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}

	@Override
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_crime_camera, parent, false);

		Button takePictureButton = (Button) v
				.findViewById(R.id.crime_camera_takePictureButton);
		takePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCamera != null) {
					mCamera.takePicture(mShurtterCallback, null, mJpregCallback);
				}
			}
		});

		mSurfaceView = (SurfaceView) v
				.findViewById(R.id.crime_camera_surfaceView);

		// Surface holder implementation SETTYPE() AND SURFACE_TYPE_PUSH_BUFFERS
		// ARE DEPRECATED
		// This is how you get camera view for devices under honeycomb --
		// compatability
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// SurfaceHolder.callback interface to interact with camera
		holder.addCallback(new SurfaceHolder.Callback() {
			// Stop the preview
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (mCamera != null) {
					mCamera.startPreview();
				}

			}

			// set the preview on create
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException e) {
					Log.e(TAG, "Error setting up the preview display", e);
				}

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				if (mCamera == null)
					return;

				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getBestSupportedSize(
						parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);

				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}

		});

		mProgressContainer = v
				.findViewById(R.id.crime_camera_progressContainter);
		mProgressContainer.setVisibility(View.INVISIBLE);

		return v;

	}

	/* OPEN THE CAMERA FOR DEVICES OVER GINGERBREAD */
	@TargetApi(9)
	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}

	}

	/* NULL CHECK TO KEEP APP FROM CRASHING....CAMERA */
	@Override
	public void onPause() {
		super.onPause();

		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

}
