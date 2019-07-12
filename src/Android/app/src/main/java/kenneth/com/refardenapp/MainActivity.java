package kenneth.com.refardenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button hereButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hereButton = (Button) findViewById(R.id.hereButton);
        hereButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openObservingPlantActivity();
            }
        });
    }

    public void openSignUpActivity(){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    public void openObservingPlantActivity() {
        Intent intent = new Intent(this, ObservingPlantActivity.class);
        startActivity(intent);
    }

}
