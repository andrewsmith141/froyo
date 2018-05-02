package uk.ac.tees.com2060.froyo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ListingsFragment extends Fragment {

    private Context mContext = getContext();
    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;
    private ListView mListView;


    public ListingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        mListView = view.findViewById(R.id.list_view_listings);

        //  Get listings for a specified user
        //final ArrayList<Listing> listingList = Listing.getListingsFromUser();

        //  Get all nearby listings
        //final ArrayList<Listing> listingList = Listing.getListingsFromArea();

        return view;
    }

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
