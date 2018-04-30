package com.nanodegree.nahla.cupbake.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.fragments.DescriptionFragment;
import com.nanodegree.nahla.cupbake.fragments.StepsFragment;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nanodegree.nahla.cupbake.utils.Const.RECIPE_DESCRIPTION;
import static com.nanodegree.nahla.cupbake.utils.Const.RECIPE_STEP;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String RECIPE_DETAILS = "recipeDetails";
    ResponseRecipeListing recipeListing;

    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent newInstance(Context context, ResponseRecipeListing recipeListing) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(RECIPE_DETAILS, recipeListing);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_details_large_screen);
        } else {
            setContentView(R.layout.activity_details);
        }

        ButterKnife.bind(this);
        if (getIntent() != null)
            recipeListing = getIntent().getParcelableExtra(RECIPE_DETAILS);

        if (recipeListing != null && recipeListing.getName() != null) {
            setUpToolbar();
            if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                setUpFragment(DescriptionFragment.newInstance(recipeListing), RECIPE_DESCRIPTION, R.id.steps_ingredientsFL);
                setUpFragment(StepsFragment.newInstance(recipeListing.getSteps().get(0)), RECIPE_STEP, R.id.stepFL);

            } else {
                setUpFragment(DescriptionFragment.newInstance(recipeListing), RECIPE_DESCRIPTION);
            }
        }

    }

    private void setUpToolbar() {
        titleTV.setText(recipeListing.getName());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnClickListener(this);
        getSupportActionBar().setTitle(null);

    }

    private void setUpFragment(Fragment fragment, String tag) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.detailsContainerFL);
        if (currentFragment != null && tag.equals(currentFragment.getTag()))
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.detailsContainerFL, fragment, tag);
        ft.commit();
    }

    private void setUpFragment(Fragment fragment, String tag, int layoutId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(layoutId);
        if (currentFragment != null && tag.equals(currentFragment.getTag()))
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(layoutId, fragment, tag);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
