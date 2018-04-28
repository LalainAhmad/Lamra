package com.lamra.smd.lamra.java;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamra.smd.lamra.Login;
import com.lamra.smd.lamra.R;
//import com.lamra.smd.lamra.java.User;
import com.lamra.smd.lamra.ScreenHome;
import com.lamra.smd.lamra.java.UserDao;

import java.util.ArrayList;
import java.util.List;


public class SignUp extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText Email1;
    private EditText Password;
    private View mProgressView;
    private static final String TAG = "EmailPassword";
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private Context c;
    private Button LOGIN2;
//    List<AuthUI.IdpConfig> providers;
    public EditText getEmail1() {
        return Email1;
    }
    public void setEmail1(EditText email1) {
        Email1 = email1;
    }
    public EditText getPassword() {
        return Password;
    }
    public void setPassword(EditText password) {
        Password = password;
    }
    public void onClickLogIn(View v) {
        login();
    }

    @Override
    public  void onClick(View v)
    {
        final FirebaseUser user = mAuth.getCurrentUser();
        int i = v.getId();
        if (i == R.id.signup2) {
            createAccount(Email1.getText().toString(), Password.getText().toString());
       }
       if(i==R.id.loginthen)
       {
           if(user.isEmailVerified())
           {
               login();
           }
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        c = this;
        Email1 = (EditText) findViewById(R.id.emailID);
        Password = (EditText) findViewById(R.id.pw);
        LOGIN2 = (Button) findViewById(R.id.loginthen);
        mDetailTextView = (TextView) findViewById(R.id.textview1);
        mStatusTextView = (TextView) findViewById(R.id.textview2);
        mProgressView = findViewById(R.id.progressbar);
        //       providers = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.signup2).setOnClickListener(this);
        LOGIN2.setOnClickListener(this);
    }

    private void sendEmailVerification() {
        findViewById(R.id.signup2).setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                findViewById(R.id.signup2).setEnabled(true);
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this,
                            "Verification email sent to " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(SignUp.this, "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show();
                }
                // [END_EXCLUDE]
            }
        });
       // [END send_email_verification]
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

    private void hideProgressDialog() {
        mProgressView.setVisibility(View.INVISIBLE);
    }

    private void showProgressDialog() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {

                mStatusTextView.setText("HOGYAAA " + user.getEmail() + user.isEmailVerified());
                mDetailTextView.setText(user.getUid());
                login();

        } else {

            mStatusTextView.setText("NAHIN HOTA " + user.getEmail() + user.isEmailVerified());
            //        mStatusTextView.setText(R.string.signed_out);

            mDetailTextView.setText(null);

        }
    }
    public void login() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }


//    public int checkFirebaseForUsername(String email123) {
//        final int[] flag = {0};
//        final String email = email123;
//        Log.e("tag", "working now");
//        //flag[0]=1;
//        //       DatabaseReference mTest = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("users").child(email).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//            //   @Override
//            public void onDataChanged(DataSnapshot dataSnapshot) {
//                Log.e("tag", "checking");
//
//                if (dataSnapshot.exists()) {
//                    Log.e("tag", "exists");
//                    flag[0] = 1;
//                }
//            }
//
//            //    @Override
//            public void onCancelled(DataSnapshot datasnapshot) {
//            }
//        });
//
//        if (flag[0] == 1)
//            return 1;
//        else
//            return 0;
//    }
//

    public void createAccount(final String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START create_user_with_email]
//        if (checkFirebaseForUsername(email) == 0) {
//
//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//
//                                Toast.makeText(getBaseContext(), "inside", Toast.LENGTH_LONG).show();
//
//                                //               User newUser = new User();
//                                //             newUser.setUId(mAuth.getCurrentUser().getUid());
//                                //           newUser.setUsername(username);
//                                //         newUser.setEmailId(email);
//
//                                try {
//                                    mDatabase.child("users").child(email).setValue(mAuth.getUid());
//                                } catch (Exception e) {
//                                    Toast.makeText(SignUp.this, "error while inserting", Toast.LENGTH_LONG).show();
//                                }
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
//                                //      builder.setTitle(R.string.signup_success)
//                                //            .setPositiveButton(R.string.login_button_label, new DialogInterface.OnClickListener() {
//
//                                //               @Override
//                                //             public void onClick(DialogInterface dialogInterface, int i) {
////
//                                Intent intent = new Intent(SignUp.this, Login.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }
//                    });
// //           AlertDialog dialog = builder.create();
//   //         dialog.show();
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
//            //               builder.setTitle(R.string.signup_error_title)
//            //                     .setPositiveButton(android.R.string.ok, null);
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//    }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(c, "createUserWithEmail1:success", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                sendEmailVerification();

                                //                           if (user.isEmailVerified()) {
                                updateUI(user);
                                //                         } else {
                                //                           Toast.makeText(c,"Verify your email id",Toast.LENGTH_SHORT).show();

                                //                     }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(c, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                            // [START_EXCLUDE]
                            hideProgressDialog();
                            // [END_EXCLUDE]
                        }

                    });
            // [END create_user_with_email

        }
 //   }
}