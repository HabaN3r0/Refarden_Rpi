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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by 1002215 on 20/7/19.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = "Profile Fragment";
    private FirebaseAuth mAuth;
    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileBirth;
    private TextView mProfileEmail;
    private TextView mProfileExperience;
    private TextView mProfilePlantTypes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Set Fragment Title
        getActivity().setTitle("My Profile");
        mAuth = FirebaseAuth.getInstance();
        mProfileImage = view.findViewById(R.id.profileImage);
        mProfileName = view.findViewById(R.id.profileName);
        mProfileBirth = view.findViewById(R.id.profileBirth);
        mProfileEmail = view.findViewById(R.id.profileEmail);
        mProfileExperience = view.findViewById(R.id.profileExperience);
        mProfilePlantTypes = view.findViewById(R.id.profilePlantTypes);

        // Creating database instance and reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Profile");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Call another function to update the UI on the screen by passing in datasnapshot which is like the info
                updateUi(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return view;
    }

    private void updateUi(DataSnapshot dataSnapshot){

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d(TAG, "ds is:  " + ds.getKey() + " value: " + ds.getValue());
            if (ds.getKey().equals("Name")) {
                if (ds.getValue().equals(null)) {} else {
                    mProfileName.setText(ds.getValue().toString());
                }
            } else if (ds.getKey().equals("Birthday")) {
                if (ds.getValue().equals(null)) {} else {
//                    String day = String.valueOf(Integer.valueOf(ds.getValue().toString())%1000000);
//                    String month = String.valueOf(Integer.valueOf(ds.getValue().toString())%10000);
                    int date = Integer.valueOf(ds.getValue().toString());
                    String day = String.valueOf(date/1000000);
                    date %= 1000000;
                    String month = String.valueOf(date/10000);
                    date %= 10000;
                    String year = String.valueOf(date);

                    Log.d(TAG, "day: " + day + " month: " + month + " year: " + year);
                    mProfileBirth.setText(day + "/" + month + "/" + year);
                }
            } else if (ds.getKey().equals("Email")) {
                if (ds.getValue().equals(null)) {} else {
                    mProfileEmail.setText(ds.getValue().toString());
                }
            } else if (ds.getKey().equals("Start Date")) {
                if (ds.getValue().equals(null)) {} else {

                    //Compare start date with current date
                    SimpleDateFormat myFormat = new SimpleDateFormat("ddMMyyyy");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
                    LocalDateTime currentDate = LocalDateTime.now();
                    String inputString1 = ds.getValue().toString();
                    String inputString2 = dtf.format(currentDate);

                    Log.w(TAG, "date1: " + inputString1 + " date2: " + inputString2);
                    try {
                        Date date1 = myFormat.parse(inputString1);
                        Date date2 = myFormat.parse(inputString2);
                        long diff = date2.getTime() - date1.getTime();
                        mProfileExperience.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " days");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else if (ds.getKey().equals("Plant Types")) {
                if (ds.getValue().equals(null)) {} else {
                    mProfilePlantTypes.setText(ds.getValue() + "plants");
                }
            } else {}
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

//    private void updateProfile(FirebaseUser currentUser) {
//
//        if (currentUser != null) {
//            // Name, email address, and profile photo Url
//            String name = currentUser.getDisplayName();
//            String email = currentUser.getEmail();
//            Uri photoUrl = currentUser.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = currentUser.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            String uid = currentUser.getUid();
//            Log.d(TAG, "Name is  " + name);
//            Log.d(TAG, "Email is  " + email);
//            Log.d(TAG, "uid is  " + uid);
//
//        }
//
//    }
}
