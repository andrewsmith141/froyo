package uk.ac.tees.com2060.froyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ListingAdapter used to create a custom ListView adapter and allows the ListView
 * to store and display the necessary information to show a Listing
 */
public class ListingAdapter extends ArrayAdapter<Listing> {

    //  Attributes used for getting the Context of the Adapter and the data
    private Context mContext;
    private ArrayList<Listing> mDataSource;

    /**
     * Constructor calls upon the default constructor to assign the given values
     *
     * @param items An arraylist of each of the listings
     * @param context   The context of the ListingAdapter
     */
    public ListingAdapter(ArrayList<Listing> items, Context context){
        super(context, R.layout.list_view_listing, items);

        mContext = context;
        mDataSource = items;
    }

    /**
     * Override of the getView method which is used to display the custom ListView
     *
     * @param position  The position of the current item
     * @param convertView  The reusable View
     * @param parent    Provided in order to assign the proper layout parameters to the current view
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null){
            LayoutInflater li;
            li = LayoutInflater.from(getContext());
            rowView = li.inflate(R.layout.list_view_listing, null);
        }

        Listing lis = getItem(position);

        if (lis != null) {
            // Get title element
            TextView titleTextView =
                    (TextView) rowView.findViewById(R.id.listing_list_title);

            // Get subtitle element
            TextView subtitleTextView =
                    (TextView) rowView.findViewById(R.id.listing_list_subtitle);

            // Get detail element
            TextView detailTextView =
                    (TextView) rowView.findViewById(R.id.listing_list_detail);

            // Get thumbnail element
            ImageView thumbnailImageView =
                    (ImageView) rowView.findViewById(R.id.listing_list_thumbnail);

            if (titleTextView != null)
                titleTextView.setText(lis.getName());

            if (subtitleTextView != null)
                subtitleTextView.setText(lis.getFromLocation());

        }

        return rowView;
    }
}
