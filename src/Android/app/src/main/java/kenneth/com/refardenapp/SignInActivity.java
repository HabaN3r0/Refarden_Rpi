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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private Button hereButton;
    private Button signInButton;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private boolean flag;
    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordInput);
        hereButton = (Button) findViewById(R.id.hereButton);
        mAuth = FirebaseAuth.getInstance();
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
                verifyAccount();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
//        updateUI(currentUser);
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

    private void validateAccount() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            user.getUid();

//                           updateUI(user);
                            openMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                           updateUI(null);
                        }

                        // ...
                    }
                });
    }

//    public void searchDatabase(DataSnapshot dataSnapshot) {
//        String emailInput = textInputEmail.getEditText().getText().toString().trim();
//        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
//        setFlag(false);
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            if (ds.getKey().equals(emailInput) && ds.getValue().equals(passwordInput)) {
//                setFlag(true);
//                Log.d(TAG, "In search database");
//            }
//
//        }
//    }
//
//    private void setFlag(boolean flag) {
//        this.flag = flag;
//        Log.d(TAG, "In set flag");
//    }
//
//    private boolean getFlag() {
//        Log.d(TAG, "In get flag");
//        return this.flag;
//    }

    public void openSignUpActivity(){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    public void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

   public void verifyAccount() {
       if (!validateEmail() | !validatePassword()) {
           return;
       }
       validateAccount();

   }

}
