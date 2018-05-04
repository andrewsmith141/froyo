package uk.ac.tees.com2060.froyo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AuthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AuthFragment#} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {

    private Context mContext = getContext();
    private FirebaseAuth mAuth;
    private EditText email_register, password_register, email_signin,password_signin, display_name;
    private Button button_register, button_signin;
    private DatabaseReference database;


    private OnFragmentInteractionListener mListener;

    public AuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        email_register = view.findViewById(R.id.editText_email_register);
        password_register = view.findViewById(R.id.editText_password_register);

        email_signin = view.findViewById(R.id.editText_email_signin);
        password_signin = view.findViewById(R.id.editText_password_signin);
        display_name = view.findViewById(R.id.editText_display_name);

        button_register = view.findViewById(R.id.button_register);
        button_signin = view.findViewById(R.id.button_signin);

        mAuth = FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegister();
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSignIn();
            }
        });

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
        void onAuthFragmentInteraction();
    }


    public void submitRegister()
    {

        if (email_register.getText().length() > 0 && password_register.getText().length() > 0)
        {
            mAuth.createUserWithEmailAndPassword(
                    email_register.getText().toString(), password_register.getText().toString())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                database = FirebaseDatabase.getInstance().getReference();
                                writeNewUser(user.getUid(),email_register.getText().toString(), display_name.getText().toString());
                                mListener.onAuthFragmentInteraction();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(display_name.getText().toString())

                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage("Registration failed!")
                                        .setPositiveButton("Okay", null)
                                        .create().show();
                            }
                        }
                    });
        }
        else
        {
            new AlertDialog.Builder(mContext)
                    .setMessage("You must enter an email address and password!")
                    .setPositiveButton("Okay", null)
                    .create().show();
        }
    }

    private void writeNewUser(String id, String name, String email){
        User user = new User(id, name, email);

        database.child("users").child(id).setValue(user);
    }


    public void submitSignIn()
    {
        if (email_signin.getText().length() > 0 && password_signin.getText().length() > 0)
        {
            mAuth.signInWithEmailAndPassword(
                    email_signin.getText().toString(), password_signin.getText().toString())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                mListener.onAuthFragmentInteraction();

                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage("Login failed!")
                                        .setPositiveButton("Okay", null)
                                        .create().show();
                            }
                        }
                    });
        }
        else
        {
            new AlertDialog.Builder(mContext)
                    .setMessage("You must enter an email address and password!")
                    .setPositiveButton("Okay", null)
                    .create().show();
        }



    }




}
