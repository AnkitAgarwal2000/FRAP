package com.soumio.inceptiontutorial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PantryActivity extends AppCompatActivity {

    Button item;
    DatabaseReference reff;
    String subemail;
    long maxid;
    private String m_Text;
    Ingredient ingredient;
    String ingre[]=new String[20];
    TextView tria;
    Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        subemail=getIntent().getExtras().getString("subemail");
        reff= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(subemail);
        item=findViewById(R.id.addItem);
        ingredient=new Ingredient();
//        tria=findViewById(R.id.trial);


        RecyclerView programmingList = (RecyclerView)findViewById(R.id.Items);
        programmingList.setLayoutManager(new LinearLayoutManager(this));


        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            maxid=dataSnapshot.getChildrenCount();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(PantryActivity.this);
                builder.setTitle("Add Item");

// Set up the input
                final EditText input = new EditText(PantryActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
//                        itemCount();

                        ingredient.setItem(m_Text);
                        reff.child(String.valueOf(maxid+1)).setValue(ingredient);



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();



//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists())
//                        {
//                            int no=(int)dataSnapshot.getChildrenCount();
//                            for(int i=0;i<no;i++)
//                            {
//                                String noo = Integer.toString((i+1));
//                                ingre[i] = dataSnapshot.child(noo).child("item").getValue().toString();
//
//                            }
//                            String str[]=new String[no];
//                            int k=0;
//                            for(int i=no-1;i>=0;i--)
//                            {
//                                str[i]=ingre[k];
//                                k++;
//                            }
//                            for(int i=0;i<no;i++)
//                            {
//                                ingre[i]=str[i];
//                            }
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });





            }
        });
//        programmingList.setAdapter(new ProgrammingAdapter(ingre));




        reff= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(subemail);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    int no=(int)dataSnapshot.getChildrenCount();
                    for(int i=0;i<no;i++)
                    {
                        String noo = Integer.toString((i+1));
                        ingre[i] = dataSnapshot.child(noo).child("item").getValue().toString();

                    }
                    String str[]=new String[no];
                    int k=0;
                    for(int i=no-1;i>=0;i--)
                    {
                        str[i]=ingre[k];
                        k++;
                    }
                    for(int i=0;i<no;i++)
                    {
                        ingre[i]=str[i];
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        programmingList.setAdapter(new ProgrammingAdapter(ingre));






    }

//    public void displayItems()
//    {
////        itemCount();
//        final int no=(int)maxid;
//
//        final RecyclerView programmingList = (RecyclerView)findViewById(R.id.Items);
//        programmingList.setLayoutManager(new LinearLayoutManager(this));
//
//
//        reff= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(subemail);
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String ingre[]=new String[no];
//                if(dataSnapshot.exists())
//                {
//                    for(int i=0;i<no;i++)
//                    {
//
//                        String noo = Integer.toString((i));
//                        ingre[i] = dataSnapshot.child(noo).child("item").getValue().toString();
//
//                    }
//                    programmingList.setAdapter(new ProgrammingAdapter(ingre));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//
//    }

//    public void itemCount()
//    {
//        reff= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(subemail);
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    maxid=dataSnapshot.getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public void func(String str, int in)
//    {
//
//    }
}