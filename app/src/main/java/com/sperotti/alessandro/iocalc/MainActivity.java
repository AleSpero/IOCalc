package com.sperotti.alessandro.iocalc;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.kobakei.ratethisapp.RateThisApp;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();

        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Spazio riservato a RateMyApp
        RateThisApp.Config config = new RateThisApp.Config(3,5);
        config.setTitle(R.string.ratetitle);
        config.setMessage(R.string.ratemex);
        config.setNoButtonText(R.string.rateno);
        config.setCancelButtonText(R.string.ratelater);
        config.setYesButtonText(R.string.ratenow);
        RateThisApp.init(config);




        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);


       /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_holder,new FragmentNumConv(),"fragmentnumconv");
        ft.commit();*/

        // Get the ViewPager and set it's PagerAdapter so that it can display items

        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount()));

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);




    }

}
