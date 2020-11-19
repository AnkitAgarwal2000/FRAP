package com.soumio.inceptiontutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    EditText user, pass, uname;
    TextView log_link;
    Button signB;
    DatabaseReference reff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        user=findViewById(R.id.unameS);
        pass=findViewById(R.id.passS);
        uname=findViewById(R.id.NameS);
        log_link=findViewById(R.id.login_link);
        signB=findViewById(R.id.signupB);
        reff= FirebaseDatabase.getInstance().getReference().child("Users");
        member=new Member();


        signB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });


        log_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signB.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = uname.getText().toString();
        final String email = user.getText().toString();
        final String password = pass.getText().toString();
        int iend=email.indexOf("@");
        final String subemail=email.substring(0,iend);



        // TODO: Implement your own signup logic here.

        reff.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            user.setError("Id already exists");
                        }
                        else
                        {
                            member.setName(name);
                            member.setEmail(email);
                            member.setPass(password);
                            reff.child(subemail).setValue(member);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        signB.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signB.setEnabled(true);
    }




    public boolean validate() {
        boolean valid = true;

        String name = uname.getText().toString();
        String email = user.getText().toString();
        String password = pass.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            uname.setError("at least 3 characters");
            valid = false;
        } else {
            uname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user.setError("enter a valid email address");
            valid = false;
        } else {
            user.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pass.setError(null);
        }

        return valid;
    }

}