package uk.ac.tees.com2060.froyo;


import android.content.Context;
import android.content.Intent;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddListingFragment extends Fragment {

    //  Attributes
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    //  Used for
    private ItemSize itemSize;
    private EditText item_name, item_weight, item_from, item_to;
    private Button submit_listing_button, from_location_button, to_location_button;


    public AddListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_listing, container, false);

        database = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        item_name = view.findViewById(R.id.editText_item_name);
        item_weight = view.findViewById(R.id.editText_item_weight);
        from_location_button = view.findViewById(R.id.button_from_location);
        to_location_button = view.findViewById(R.id.button_to_location);
        item_from = view.findViewById(R.id.editText_from_location);
        item_to = view.findViewById(R.id.editText_to_location);

        submit_listing_button = view.findViewById(R.id.button_add_listing);


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
            }
        });


        return view;
    }

    private void writeNewListing(String id, String itemName, String fromLocation, String toLocation,
                                 double weight){
        Listing listing = new Listing(id, itemName, fromLocation, toLocation, weight);

        database.child("Listings").child(id).setValue(listing);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                item_from.setText(place.getAddress());
            } else {
                Toast.makeText(getActivity(), "Unsucessful", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                item_to.setText(place.getAddress());

            } else {
                Toast.makeText(getActivity(), "Unsucessful", Toast.LENGTH_LONG).show();
            }
        }
    }

}
