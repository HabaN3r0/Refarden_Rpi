package kenneth.com.refardenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button hereButton;

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
    }

    public void openSignUpActivity(){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

}
