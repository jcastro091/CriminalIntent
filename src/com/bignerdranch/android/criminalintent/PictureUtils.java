/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

/**
 * @author John
 * 
 */
@SuppressWarnings("deprecation")
public class PictureUtils {
	/*
	 * Get a BitmapDrawable from local file to scle down phot to fit current
	 * screen size
	 */
	public static BitmapDrawable getScaledDrawable(Activity a, String path) {
		Display display = a.getWindowManager().getDefaultDisplay();

		float destWidth = display.getWidth();
		float destHeight = display.getHeight();

		// Read in the dimensions of the image on disk
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;

		int inSampleSize = 1;
		if (srcHeight > destHeight || srcWidth > destWidth) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / destHeight);
			} else {
				inSampleSize = Math.round(srcWidth / destWidth);
			}
		}

		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return new BitmapDrawable(a.getResources(), bitmap);

	}

	// Clean up the view imagine for the sake of memory
	public static void cleanImageView(ImageView imageView) {
		if (!(imageView.getDrawable() instanceof BitmapDrawable))
			return;

		BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
		b.getBitmap().recycle();
		imageView.setImageDrawable(null);
	}

}
