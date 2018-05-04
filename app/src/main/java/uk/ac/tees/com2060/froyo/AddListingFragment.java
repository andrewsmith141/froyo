package uk.ac.tees.com2060.froyo;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddListingFragment extends Fragment {

    //  Attributes used for authenticating the user and connecting to the firebase databse
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    //  Values used for the layout and adding data to the database
    private ItemSize itemSize;
    private EditText item_name, item_weight, item_from, item_to;
    private String item_general_location;
    private Button submit_listing_button, from_location_button, to_location_button;


    public AddListingFragment() {
        // Required empty public constructor
    }

    /**
     *  Default onCreateView constructor which is called when creating a fragment
     *  Used for creating and calling the ListingFragment from within and displaying listings
     *
     * @param inflater  Creates objects associated with the given layout (XML) file
     * @param container This can contains other view within which allows it to stack child views
     * @param savedInstanceState    Allows data to be passed dynamically without loss of data
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_listing, container, false);

        // Connect to the database and make sure the user is authorized
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //  Assigning values to layout for manipulating the data and putting it into the database
        item_name = view.findViewById(R.id.editText_item_name);
        item_weight = view.findViewById(R.id.editText_item_weight);
        from_location_button = view.findViewById(R.id.button_from_location);
        to_location_button = view.findViewById(R.id.button_to_location);
        item_from = view.findViewById(R.id.editText_from_location);
        item_to = view.findViewById(R.id.editText_to_location);
        submit_listing_button = view.findViewById(R.id.submit_add_listing);


        //  Adding onClickListeners to the buttons which redirect to a Place Picker
        //  allowing the user to select their pickup/dropoff location with ease
        from_location_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        to_location_button.setOnClickListener(new View.OnClickListener() {

           /* @Override
            public void onClick(View v) {

                Fragment fragmentToLocation = new MapsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentToLocation)
                        .addToBackStack(null)
                        .commit();

            }*/

           @Override
            public void onClick(View v){
               PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

               try {
                   startActivityForResult(builder.build(getActivity()), 2);
               } catch (GooglePlayServicesRepairableException e) {
                   e.printStackTrace();
               } catch (GooglePlayServicesNotAvailableException e) {
                   e.printStackTrace();
               }
           }
        });

        //  Make sure user is authorized and add the form data to the database
        submit_listing_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                double weight = Double.parseDouble(item_weight.getText().toString());

                writeNewListing(
                        user.getUid(),
                        item_name.getText().toString(),
                        item_from.getText().toString(),
                        item_to.getText().toString(),
                        weight );

                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                //  return to the previous fragment
                onBackPressed();
            }
        });

        return view;
    }

    /**
     * writeNewListing method takes the values from the form and adds them to the database
     * making use of timestamped data for more scalablity and avoiding nested data
     *
     * @param id    Users ID
     * @param itemName  Name of the Item the user wishes to list
     * @param fromLocation  The location the item will be taken from
     * @param toLocation    The location the item will be taken to
     * @param weight        The weight of the item
     */
    private void writeNewListing(String id, String itemName, String fromLocation, String toLocation,
                                 double weight){
        //  Create a new Listing with the given data
        Listing listing = new Listing(id, itemName, fromLocation, toLocation, weight);
        //  Push that data to the database, giving it a unique id
        database.child("Listings").child(id).push().setValue(listing);
    }

    /**
     * Used to create the location picker and return the value if successful
     *
     * @param requestCode   Unique id for each request the method can receive
     * @param resultCode    Used to identify if request code was successfuly received
     * @param data  Intent required to great a place picker
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                //  Get the value from the place picker
                item_from.setText(place.getAddress());
            } else {
                Toast.makeText(getActivity(), "Unsucessful", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                //  Get the value from the place picker
                item_to.setText(place.getAddress());

            } else {
                Toast.makeText(getActivity(), "Unsucessful", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Returns the user to the supportFragment after they have successfully submitted the listing
     */
    public void onBackPressed(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
