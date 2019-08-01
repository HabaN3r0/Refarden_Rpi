package kenneth.com.refardenapp;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewTradePostActivity extends AppCompatActivity {

    private static final String TAG = "New Trade Post Activity";
    private Button mNewPostBackButton;
    private TextView mNewPostProfileName;
    private ImageView mNewPostProfileImage;
    private TextInputLayout mNewPostPlantTypeEditText;
    private TextInputLayout mNewPostPlantsWantedEditText;
    private Button mNewPostUploadCamera;
    private Button mNewPostUploadGallery;
    private TextInputLayout mNewPostDescription;
    private FloatingActionButton mCreatePostAddButton;
    private FirebaseAuth mAuth;
    private String name;
    private String stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trade_post);

        // Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mAuth = FirebaseAuth.getInstance();
        mNewPostBackButton = findViewById(R.id.newPostBackButton);
//        mNewPostProfileName = findViewById(R.id.newPostProfileName);
//        mNewPostProfileImage = findViewById(R.id.newPostProfileImage);
        mNewPostPlantTypeEditText = findViewById(R.id.newPostPlantTypeEditText);
        mNewPostPlantsWantedEditText = findViewById(R.id.newPostPlantsWantedEditText);
        mNewPostUploadCamera = findViewById(R.id.newPostUploadCamera);
        mNewPostUploadGallery = findViewById(R.id.newPostUploadGallery);
        mNewPostDescription = findViewById(R.id.newPostDescriptionEditText);
        mCreatePostAddButton = findViewById(R.id.createNewPostFAB);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        // Read from the database
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Call another function to update the UI on the screen by passing in datasnapshot which is like the info
                updateName(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mNewPostBackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        mNewPostUploadCamera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        mNewPostUploadGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        mCreatePostAddButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadPost(myRef);
                    }
                }
        );

    }

    private void updateName(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d(TAG, "ds is:  " + ds.getKey());
            if (ds.getKey().equals("Name")) {
                if (ds.getValue().equals(null)) {} else {
                    name = ds.getValue().toString();
                }
//                mNewPostProfileName.setText(ds.getValue().toString());
            } else if (ds.getKey().equals("Stars")) {
                if (ds.getValue().equals(null)) {
                } else {
                    stars = ds.getValue().toString();
                }
            }
        }
    }

    private boolean validatePlantType() {
        String plantType = mNewPostPlantTypeEditText.getEditText().getText().toString().trim();

        if (plantType.isEmpty()) {
            mNewPostPlantTypeEditText.setError("Field can't be empty");
            return false;
        }
        else {
            mNewPostPlantTypeEditText.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePlantsWanted() {
        String plantsWanted = mNewPostPlantsWantedEditText.getEditText().getText().toString().trim();

        if (plantsWanted.isEmpty()) {
            mNewPostPlantsWantedEditText.setError("Field can't be empty");
            return false;
        }

        else {
            mNewPostPlantsWantedEditText.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDescription() {
        String description = mNewPostDescription.getEditText().getText().toString().trim();

        if (description.isEmpty()) {
            mNewPostDescription.setError("Field can't be empty");
            return false;
        }

        else {
            mNewPostDescription.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private void uploadPost(DatabaseReference myRef) {

        if (!validatePlantType() | !validatePlantsWanted() | !validateDescription()) {
            return;
        }

//        SimpleDateFormat myFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        LocalDateTime currentDate = LocalDateTime.now();
        String timeStamp = dtf.format(currentDate);

        //Post in Trading Platform
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Name").setValue(name);
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Plant Type").setValue(mNewPostPlantTypeEditText.getEditText().getText().toString().trim());
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Plants Wanted").setValue(mNewPostPlantsWantedEditText.getEditText().getText().toString().trim());
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Description").setValue(mNewPostDescription.getEditText().getText().toString().trim());
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Stars").setValue(stars);
        myRef.child("Trading Platform").child(mAuth.getCurrentUser().getUid()).child(timeStamp).child("Request").setValue("no");


        //Save a copy of info in user's account
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Trading Platform").child(timeStamp).child("Plant Type").setValue(mNewPostPlantTypeEditText.getEditText().getText().toString().trim());
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Trading Platform").child(timeStamp).child("Plants Wanted").setValue(mNewPostPlantsWantedEditText.getEditText().getText().toString().trim());
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Trading Platform").child(timeStamp).child("Description").setValue(mNewPostDescription.getEditText().getText().toString().trim());
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Trading Platform").child(timeStamp).child("Request").setValue("no");

        Toast.makeText(NewTradePostActivity.this, "Trade post created",
                Toast.LENGTH_LONG).show();
        finish();
    }
}
