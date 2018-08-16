package data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishpad.draghicistefan.wishpad.R;

import java.util.ArrayList;

import model.Wish;

/**
 * Created by Draghici Stefan on 11.02.2016.
 */
//The custom array adapter we will use to store our wishes the way we choose
public class WishAdapter extends ArrayAdapter<Wish>
{
    //We declare and activity which will use the adapter
    Activity activity;
    //We declare an int for the layout resource
    int layoutResource;
    //We declare the wish object that we will store
    Wish wish;
    //We declare the arraylist of wishes
    ArrayList<Wish> mData=new ArrayList<>();

    public WishAdapter(Activity act, int resource, ArrayList<Wish> data)
    {
        super(act, resource, data);
        //We instantiate our variables
        activity=act;
        layoutResource=resource;
        mData=data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @Override
    public Wish getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public int getPosition(Wish item)
    {
        return super.getPosition(item);
    }

    //The method that will display our data
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //We instantiate the row and the viewholder class - the model that the row will use to display our data
        View row=convertView;
        ViewHolder viewHolder=null;
        //If the row and row's tag are not null, we instantiate a layout inflater which will actually build our custom rows
        if((row==null) || (row.getTag()==null))
        {
            LayoutInflater inflater=LayoutInflater.from(activity);
            row=inflater.inflate(layoutResource,null);
            //We instantiate a viewholder which we will populate with data retrieved from our row
            viewHolder=new ViewHolder();
            viewHolder.mTitle= (TextView) row.findViewById(R.id.wishDateTextView);
            viewHolder.mImage= (ImageView) row.findViewById(R.id.iconImageView);
            //we set the tag of our row to point to the viewholder's values
            row.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) row.getTag();
        }
        viewHolder.mWish=getItem(position);
        viewHolder.mTitle.setText(viewHolder.mWish.getTitle());
        return row;
    }

    //We create a helper class that will serve as a model for our list rows
    class ViewHolder
    {
        Wish mWish;
        TextView mTitle;
        ImageView mImage;
    }
}
