package clock.aut;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Person;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;

import java.io.IOException;

import base.BaseActivity;
import service.DashboardItemRepository;
import service.models.PersonnelClockStatusViewModel;

public class PersonnelDetailActivity extends BaseActivity {


    private DashboardItemRepository dashboardItemRepository = new DashboardItemRepository();
    private ListView dashboardItemslistView;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private PersonnelClockStatusViewModel[] models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_detail);
        setTitle("اطلاعات پرسنل");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



      /*  progressBar = (ProgressBar) findViewById(R.id.dashboard_progress);

        progressBar.setVisibility(View.VISIBLE );*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public void initViewList(ListView dashboardItemslistView) {
        this.dashboardItemslistView=dashboardItemslistView;
        GetPersonnelClockStatusByDate task = new GetPersonnelClockStatusByDate();
        task.execute((Void) null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnel_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private PersonnelDetailActivity personnelDetailActivity;
        private int count=0;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_personnel_detail, container, false);
            // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //  textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            if (count == 0) {
                ListView personnelDetailListView = (ListView) rootView.findViewById(R.id.personnelDetailListView);
                personnelDetailActivity.initViewList(personnelDetailListView);
            } else {
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText
                        ("در دست تکمیل ");
            }

            return rootView;
        }

        public void setPersonnelDetailActivity(PersonnelDetailActivity personnelDetailActivity) {
            this.personnelDetailActivity = personnelDetailActivity;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final PersonnelDetailActivity activity;

        public SectionsPagerAdapter(FragmentManager fm, PersonnelDetailActivity activity) {
            super(fm);
            this.activity = activity;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment f = PlaceholderFragment.newInstance(position + 1);
            ((PlaceholderFragment) f).setPersonnelDetailActivity(activity);
            ((PlaceholderFragment) f).setCount(position);

            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }


    class GetPersonnelClockStatusByDate extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                refreshAdapter();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {

                long personnelId=(long)getIntent().getExtras().get("PersonnelId");

                models = dashboardItemRepository.getPersonnelClockStatusByDate(personnelId);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(PersonnelDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
            }


            return true;
        }
    }

    private void refreshAdapter() {
        DashboardItemAdapter adapter = new DashboardItemAdapter(PersonnelDetailActivity.this, models);
        dashboardItemslistView.setAdapter(adapter);


        dashboardItemslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (!haveNetworkConnection(PersonnelDetailActivity.this,
                        "به اینترنت متصل نیستید")) {
                    return;
                }


            }
        });
    }
}
