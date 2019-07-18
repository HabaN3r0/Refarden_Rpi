package kenneth.com.refardenapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private TextView textViewTemperature;
    private TextView textViewSolution;
    private TextView textViewHumidity;
    private TextView textViewLight;
    private TextView textViewWater;
    private TextView textViewPh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTemperature = findViewById(R.id.temperature);
        textViewSolution = findViewById(R.id.solution);
        textViewHumidity = findViewById(R.id.humidity);
        textViewLight = findViewById(R.id.light);
        textViewWater = findViewById(R.id.water);
        textViewPh = findViewById(R.id.ph);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Growing Conditions");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                updateUi(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void updateUi(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Log.d(TAG, "ds is:  " + ds.getKey());
            GrowingConditions growCond = new GrowingConditions();
            if (ds.getKey().equals("Temperature")) {
                textViewTemperature.setText(ds.getValue()+ "°C");
            } else if (ds.getKey().equals("Solution")) {
                textViewSolution.setText(ds.getValue() + "/1000 ml");
            } else if (ds.getKey().equals("Humidity")) {
                textViewHumidity.setText(ds.getValue() + "%");
            } else if (ds.getKey().equals("Light")) {
                textViewLight.setText(ds.getValue() + "lm");
            } else if (ds.getKey().equals("Water")) {
                textViewWater.setText(ds.getValue() + "/1000 ml");
            } else if (ds.getKey().equals("Ph")) {
                textViewPh.setText(ds.getValue() + "%");
            }

        }
    }
}
