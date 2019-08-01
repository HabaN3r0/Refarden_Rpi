package kenneth.com.refardenapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1002215 on 20/7/19.
 */

public class SettingsFragment extends Fragment {

    private static final String TAG = "Settings Fragment";
    private Switch autoSwitch;
    private SeekBar tempSeek;
    private SeekBar lightSeek;
    private SeekBar concSeek;
    private SeekBar freqSeek;
    private TextView tempPercent;
    private TextView lightPercent;
    private TextView concPercent;
    private TextView freqPercent;
    private LinearLayout settingsTemp;
    private LinearLayout settingsLight;
    private LinearLayout settingsConc;
    private LinearLayout settingsFreq;
    private FirebaseAuth mAuth;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Write a message to the database
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User Accounts").child(mAuth.getCurrentUser().getUid()).child("Settings Conditions");

        //Set Fragment Title
        getActivity().setTitle("Settings");

        autoSwitch = view.findViewById(R.id.settingsAutomateSwitch);
        settingsTemp = view.findViewById(R.id.settingsTemperature);
        tempSeek = view.findViewById(R.id.settingsTemperatureSeek);
        tempPercent = view.findViewById(R.id.settingsTemperaturePercent);

        settingsLight = view.findViewById(R.id.settingsLight);
        lightSeek = view.findViewById(R.id.settingsLightSeek);
        lightPercent = view.findViewById(R.id.settingsLightPercent);


        settingsConc = view.findViewById(R.id.settingsConcentration);
        concSeek = view.findViewById(R.id.settingsConcentrationSeek);
        concPercent = view.findViewById(R.id.settingsConcentrationPercent);

        settingsFreq = view.findViewById(R.id.settingsWaterFrequency);
        freqSeek = view.findViewById(R.id.settingsWaterFrequencySeek);
        freqPercent = view.findViewById(R.id.settingsWaterFrequencyPercent);

        //Check switch's last state
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Call another function to update the UI on the screen by passing in datasnapshot which is like the info
                checkAutomate(dataSnapshot, view, myRef);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Listener for automate switch to trigger the seekbars
        autoSwitch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (autoSwitch.isChecked()){
                            settingsTemp.setVisibility(View.INVISIBLE);
                            settingsLight.setVisibility(View.INVISIBLE);
                            settingsConc.setVisibility(View.INVISIBLE);
                            settingsFreq.setVisibility(View.INVISIBLE);
                            myRef.child("Automate").setValue("on");
                        }
                        else {
                            settingsTemp.setVisibility(View.VISIBLE);
                            settingsLight.setVisibility(View.VISIBLE);
                            settingsConc.setVisibility(View.VISIBLE);
                            settingsFreq.setVisibility(View.VISIBLE);
                            myRef.child("Automate").setValue("off");

                            //Initialise the seekbars
                            setTempSeek(view, myRef);
                            setLightSeek(view, myRef);
                            setConcSeek(view, myRef);
                            setFreqSeek(view, myRef);

                        }
                    }
                }
        );

        return view;
    }

    public void setTempSeek(View view, final DatabaseReference myRef){
        int maxTemp = 30;
        tempSeek.setMax(maxTemp);
        tempPercent.setText(tempSeek.getProgress() + "°C");

        tempSeek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        tempPercent.setText(progressValue + "°C");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        tempPercent.setText(progressValue + "°C");
                        //TODO: Send info to firebase
                        myRef.child("Temperature").setValue(progressValue);

                    }
                }
        );

    }

    public void checkAutomate(DataSnapshot dataSnapshot, View view, DatabaseReference myRef){
        String automateCheck = "on";
        String temperatureCheck = "0";
        String lightCheck = "0";
        String concentrationCheck = "0";
        String frequencyCheck = "0";

        // Extract settings info into string variables
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d(TAG, "ds is:  " + ds.getKey());
            if (ds.getKey().equals("Automate")) {
                automateCheck = ds.getValue().toString();
            } else if (ds.getKey().equals("Temperature")) {
                temperatureCheck = ds.getValue().toString();
            } else if (ds.getKey().equals("Light")) {
                lightCheck = ds.getValue().toString();
            } else if (ds.getKey().equals("Concentration")) {
                concentrationCheck = ds.getValue().toString();
            } else if (ds.getKey().equals("Frequency")) {
                frequencyCheck = ds.getValue().toString();
            } else {}
        }

        if (automateCheck.equals("on")) {
            autoSwitch.setChecked(true);
        } else {
            autoSwitch.setChecked(false);
            settingsTemp.setVisibility(View.VISIBLE);
            setTempSeek(view, myRef);
            settingsLight.setVisibility(View.VISIBLE);
            setLightSeek(view, myRef);
            settingsConc.setVisibility(View.VISIBLE);
            setConcSeek(view, myRef);
            settingsFreq.setVisibility(View.VISIBLE);
            setFreqSeek(view, myRef);
        }

        tempSeek.setProgress(Integer.valueOf(temperatureCheck));
        lightSeek.setProgress(Integer.valueOf(lightCheck));
        concSeek.setProgress(Integer.valueOf(concentrationCheck));
        freqSeek.setProgress(Integer.valueOf(frequencyCheck));
    }

    public void setLightSeek(View view, final DatabaseReference myRef){
        int maxLight = 1000;
        lightSeek.setMax(maxLight);
        lightPercent.setText(lightSeek.getProgress() + "lm");

        lightSeek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        lightPercent.setText(progressValue + "lm");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        lightPercent.setText(progressValue + "lm");

                        myRef.child("Light").setValue(progressValue);
                    }
                }
        );

    }

    public void setConcSeek(View view, final DatabaseReference myRef){
        int maxConc = 100;
        concSeek.setMax(maxConc);
        concPercent.setText(concSeek.getProgress() + "%");

        concSeek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        concPercent.setText(progressValue + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        concPercent.setText(progressValue + "%");

                        myRef.child("Concentration").setValue(progressValue);
                    }
                }
        );
    }

    public void setFreqSeek(View view, final DatabaseReference myRef){
        int maxFreq = 60;
        freqSeek.setMax(maxFreq);
        freqPercent.setText(freqSeek.getProgress() + "sec");

        freqSeek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        freqPercent.setText(progressValue + "sec");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        freqPercent.setText(progressValue + "sec");

                        myRef.child("Frequency").setValue(progressValue);
                    }
                }
        );
    }

}
