package com.loya.android.arvark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loya.android.arvark.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity.this";
    private Button loginButton;
    private TextView forgotPassword;
    private TextView signUp;
    private EditText mLoginEmailField;
    private EditText mLoginPasswordField;


    private ProgressBar loadingIndicator;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.sign_in_btn);
        forgotPassword = (TextView) findViewById(R.id.forgot_password_text);
        mLoginEmailField = (EditText) findViewById(R.id.email_edit_text);
        mLoginPasswordField = (EditText) findViewById(R.id.password_edit_text);
        loadingIndicator = (ProgressBar) findViewById(R.id.login_progress_bar);
        signUp = (TextView)findViewById(R.id.sign_up_text);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mLoginEmailField.getText().toString(), mLoginPasswordField.getText().toString());
               // startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Sign In successful",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void hideProgressDialog() {
        loadingIndicator.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mLoginEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mLoginEmailField.setError("Required.");
            valid = false;
        } else {
            mLoginEmailField.setError(null);
        }

        String password = mLoginPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mLoginPasswordField.setError("Required.");
            valid = false;
        } else {
            mLoginPasswordField.setError(null);
        }

        return valid;
    }

}
