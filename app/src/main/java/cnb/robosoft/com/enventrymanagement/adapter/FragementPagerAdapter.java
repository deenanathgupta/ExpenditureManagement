package cnb.robosoft.com.enventrymanagement.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cnb.robosoft.com.enventrymanagement.DetailsFragment;

/**
 * Created by deena on 12/4/16.
 */
public class FragementPagerAdapter extends FragmentPagerAdapter  {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final ViewPager mViewPager;
    public FragementPagerAdapter(FragmentManager fm,ViewPager mViewPager) {
        super(fm);
        this.mViewPager=mViewPager;
        Log.i("Test", "FragementPagerAdapter CON()");
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("Test", "getItem()");
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        Log.i("Test", "getCount()"+mFragmentList.size());
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
