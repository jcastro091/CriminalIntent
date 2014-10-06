/**
 * 
 */
package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * @author John
 *
 */
public class CrimePagerActivity extends FragmentActivity implements CrimeFragment.Callbacks{
	
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mViewPager =new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		
		FragmentManager fm =getSupportFragmentManager();
		//Set adapter to FragmentState Pager Adatper
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		
		
		//setting the title of crime on Viewpager
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				Crime crime =mCrimes.get(pos);
				if(crime.getTitle() !=null) {
					setTitle(crime.getTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});

		
		//sets the View pager to current crime selected
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}

	@Override
	public void onCrimeUpdated(Crime crime) {
		// TODO Auto-generated method stub
		
	}
}
