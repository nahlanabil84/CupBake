package com.nanodegree.nahla.cupbake.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.fragments.RecipeListingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nanodegree.nahla.cupbake.utils.Const.RECIPE_LIST;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpFragment();

    }

    private void setUpToolbar() {
        titleTV.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    private void setUpFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.homeContainerFL);

        if (currentFragment != null && RECIPE_LIST.equals(currentFragment.getTag()))
            return;

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction()
                .replace(R.id.homeContainerFL, RecipeListingFragment.newInstance(), RECIPE_LIST);

        ft.commit();
    }
}
