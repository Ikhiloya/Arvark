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
import com.google.firebase.auth.FirebaseAuth;
import com.loya.android.arvark.R;

public class ResetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText resetEmailField;
    private TextView rememberText;
    private Button resetButton;

    private FirebaseAuth mAuth;

    private ProgressBar resetProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetEmailField = (EditText) findViewById(R.id.reset_email_edit_text);
        rememberText = (TextView) findViewById(R.id.remember_text);
        resetButton = (Button) findViewById(R.id.reset_button);
        resetProgressBar =(ProgressBar) findViewById(R.id.reset_loading_indicator);
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEmail(resetEmailField.getText().toString());
            }
        });

        rememberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });

    }


    private void resetEmail(String email) {
        Log.d(TAG, "resetEmail:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                            finish();
                        }
                        hideProgressDialog();
                    }

                });

    }

    private void hideProgressDialog() {
        resetProgressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        resetProgressBar.setVisibility(View.VISIBLE);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = resetEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            resetEmailField.setError("Required.");
            valid = false;
        } else {
            resetEmailField.setError(null);
        }

        return valid;
    }
}
