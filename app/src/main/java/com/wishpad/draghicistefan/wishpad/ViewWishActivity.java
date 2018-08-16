package com.wishpad.draghicistefan.wishpad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import data.DataBaseHandler;

public class ViewWishActivity extends AppCompatActivity
{
    //We declare the views
    private TextView viewWishTitleTextView, viewWishDetailTextView, viewWishDateTextView;
    //We declare the datatbase handler class
    private DataBaseHandler dbHandler;
    private int wishId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //We instantiate the views by calling the findViewById, and using the id's we gave each of them in the XML layout file
        viewWishTitleTextView = (TextView) findViewById(R.id.viewWishTitleTextView);
        viewWishDetailTextView = (TextView) findViewById(R.id.viewWishDetailTextView);
        viewWishDateTextView = (TextView) findViewById(R.id.viewWishDateTextView);

        //We stored the extra information we got from the intent into a Bundle object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            //If the information in the received intent are not null, we use that information to populate the our text labels
            viewWishTitleTextView.setText(extras.getString("title"));
            viewWishDetailTextView.setText("\"" + extras.getString("content") + "\"");
            viewWishDateTextView.setText("Whished at " + extras.getString("recorddate"));
            wishId = extras.getInt("wishId");
        }

        //We use the floating action button as an option to delete the wish we are currently viewing
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Snackbar.make(view, "Erase your wish", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                dbHandler = new DataBaseHandler(getApplicationContext());
                dbHandler.deleteWish(wishId);
                startActivity(new Intent(ViewWishActivity.this, MainActivity.class));
            }
        });
    }

}
