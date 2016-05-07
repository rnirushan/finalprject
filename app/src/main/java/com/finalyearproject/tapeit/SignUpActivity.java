package com.finalyearproject.tapeit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyearproject.controllers.UserController;
import com.finalyearproject.dto.User;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int REQUEST_SIGNIN = 0;

    public EditText txtName;
    public EditText txtEmail;
    public EditText txtPassword;
    public EditText txtConfirmPassword;
    public Button btnSignup;
    public TextView lnkLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        lnkLogin = (TextView) findViewById(R.id.lnkLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        lnkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivityForResult(intent, REQUEST_SIGNIN);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        addUser();
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void addUser(){
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        UserController controller = new UserController(this);
        controller.registerUser(new User(name, email, password));
    }

    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        btnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            txtName.setError("at least 3 characters");
            valid = false;
        } else {
            txtName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        if(!password.equals(confirmPassword)){
            txtConfirmPassword.setError("Password should be matched");
            valid = false;
        } else {
            txtConfirmPassword.setError(null);
        }

        return valid;
    }
}
