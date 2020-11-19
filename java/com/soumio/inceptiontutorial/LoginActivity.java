package com.soumio.inceptiontutorial;

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

public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button log;
    TextView sign;
    DatabaseReference ref;

    private static final String TAG = "LoginActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=findViewById(R.id.unameS);
        pass=findViewById(R.id.passS);
        log=findViewById(R.id.signupB);
        sign=findViewById(R.id.login_link);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });





    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        log.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
        String email = user.getText().toString();
        String password = pass.getText().toString();
        int iend = email.indexOf("@");
        final String subemail = email.substring(0, iend);

        // TODO: Implement your own authentication logic here.

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(subemail);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String p = dataSnapshot.child("pass").getValue().toString();
                    if (p.equals(pass.getText().toString())) {
                        onLoginSuccess();
                    } else {
                        onLoginFailed();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        if(email.equals("Ankit@gmail.com") && password.equals("1234abcd"))
//        {
//            onLoginSuccess();
//        }
//        else
//        {
//            onLoginFailed();
//        }


//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    public void onLoginSuccess() {
        log.setEnabled(true);
        String email = user.getText().toString();
        int iend = email.indexOf("@");
        final String subemail = email.substring(0, iend);
        Intent i=new Intent(LoginActivity.this,HomeActivity.class);
        i.putExtra("subemail",subemail);
        startActivity(i);
//        finish();
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }




    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


        log.setEnabled(true);
    }



    public boolean validate() {
        boolean valid = true;

        String email = user.getText().toString();
        String password = pass.getText().toString();

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