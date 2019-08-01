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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TradeItemActivity extends AppCompatActivity {

    private static final String TAG = "Trade Item Activity";
    private FirebaseAuth mAuth;
    private Button mTradeItemtBackButton;
    private TextView mTradeItemProfileName;
    private ImageView mTradeItemProfileImage;
    private ImageView mTradeItemStar1;
    private ImageView mTradeItemStar2;
    private ImageView mTradeItemStar3;
    private ImageView mTradeItemStar4;
    private ImageView mTradeItemStar5;
    private TextView mTradeItemPlantType;
    private TextView mTradeItemDescription;
    private ImageView mTradeItemPostPlantImage;
    private TextView mTradeItemPlantsWanted;
    private Button mTradeItemTradeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_item);
        mTradeItemtBackButton = findViewById(R.id.tradeItemBackButton);
        mTradeItemProfileName = findViewById(R.id.tradeItemProfileName);
        mTradeItemProfileImage = findViewById(R.id.tradeItemPostProfileImage);
        mTradeItemStar1 = findViewById(R.id.tradeItemStar1);
        mTradeItemStar2 = findViewById(R.id.tradeItemStar2);
        mTradeItemStar3 = findViewById(R.id.tradeItemStar3);
        mTradeItemStar4 = findViewById(R.id.tradeItemStar4);
        mTradeItemStar5 = findViewById(R.id.tradeItemStar5);
        mTradeItemPlantType = findViewById(R.id.tradeItemPlantType);
        mTradeItemDescription = findViewById(R.id.tradeItemDescription);
        mTradeItemPostPlantImage = findViewById(R.id.tradeItemPostPlantImage);
        mTradeItemPlantsWanted = findViewById(R.id.tradeItemPlantsWanted);
        mTradeItemTradeButton = findViewById(R.id.tradItemTradeButton);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        final String uid = intent.getStringExtra("uid");
        final String timeStamp = intent.getStringExtra("timeStamp");
        String userName = intent.getStringExtra("userName");
        int plantImage = intent.getIntExtra("plantImage",0);
        String plantType = intent.getStringExtra("plantType");
        String plantsWanted = intent.getStringExtra("plantsWanted");
        String description = intent.getStringExtra("description");
        int star1 = intent.getIntExtra("star1",0);
        int star2 = intent.getIntExtra("star2",0);
        int star3 = intent.getIntExtra("star3",0);
        int star4 = intent.getIntExtra("star4",0);
        int star5 = intent.getIntExtra("star5",0);

        // Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mTradeItemProfileName.setText(userName);
//        mTradeItemProfileImage.setImageResource(plantImage);
        mTradeItemPlantType.setText(plantType);
        mTradeItemPlantsWanted.setText(plantsWanted);
        mTradeItemDescription.setText(description);
        mTradeItemPostPlantImage.setImageResource(plantImage);
        mTradeItemStar1.setImageResource(star1);
        mTradeItemStar2.setImageResource(star2);
        mTradeItemStar3.setImageResource(star3);
        mTradeItemStar4.setImageResource(star4);
        mTradeItemStar5.setImageResource(star5);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        mTradeItemtBackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );


        // Read from the database
        myRef.child("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Call another function to update the UI on the screen by passing in datasnapshot which is like the info
//                updateName(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mTradeItemTradeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Update requests
                        myRef.child("Trading Platform").child(uid).child(timeStamp).child("Request").setValue("yes");
                        myRef.child("User Accounts").child(uid).child("Trading Platform").child(timeStamp).child("Request").setValue("yes");
                    }
                }
        );
    }

}
