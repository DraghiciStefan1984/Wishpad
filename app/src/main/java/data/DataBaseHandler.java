package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Wish;

/**
 * Created by Draghici Stefan on 11.02.2016.
 */
//The helper class that we will use to store our wish objects will extend the SQLiteOpenHelper class
public class DataBaseHandler extends SQLiteOpenHelper
{
    //We declare and instantiate an array list where we will store our wishes
    private final ArrayList<Wish> wishList=new ArrayList<>();

    //The public constructor will have the current context as a parameter
    public DataBaseHandler(Context context)
    {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //We create a string describing the creation of the table, and then we execute that query, and the table is created
        String CREATE_WISHES_TABLE="CREATE TABLE "+Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY, "+
                Constants.TITLE_NAME+" TEXT, "+Constants.CONTENT_NAME+" TEXT, "+Constants.DATE_NAME+" LONG);";
        db.execSQL(CREATE_WISHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //We check if there is another versio of the database and, if it exists,
        // we delete the current one and invoke the onCreate method to create the upgraded version
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //We create the public methor for adding a new wish to our database
    public void addWish(Wish wish)
    {
        //We create a new instance of a database by calling the getWritableDatabase method from our current context
        SQLiteDatabase db=this.getWritableDatabase();
        //We create a new instance of ContentValues to store the values for each field
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.TITLE_NAME, wish.getTitle());
        contentValues.put(Constants.CONTENT_NAME, wish.getContent());
        contentValues.put(Constants.DATE_NAME, java.lang.System.currentTimeMillis());

        //We insert the wish with its value fields into the database and then close the connection
        db.insert(Constants.TABLE_NAME, null, contentValues);
        db.close();
    }

    //The public method we will use to retrieve all items from our database
    public ArrayList<Wish> getAllWishes()
    {
        //We create a query to select all items from the database
        //String selectQuery="SELECT * FROM "+Constants.TABLE_NAME;
        //We get a readable database from the current context
        SQLiteDatabase db=this.getReadableDatabase();
        //With a cursor interface we query the database
        Cursor cursor=db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.TITLE_NAME,
                               Constants.CONTENT_NAME, Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME+" DESC");
        //If the cursor finds a new field that is not empty, for every time it finds a new entry,
        // we create a new instance of wish object and set its attributes according to the information stored in the respective fields
        //and add the newly crated wish in our array list
        if(cursor.moveToFirst())
        {
            do
            {
                Wish wish=new Wish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                wish.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                DateFormat dateFormat=DateFormat.getDateInstance();
                String date=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                wish.setRecordDate(date);

                wishList.add(wish);
            }
            while (cursor.moveToNext());
        }
        //After we got all the entries in the database, we return the full array list of wishes
        return wishList;
    }

    //The method we use to delete a selected wish from the database
    public void deleteWish(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
