/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

/**
 * @author John
 * 
 */
public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}

	/* Loads the crime from JSON */
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try {
			// Open and read file into StringBuilder
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			// Parse JSON using JSONTOKENER
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue();
			// Build the arrat of crimes from JSONOBJECT
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return crimes;
	}

	/* Saves and writes to JSON */
	public void saveCrimes(ArrayList<Crime> crimes) throws IOException,
			JSONException {
		// Build an array in JSON
		JSONArray array = new JSONArray();
		// For each crime, put array in Json
		for (Crime c : crimes)
			array.put(c.toJSON());

		// Write the file to disk
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());

		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
