package com.soumio.inceptiontutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    ImageButton pan,profile,scan;
    Button explore,saved;
    ImageView line1, line2;
    String subemail;
    TextView exp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        pan=findViewById(R.id.pantryB);
        profile=findViewById(R.id.profileB);
        scan=findViewById(R.id.scanB);
        explore=findViewById(R.id.explore);
        saved=findViewById(R.id.saved);
        line1=findViewById(R.id.line1);
        line2=findViewById(R.id.line2);
        exp=findViewById(R.id.exp);


        line2.setVisibility(View.INVISIBLE);

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.INVISIBLE);
                exp.setText("No Saved items!");
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                exp.setText("No recipes yet!");
            }
        });

        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subemail=getIntent().getExtras().getString("subemail");
                Intent i=new Intent(HomeActivity.this,PantryActivity.class);
                i.putExtra("subemail",subemail);
                startActivity(i);
            }
        });









        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,ChooseModel.class);
                i.putExtra("subemail",subemail);
                startActivity(i);
            }
        });



    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
//        finish();
    }
}