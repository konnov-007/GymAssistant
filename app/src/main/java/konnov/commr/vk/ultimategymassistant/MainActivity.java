package konnov.commr.vk.ultimategymassistant;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CheckerFragment checkerFragment;
    TimerFragment timerFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mViewPager.getCurrentItem()) {
                    case 0: {
                        checkerFragment.uncheckEverything();
                        Snackbar.make(view, "Boxes are unchecked", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    }
                    case 1: {
                        timerFragment.clearAll();
                        Snackbar.make(view, "Timer is reset", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    public static class CheckerFragment extends Fragment {
        private CheckBox shoes;
        private CheckBox water;
        private CheckBox gloves;
        private CheckBox notebook;
        private CheckBox shake;

        private static final String ARG_SECTION_NUMBER = "0";

        public CheckerFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CheckerFragment newInstance(int sectionNumber) {
            CheckerFragment fragment = new CheckerFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.checker_fragment, container, false);
            shoes = rootView.findViewById(R.id.shoes_CB);
            water = rootView.findViewById(R.id.water_CB);
            gloves = rootView.findViewById(R.id.gloves_CB);
            notebook = rootView.findViewById(R.id.notebook_CB);
            shake = rootView.findViewById(R.id.shake_CB);
            return rootView;
        }

        private void uncheckEverything(){
            shoes.setChecked(false);
            water.setChecked(false);
            gloves.setChecked(false);
            notebook.setChecked(false);
            shake.setChecked(false);
        }
    }

    public static class TimerFragment extends Fragment implements View.OnClickListener{
        Button timerButton;
        TextView timerTV;
        private static final String ARG_SECTION_NUMBER = "1";
        long startTime = 0;

        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerTV.setText(String.format("%02d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        };


        public TimerFragment() {
        }

        public static TimerFragment newInstance(int sectionNumber) {
            TimerFragment fragment = new TimerFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.timer_fragment, container, false);
            timerButton = rootView.findViewById(R.id.start_stopwatch);
            timerButton.setOnClickListener(this);
            timerTV = rootView.findViewById(R.id.stopwatchTV);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == timerButton.getId()){
                if(timerButton.getText().equals(getResources().getString(R.string.end_timer))) {
                    timerHandler.removeCallbacks(timerRunnable);
                    timerButton.setText(getResources().getString(R.string.start_timer));
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    timerButton.setText(getResources().getString(R.string.end_timer));
                }
            }
        }


        public void clearAll(){
            timerTV.setText("00:00");
        }

    }






    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: {
                    checkerFragment = CheckerFragment.newInstance(position + 1);
                    return checkerFragment;
                }
                case 1: {
                    timerFragment = TimerFragment.newInstance(position + 1);
                    return timerFragment;
                }
            }
        return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
