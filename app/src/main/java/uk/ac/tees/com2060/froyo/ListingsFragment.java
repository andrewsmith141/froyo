package uk.ac.tees.com2060.froyo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragment used to contain he listings
 */
public class ListingsFragment extends Fragment {

    //  Connect to database
    private Context mContext = getContext();
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef, realRef;


    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private Button mAddListing;


    public ListingsFragment() {}

    /**
     *  Default onCreateView constructor which is called when creating a fragment
     *  Used for displaying listings and calling AddListingFragment to add a new one
     *
     * @param inflater  Creates objects associated with the given layout (XML) file
     * @param container This can contains other view within which allows it to stack child views
     * @param savedInstanceState    Allows data to be passed dynamically without loss of data
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        mListView = view.findViewById(R.id.list_view_listings);
        mAddListing = view.findViewById(R.id.button_add_listing);


        rootRef = FirebaseDatabase.getInstance().getReference();


        mAddListing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //  Create new AddListingFragment and call it
                Fragment fragmentAddListing = new AddListingFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentAddListing)
                        .addToBackStack(null)
                        .commit();
            }
        });

        /*
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed : " + databaseError.getMessage());

            }
        });

        ListView listingsListView = view.findViewById(R.id.list_view_listings);

        ListingAdapter lisAdapter = new ListingAdapter(getActivity(), R.layout.list_view_listing, );

        setListAdapter(lisAdapter);



        //  Get listings for a specified user
        //final ArrayList<Listing> listingList = Listing.getListingsFromUser();

        //  Get all nearby listings
        //final ArrayList<Listing> listingList = Listing.getListingsFromArea();
        */

        return view;
    }

    /**
     * Attaches the current fragment to an existing activity
     *
     * @param context Existing Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onListingsFragmentInteraction();
    }
}
