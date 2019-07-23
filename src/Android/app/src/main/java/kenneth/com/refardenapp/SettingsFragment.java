package kenneth.com.refardenapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1002215 on 20/7/19.
 */

public class SettingsFragment extends Fragment {

    private Switch autoSwitch;
    private SeekBar tempSeek;
    private SeekBar lightSeek;
    private SeekBar concSeek;
    private SeekBar freqSeek;
    private TextView tempPercent;
    private TextView lightPercent;
    private TextView concPercent;
    private TextView freqPercent;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Set Fragment Title
        getActivity().setTitle("Settings");

        //Initialise the seekbars
        setTempSeek(view);
        setLightSeek(view);
        setConcSeek(view);
        setFreqSeek(view);

        return view;
    }

    public void setTempSeek(View view){
        int maxTemp = 30;
        tempSeek = view.findViewById(R.id.settingsTemperatureSeek);
        tempSeek.setMax(maxTemp);
        tempPercent = view.findViewById(R.id.settingsTemperaturePercent);
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
                    }
                }
        );

    }

    public void setLightSeek(View view){
        int maxLight = 1000;
        lightSeek = view.findViewById(R.id.settingsLightSeek);
        lightSeek.setMax(maxLight);
        lightPercent = view.findViewById(R.id.settingsLightPercent);
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
                        //TODO: Send info to firebase
                    }
                }
        );

    }

    public void setConcSeek(View view){
        int maxConc = 100;
        concSeek = view.findViewById(R.id.settingsConcentrationSeek);
        concSeek.setMax(maxConc);
        concPercent = view.findViewById(R.id.settingsConcentrationPercent);
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
                        //TODO: Send info to firebase
                    }
                }
        );
    }

    public void setFreqSeek(View view){
        int maxFreq = 60;
        freqSeek = view.findViewById(R.id.settingsWaterFrequencySeek);
        freqSeek.setMax(maxFreq);
        freqPercent = view.findViewById(R.id.settingsWaterFrequencyPercent);
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
                        //TODO: Send info to firebase
                    }
                }
        );
    }

}
