package kenneth.com.refardenapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
//    private TextInputLayout textInputName;
    private TextInputLayout textInputName;
    private TextInputLayout textInputBirth;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private Button backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        textInputName = findViewById(R.id.nameInput);
        textInputBirth = findViewById(R.id.birthInput);
        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordInput);
        textInputConfirmPassword = findViewById(R.id.confirmPasswordInput);
        mAuth = FirebaseAuth.getInstance();

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
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
            textInputConfirmPassword.setError("Passwords are not the same!");

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
        String nameInput = textInputName.getEditText().getText().toString().trim();
        String birthInput = textInputBirth.getEditText().getText().toString().trim();
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
//        String nameInput = textInputName.getEditText().getText().toString().trim();

        String input = "Email: " + nameInput;
        input += "\n";
        input += "Password: " + birthInput;
        Log.d(TAG, "input: " + input);

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String emailInput = textInputEmail.getEditText().getText().toString().trim();
                            String nameInput = textInputName.getEditText().getText().toString().trim();
                            String birthInput = textInputBirth.getEditText().getText().toString().trim();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, String.valueOf(user));
                            Toast.makeText(SignUpActivity.this, emailInput + " account Created", Toast.LENGTH_SHORT).show();

                            // TODO: should display something to show that the user is logged into his account
                            // updateUI(user)
                            createFirebaseAccount(user, nameInput, birthInput);

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }

                    }
                });
//        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

    }

    private void createFirebaseAccount(FirebaseUser currentUser, String name, String birth) {

        //Create base empty database template upon account creation.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User Accounts").child(currentUser.getUid());

        //Get current date to save the start date in the database
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime startDate = LocalDateTime.now();

        myRef.child("Profile").child("Name").setValue(name);
        myRef.child("Profile").child("Birthday").setValue(birth);
        myRef.child("Profile").child("UID").setValue(currentUser.getUid());
        myRef.child("Profile").child("Email").setValue(currentUser.getEmail());
        myRef.child("Profile").child("Start Date").setValue(dtf.format(startDate));
        myRef.child("Profile").child("Stars").setValue(4);

        myRef.child("Growing Conditions").child("Temperature").setValue(0);
        myRef.child("Growing Conditions").child("Solution").setValue(0);
        myRef.child("Growing Conditions").child("Humidity").setValue(0);
        myRef.child("Growing Conditions").child("Light").setValue(0);
        myRef.child("Growing Conditions").child("Water").setValue(0);
        myRef.child("Growing Conditions").child("Ph").setValue(0);

        myRef.child("Settings Conditions").child("Automate").setValue("on");
        myRef.child("Settings Conditions").child("Temperature").setValue(0);
        myRef.child("Settings Conditions").child("Light").setValue(0);
        myRef.child("Settings Conditions").child("Concentration").setValue(0);
        myRef.child("Settings Conditions").child("Frequency").setValue(0);

        //TODO: Add in all the stuff needed for an account
    }
}
