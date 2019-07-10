package kenneth.com.refardenapp;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordInput);
        textInputConfirmPassword = findViewById(R.id.confirmPasswordInput);
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

        else if (passwordInput != confirmPasswordInput) {
            textInputConfirmPassword.setError("Password is not the same");
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

        String input = "Email: " + textInputEmail.getEditText().getText().toString().trim();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString().trim();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

    }
}
