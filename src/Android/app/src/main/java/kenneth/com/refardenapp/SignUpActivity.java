package kenneth.com.refardenapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordInput);
        textInputConfirmPassword = findViewById(R.id.confirmPasswordInput);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                closeSignUpActivity();
            }
        });
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        }
        else {
            textInputEmail.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        }

        else {
            textInputPassword.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        }

        else if (!passwordInput.equals(confirmPasswordInput)) {
            textInputConfirmPassword.setError("Password is not the same" + passwordInput + " " + confirmPasswordInput);

            return false;

        }

        else {
            textInputConfirmPassword.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    public void confirmInput(View v) {

        if (!validateEmail() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        String input = "Email: " + emailInput;
        input += "\n";
        input += "Password: " + passwordInput;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        myRef.child("User Accounts").child(emailInput).setValue(passwordInput);

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

    }

    public void closeSignUpActivity() {
        finish();
    }
}
