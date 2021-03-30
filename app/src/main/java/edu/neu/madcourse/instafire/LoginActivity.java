package edu.neu.madcourse.instafire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText getEmail;
    EditText getPassword;
    String email;
    String password;
    FirebaseAuth auth;
    String TAG = "Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Firebase auth check - object on which we can check user's credentials
        // check as soon as activity is created
        auth = FirebaseAuth.getInstance();
        // means firebasee has stored the session in the app already
        // Go straight to posts activity
        if (auth.getCurrentUser() != null){
            goPostsActivity();
        }

        btnLogin = (Button)findViewById(R.id.btnLogin);
        getEmail = (EditText)findViewById(R.id.getEmail);
        getPassword = (EditText)findViewById(R.id.getPassword);
    }

    public void onClick(View view){
        // disables button after user clicks it once
        btnLogin.setEnabled(false);
        email = getEmail.getText().toString();
        password = getPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                // enables button once confirmed successful login
                btnLogin.setEnabled(true);
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                    goPostsActivity();
                } else {
                    Log.i(TAG, "Sign In with Email failed", task.getException());
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goPostsActivity(){
        Log.i(TAG,"goPostsActivity");
        // opens postsActivity
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
        // finish login activity so that back button on next activity doesn't bring you back to login
        // no longer part of backstack
        finish();
    }
}