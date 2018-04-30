package com.nanodegree.nahla.cupbake.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.fragments.StepsFragment;
import com.nanodegree.nahla.cupbake.models.recipeListing.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nanodegree.nahla.cupbake.fragments.StepsFragment.RECIPE_STEP_KEY;
import static com.nanodegree.nahla.cupbake.utils.Const.RECIPE_STEP;

public class StepActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String RECIPE_NAME_KEY = "RECIPE_NAME_KEY";
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Step step;
    private String recipeName;

    public static Intent newInstance(Context context, Step step, String recipeName) {
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra(RECIPE_STEP_KEY, step);
        intent.putExtra(RECIPE_NAME_KEY, recipeName);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            step = getIntent().getParcelableExtra(RECIPE_STEP_KEY);
            recipeName = getIntent().getStringExtra(RECIPE_NAME_KEY);
        }

        if (step != null && recipeName != null) {
            setUpToolbar();
            setUpFragment();
        }

    }

    private void setUpToolbar() {
        titleTV.setText(recipeName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnClickListener(this);
        getSupportActionBar().setTitle(null);
    }

    private void setUpFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction()
                .replace(R.id.stepContainerFL, StepsFragment.newInstance(step), RECIPE_STEP);

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
