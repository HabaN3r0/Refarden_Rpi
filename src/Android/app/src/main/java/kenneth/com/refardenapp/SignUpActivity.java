package kenneth.com.refardenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private Button backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordInput);
        textInputConfirmPassword = findViewById(R.id.confirmPasswordInput);
        mAuth = FirebaseAuth.getInstance();

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

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String emailInput = textInputEmail.getEditText().getText().toString().trim();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, String.valueOf(user));
                            Toast.makeText(SignUpActivity.this, emailInput + " Account Created", Toast.LENGTH_SHORT).show();

                            // TODO: should display something to show that the user is logged into his account
                            // updateUI(user)
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

    public void closeSignUpActivity() {
        finish();
    }
}
