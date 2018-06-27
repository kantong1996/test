package com.example.kanto.projects;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.io.IOException;


public class MainTabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

//    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static FragmentStatePagerAdapter adapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */


    private MyDBClass mDBClass;
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private Integer check;
    private Boolean rowExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        mDBClass = new MyDBClass(getApplicationContext());
        mDb = mDBClass.getWritableDatabase();



        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mCursor = mDb.rawQuery("SELECT * FROM List", null);
                if (mCursor.moveToFirst()) {
//                     DO SOMETHING WITH CURSOR
                    rowExists = true;
                } else {
                    // I AM EMPTY
                    rowExists = false;
                }
                switch (position) {
                    case 0:
                        if (rowExists.equals(false)) {
                            return new MainFragment();
                        } else {
                            return new HomeFragment();
                        }
                    case 1:
                        return new RegisterFragment();
                    case 2:
                        return new Search();
                    case 3:
                        return new CreateFragment();
                    case 4:
                        return new InformationFragment();
                    case 5:
                        return new DescriptionFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 6;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "หน้าหลัก";
                    case 1:
                        return "แบบสอบถามสุขภาพ";
                    case 2:
                        return "ค้นหา";
                    case 3:
                        return "สร้างรายการอาหาร";
                    case 4:
                        return "ประเมินตนเอง";
                    case 5:
                        return "รายละเอียด";
                }
                return null;
            }
        };
        ViewPager pager = (ViewPager) findViewById(R.id.container);
        pager.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);


    }
}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//         Handle action bar item clicks here. The action bar will
//         automatically handle clicks on the Home/Up button, so long
//         as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

//        noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//        @Override
//        public Fragment getItem(int position) {
//             getItem is called to instantiate the fragment for the given page.
//             Return a PlaceholderFragment (defined as a static inner class below).
//            switch (position) {
//                case 0:
//                    if(rowExists.equals(false)){
//                        return new MainFragment();
//                    }else{
//                        return new HomeFragment();
//                    }
//                case 1:
//                    return new RegisterFragment();
//                case 2:
//                    return new Search();
//                case 3:
//                    return new CreateFragment();
//                case 4:
//                    return new InformationFragment();
//            }
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//             Show 3 total pages.
//            return 5;
//        }
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "หน้าหลัก";
//                case 1:
//                    return "แบบสอบถามสุขภาพ";
//                case 2:
//                    return "ค้นหา";
//                case 3:
//                    return "สร้างรายการอาหาร";
//                case 4:
//                    return "ประเมินตนเอง";
//            }
//            return null;
//        }
//    }
//}
