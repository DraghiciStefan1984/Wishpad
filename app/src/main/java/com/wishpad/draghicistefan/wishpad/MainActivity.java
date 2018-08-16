package com.wishpad.draghicistefan.wishpad;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import data.DataBaseHandler;
import data.WishAdapter;
import model.Wish;

public class MainActivity extends AppCompatActivity
{
   //We declare the datatbase handler class
    private DataBaseHandler dataBaseHandler;
    //We declare the arraylist of wish objects
    private ArrayList<Wish> dbWishes=new ArrayList<>();
    //We declare the views
    private WishAdapter wishAdapter;
    private ListView wishListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //We modify the default text in the snakbar view so that it displays our text message
                Snackbar.make(view, "Add a new Wish!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //When the user tapps on the add button, the activity where he can add a new wish will be displayed
                startActivity(new Intent(MainActivity.this, WishActivity.class));
            }
        });

        //We instantiate the views by calling the findViewById, and using the id's we gave each of them in the XML layout file
        wishListView= (ListView) findViewById(R.id.wishListView);
        wishAdapter=new WishAdapter(MainActivity.this, R.layout.wish_row, dbWishes);
        wishListView.setAdapter(wishAdapter);
        //We set a OnItemClickListener on each item of the list, so that when it is tapped, the user can view the wish on a new screen
        wishListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent=new Intent(MainActivity.this, ViewWishActivity.class);
                //We use the intent to send the information about the wish, that we stored in our array list, so that it can be used in the view wish activity
                intent.putExtra("title", dbWishes.get(position).getTitle());
                intent.putExtra("content", dbWishes.get(position).getContent());
                intent.putExtra("recorddate", dbWishes.get(position).getRecordDate());
                intent.putExtra("wishId", dbWishes.get(position).getId());
                //We start the activity using the intent
                startActivity(intent);
            }
        });
        //We call this method to refresh our wishlist
        refreshData();
    }

    //The method that we use to populate the wishlist
    private void refreshData()
    {
        //First, we clear our arraylist, so that we don't show duplicate items
        dbWishes.clear();
        //We create a new instance of DataBaseHandler from our application context
        dataBaseHandler=new DataBaseHandler(getApplicationContext());
        //We store in a new array list all the items from the database by calling the getAllWishes method
        ArrayList<Wish> wishesFromDb=dataBaseHandler.getAllWishes();
        //We search the entire array list of items from the database, and store the information for each wish in String objects
        for (int i=0; i<wishesFromDb.size(); i++)
        {
            String title=wishesFromDb.get(i).getTitle();
            String date=wishesFromDb.get(i).getRecordDate();
            String content=wishesFromDb.get(i).getContent();
            int id=wishesFromDb.get(i).getId();

            //For each set of data that we find , we create a new wish objects and set it's attributes using the title, date and content we retrieved and stored eralier
            Wish wish=new Wish();
            wish.setTitle(title);
            wish.setRecordDate(date);
            wish.setContent(content);
            wish.setItemId(id);
            //We add each newly created wish to our original array list
            dbWishes.add(wish);
        }
        //We close the connection to our database class
        dataBaseHandler.close();
        //We update the wish adapter, which extens a normal list adapter, so that we see all the wishes
        wishAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
