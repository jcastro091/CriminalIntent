/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

/**
 * @author John
 *
 */
public class DatePickerFragment extends DialogFragment {
	
	static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
	private Date mDate;
	

	
	public static DatePickerFragment newInstance (Date date) {
		//stashes Date in bundle
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		//Date Picker Fragment
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);

		return fragment;
	}
	
	//Check to see if the targetFragment is null
	//Create an intent and put extra mDate
	//getTargetFragment from CrimeFragment class
	private void sendResult (int resultCode){
		if (getTargetFragment() == null)
			return ;
			
			Intent i = new Intent();
			i.putExtra(EXTRA_DATE,  mDate);
			
			getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);

		}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
		
		//Creating the calender
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		//Date picker to calendar
		DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			public void onDateChanged(DatePicker view, int year, int month, int day){
		
			//Translate year, month and ay into Date object
			mDate = new GregorianCalendar (year, month, day) .getTime();
			
			//Update argument to preserve select value on rotation
			getArguments().putSerializable(EXTRA_DATE, mDate);

			}
		});

		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(R.string.date_picker_title)
		//Set onclickListener to Button to send the result back is OK
		.setPositiveButton(android.R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);
				
			}
		})
		.create();
	}

}
