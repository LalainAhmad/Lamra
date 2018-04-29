package com.lamra.smd.lamra;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lamra.smd.lamra.java.UserF;
//import com.lamra.smd.lamra.java.User;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText Email1;
    private EditText Password;
    private EditText Bio;
    private EditText Name;
    private EditText userame;
    private EditText PhoneNo;

    private static final String TAG = "EmailPassword";
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
            createAccount(Email1.getText().toString(), Password.getText().toString());
        }
    }

   // @Override
 //   public void onClick(View v) {


//          Toast.makeText(c,"signupbuttoncalled",Toast.LENGTH_SHORT).show();
//  //        if(Email1.getText()!=null)
//        Toast.makeText(c,Email1.getText().toString(),Toast.LENGTH_SHORT).show();
//            createAccount(Email1.getText().toString(), Password.getText().toString());
//        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        c=this;

        Email1=(EditText) findViewById(R.id.emailID);
        Password=(EditText) findViewById(R.id.pw);
        Bio=(EditText) findViewById(R.id.bio);
        Name=(EditText) findViewById(R.id.fullname);
        userame=(EditText) findViewById(R.id.username);
        PhoneNo=(EditText) findViewById(R.id.phoneNo);



        findViewById(R.id.signup2).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }

    private void sendEmailVerification() {

        findViewById(R.id.signup2).setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()

                .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {

                        // [START_EXCLUDE]

                        // Re-enable button

                        findViewById(R.id.signup2).setEnabled(true);



                        if (task.isSuccessful()) {

                            Toast.makeText(c,

                                    "Verification email sent to " + user.getEmail(),

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(c,

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

    public void createAccount(String email, final String password) {
            if (!validateForm()) {
                return;
            }

            // [START create_user_with_email]

            mAuth.createUserWithEmailAndPassword(email, password)

                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(c, "createUserWith "+Email1.getText().toString()+" :success", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                UserF u = new UserF();
                                u.setUid(user.getUid());
                                u.setName(Name.getText().toString());
                                u.setUsername(userame.getText().toString());
                                u.setEmail(Email1.getText().toString());
                                u.setPassword(Password.getText().toString());
                                u.setBio(Bio.getText().toString());
                                u.setPhoneNo(PhoneNo.getText().toString());

                                mDatabase.child("users").child(user.getUid()).setValue(u);

                            } else {

                                Toast.makeText(c, "Authentication failed.", Toast.LENGTH_SHORT).show();


                            }


                            // [START_EXCLUDE]



                            // [END_EXCLUDE]

                        }

                    });

            // [END create_user_with_email]

        }

}