package com.wishpad.draghicistefan.wishpad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DataBaseHandler;
import model.Wish;

public class WishActivity extends AppCompatActivity
{
    //We declare the views
    private EditText titleEditText, contentEditText;
    private Button saveButton;
    //We declare the datatbase handler class
    private DataBaseHandler dba;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //We instantiate the views by calling the findViewById, and using the id's we gave each of them in the XML layout file
        titleEditText= (EditText) findViewById(R.id.titleEditText);
        contentEditText= (EditText) findViewById(R.id.contentEditText);
        saveButton= (Button) findViewById(R.id.saveButton);
        //We create a new instance of DataBaseHandler from our application context
        dba=new DataBaseHandler(getApplicationContext());

        //We save our wish when we click the "Save" button by calling the saveToDb method
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveToDb();
            }
        });
    }

    //The method we will call to save the wish on our database
    private void saveToDb()
    {
        //We create a new instance of a Wish object
        Wish wish=new Wish();
        //We set the title and content based on what we entered in the edit text views
        wish.setTitle(String.valueOf(titleEditText.getText()).trim());
        wish.setContent(String.valueOf(contentEditText.getText()).trim());
        //We add the wish to our database
        dba.addWish(wish);
        //After that, we close the connection to our database
        dba.close();
        //We clear the edit text views so that we can add a new wish
        titleEditText.setText("");
        contentEditText.setText("");

        //After we saved our wish, we return to the main activity
        Intent intent=new Intent(WishActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
