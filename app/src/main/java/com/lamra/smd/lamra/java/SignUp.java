package com.lamra.smd.lamra.java;
import android.content.Context;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamra.smd.lamra.R;
//import com.lamra.smd.lamra.java.User;
import com.lamra.smd.lamra.java.UserDao;


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
    private Button button;


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

    @Override

    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.signup2) {
            final FirebaseUser user = mAuth.getCurrentUser();
            user.reload();


            createAccount(Email1.getText().toString(), Password.getText().toString());
            if (!user.isEmailVerified())
                sendEmailVerification();


        }
    }

    protected Boolean checkForFirstTime(String userId) {
        mDatabase.child("users").child(userId).once("value", function(snapshot) {
            var exists = (snapshot.val() != = null);
           return true;
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        c=this;
        Email1=(EditText) findViewById(R.id.emailID);
        Password=(EditText) findViewById(R.id.pw);
        mDetailTextView=(TextView) findViewById(R.id.textview1);
        mStatusTextView=(TextView) findViewById(R.id.textview2);
        mProgressView = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.signup2).setOnClickListener(this);

    }
        //   Write a message to the database

//
//User user = new User();
//        user.setPhoneNo("03005462344");
//        user.setEmail("mahnoor@gmail.com");
//        user.setName("Mahnoor Khan");
//        user.setUsername("mano123");
//        user.setProfilePic("abc");
//
//
//        mDatabase.child("users").child("555").setValue(user);
//
//        mDatabase.child("users").child("567").setValue(user);
//
//        mDatabase.child("users").child("567").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                User u = dataSnapshot.getValue(User.class);
//                Toast.makeText(SignUp.this, "User's email:" + u.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



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

                            Toast.makeText(SignUp.this,

                                    "Failed to send verification email.",

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

    private void hideProgressDialog()
    {
        mProgressView.setVisibility(View.INVISIBLE);
    }
    private void showProgressDialog()
    {
        mProgressView.setVisibility(View.VISIBLE);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText("emailpassword_status_fmt"+ user.getEmail()+ user.isEmailVerified());
            mDetailTextView.setText("firebase_status_fmt"+ user.getUid());
            Email1.setVisibility(View.GONE);
            Password.setVisibility(View.GONE);
        } else {

    //        mStatusTextView.setText(R.string.signed_out);

            mDetailTextView.setText(null);
//
//
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);

        }

    }
    public void createAccount(String email, String password) {
            Log.d(TAG, "createAccount:" + email);
            if (!validateForm()) {
                return;
            }
            showProgressDialog();


            // [START create_user_with_email]

            mAuth.createUserWithEmailAndPassword(email, password)

                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(c, "createUserWithEmail1:success", Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();

                                updateUI(user);

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

            // [END create_user_with_email]

        }

}