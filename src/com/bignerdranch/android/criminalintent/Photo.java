/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author John
 *
 */
public class Photo {
private static final String JSON_FILENAME = "filename";
private String mFilename;

//Create a photo representing file from disk
public Photo(String filename) {
	mFilename = filename;
}

public Photo (JSONObject json) throws JSONException{
	mFilename = json.getString(JSON_FILENAME);
}

public JSONObject toJson() throws JSONException{
	JSONObject json = new JSONObject();
	json.put(JSON_FILENAME, mFilename);
	return json;
}

public String getFilename() {
	return mFilename;
}
}
