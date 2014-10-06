/**
 * 
 */
package com.bignerdranch.android.criminalintent;


import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author John
 *
 */
public class Crime {
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";
	private static final String JSON_SUSPECT = "suspect";
	private UUID mId;
	private String mTitle;
	private boolean mSolved;
	private Date mDate;
	private Photo mPhoto;
	private String mSuspect;
	
	public Crime() {
		//Generate random UUID
		mId =UUID.randomUUID();
		mDate = new Date();
	}

	@Override
	public String toString() {
		return mTitle;
	}
	/**
	 * @return the id
	 */
	public UUID getId() {
		return mId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		mTitle = title;
	}

	/**
	 * @return the solved
	 */
	public boolean isSolved() {
		return mSolved;
	}

	/**
	 * @param solved the solved to set
	 */
	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return mDate;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		mDate = date;
	}
	
	/*Loading crime from file system */
	public Crime (JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
		//Get the JSON PHOTO
		if (json.has(JSON_PHOTO))
			mPhoto =new Photo(json.getJSONObject(JSON_PHOTO));
		//Get the JSON SUSPECT
		if(json.has(JSON_SUSPECT))
			mSuspect = json.getString(JSON_SUSPECT);
	}
	
	
	/*Setting up the JSON object to put parameters to JSON*/
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		
		if (mPhoto != null){
			json.put(JSON_PHOTO, mPhoto.toJson());
			json.put(JSON_SUSPECT, mSuspect);
		}
		
		return json;
	}

	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo p) {
		mPhoto = p;
	}

	public String getSuspect() {
		return mSuspect;
	}

	public void setSuspect(String suspect) {
		mSuspect = suspect;
	}



}
