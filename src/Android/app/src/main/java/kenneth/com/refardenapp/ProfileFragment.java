package kenneth.com.refardenapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 1002215 on 20/7/19.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = "Profile Fragment";
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Set Fragment Title
        getActivity().setTitle("My Profile");
        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateProfile(currentUser);
    }

    private void updateProfile(FirebaseUser currentUser) {

        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = currentUser.getUid();
            Log.d(TAG, "Name is  " + name);
            Log.d(TAG, "Email is  " + email);
            Log.d(TAG, "uid is  " + uid);

        }

    }
}
