package com.Michael;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import com.Michael.AccountBook.R;

public class CircleChartActivity extends FragmentActivity {
    private ViewPager viewPager_main;
    private ActionBar actionBar_main;
    private List<Fragment> list = null;
    private String[] arrPagerTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragment);

        arrPagerTitles = getResources().getStringArray(R.array.arrTabSpec);

        actionBar_main = getActionBar();
        actionBar_main.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar_main.setDisplayShowHomeEnabled(true);
        actionBar_main.setDisplayHomeAsUpEnabled(true);
        actionBar_main.setHomeButtonEnabled(true);
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);

        list = new ArrayList<Fragment>();
        for (int i = 0; i < arrPagerTitles.length; i++) {
            actionBar_main.addTab(actionBar_main.newTab()
                    .setText(arrPagerTitles[i])
                    .setTabListener(new TabListener() {

                        @Override
                        public void onTabUnselected(Tab tab,
                                                    FragmentTransaction ft) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onTabSelected(Tab tab,
                                                  FragmentTransaction ft) {
//							viewPager_main.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabReselected(Tab tab,
                                                    FragmentTransaction ft) {
                            // TODO Auto-generated method stub

                        }
                    }));

            DummyFragemnt fragment = new DummyFragemnt();
            Bundle bundle = new Bundle();
            bundle.putInt("tabindex", i);
            fragment.setArguments(bundle);
            list.add(fragment);
        }

        viewPager_main.setAdapter(new MyPagerAdapter(
                getSupportFragmentManager(), list));

        viewPager_main.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar_main.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list = null;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
               this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
