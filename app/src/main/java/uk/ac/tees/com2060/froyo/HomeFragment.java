package uk.ac.tees.com2060.froyo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {}    //  Required empty constructor


    private TextView textView_test;

    /**
     *  Default onCreateView constructor which is called when creating a fragment
     *  displays the username upon relog
     *
     * @param inflater  Creates objects associated with the given layout (XML) file
     * @param container This can contains other view within which allows it to stack child views
     * @param savedInstanceState    Allows data to be passed dynamically without loss of data
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView_test = view.findViewById(R.id.textView_test);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textView_test.setText(user.getDisplayName());

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
        void onHomeFragmentInteraction();
    }
}
