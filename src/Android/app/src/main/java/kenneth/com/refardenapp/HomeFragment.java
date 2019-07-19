package kenneth.com.refardenapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by 1002215 on 19/7/19.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "Home Fragment";
    private TextView textViewTemperature;
    private TextView textViewSolution;
    private TextView textViewHumidity;
    private TextView textViewLight;
    private TextView textViewWater;
    private TextView textViewPh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewTemperature = view.findViewById(R.id.temperature);
        textViewSolution = view.findViewById(R.id.solution);
        textViewHumidity = view.findViewById(R.id.humidity);
        textViewLight = view.findViewById(R.id.light);
        textViewWater = view.findViewById(R.id.water);
        textViewPh = view.findViewById(R.id.ph);

        //Set Fragment Title
        getActivity().setTitle("Home");

        // Creating database instance and reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Growing Conditions");

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

//        return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
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
