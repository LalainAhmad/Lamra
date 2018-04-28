package com.lamra.smd.lamra;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lamra.smd.lamra.java.SignUp;




import java.util.Collections;
public class Login extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {


    ImageView logo;
    LinearLayout linearlayout1;
    private FirebaseAuth mAuth;
    private EditText Email1;
    private EditText Password;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mDetailTextView;
    private TextView mStatusTextView;
    DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();
        logo = (ImageView) findViewById(R.id.logo);
        linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        Email1 = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Email1.setOnKeyListener(this);
        Password.setOnKeyListener(this);
        Button Login = (Button) findViewById(R.id.Login);
        Button signUp = (Button) findViewById(R.id.signup);
        mDetailTextView = (TextView) findViewById(R.id.textView1);
        mStatusTextView = (TextView) findViewById(R.id.textView2);
        mProgressView = findViewById(R.id.progressbar);
        //       providers = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mProgressView = findViewById(R.id.login_progress);

        Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = Email1.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Email1.setError("Required.");
            valid = false;
        } else {

            Email1.setError(null);
        }
        String password = Password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Password.setError("Required.");
            valid = false;
        } else {
            Password.setError(null);
        }
        return valid;
    }

    protected void login() {
        Email1.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
        String email = Email1.getText().toString();
        String password = Password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            focusView = Password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Email1.setError(getString(R.string.error_field_required));
            focusView = Email1;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email1.setError(getString(R.string.error_invalid_email));
            focusView = Email1;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new LoginActivity.UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

//    protected void updateUI(FirebaseUser x)
//    {
//        Intent i = new Intent(this,ScreenHome.class);
//        i.putExtra("user",x);
//    }


//    public void onStart(final String email, String password) {
//        super.onStart();
//
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//        showProgressDialog();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(Login.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.logo || view.getId() == R.id.linearlayout1) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        int i = view.getId();

        if(i==R.id.Login)
            Toast.makeText(this, " SIGINIG INNN ", Toast.LENGTH_SHORT).show();
            signIn(Email1.getText().toString(), Password.getText().toString());

        if(i==R.id.signup)
            signUp(view);
    }

    public void onLogin(View view)
    {
        login(view);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }
        return false;
    }

    public void login(View v) {
        Intent i = new Intent(this, ScreenHome.class);
        startActivity(i);
    }

    public void signUp(View v) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    private void hideProgressDialog() {
        mProgressView.setVisibility(View.INVISIBLE);
    }

    private void showProgressDialog() {
        mProgressView.setVisibility(View.VISIBLE);
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
                            Toast.makeText(Login.this, "AAOO JEE", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText("FAILED");
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText("HOGYAAA " + user.getEmail() + user.isEmailVerified());

            mDetailTextView.setText("HOGYAAA hoga " + user.getEmail() + user.isEmailVerified());



    //        findViewById(R.id.email_password_buttons).setVisibility(View.GONE);

//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);

     //       findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);



      //      findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());

        } else {

            mStatusTextView.setText("signed out ");

            mDetailTextView.setText(null);



            findViewById(R.id.Login).setVisibility(View.VISIBLE);

            findViewById(R.id.signup).setVisibility(View.VISIBLE);

//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);

        }

    }

//
//    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            //           user.reload();
////            if(!user.isEmailVerified())
////            {
////                sendEmailVerification();
////            }
////            else {
//
//
//            mStatusTextView.setText("Sign in hogya " + user.getEmail() + user.isEmailVerified());
//            mDetailTextView.setText(user.getUid());
//            Email1.setVisibility(View.GONE);
//            Password.setVisibility(View.GONE);
//            findViewById(R.id.signup2).setVisibility(View.INVISIBLE);
//
////                if(user.isEmailVerified())
//            //              {
//            //      LOGIN2.setVisibility(View.VISIBLE);
//            login();
//            //            }
//            //               LOGIN2.setOnClickListener(this);
//
//            //}
//        } else {
//
//            mStatusTextView.setText("NAHIN HORAHA SIGN INNN KOI MASLA HAI " + user.getEmail() + user.isEmailVerified());
//            //        mStatusTextView.setText(R.string.signed_out);
//
//            mDetailTextView.setText(null);
////
////
//            //           findViewById(R.id.signup2).setVisibility(View.VISIBLE);
//
//
//            //
//
////           Intent i = new Intent(this,ScreenHome.class);
//            //          i.putExtra("user",user);
////
////            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
////
////            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//
//        }
//    }

}

