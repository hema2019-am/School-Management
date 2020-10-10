package com.example.ui.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ui.ConnectingReceview;
import com.example.ui.MainActivity;
import com.example.ui.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminTas extends AppCompatActivity {

    /**
     * After admin logged in, it brings here. It shows two button add and update students
     */

    private Toolbar mToolbarAddUpdates;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tas);

        mToolbarAddUpdates = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbarAddUpdates);

        mToolbarAddUpdates.setTitleTextColor(getResources().getColor(R.color.text_color));
        getSupportActionBar().setTitle("Admin");

        findViewById(R.id.btn_add_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    Intent add_details = new Intent(getApplicationContext(), com.example.ui.screens.add_details.class);
                    startActivity(add_details);
                } else {
                    // Not Available...
                    Toast.makeText(AdminTas.this, "Connected to internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


        findViewById(R.id.btn_update_marks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    Intent search_and_update = new Intent(getApplicationContext(), Search_and_Update.class);
                    startActivity(search_and_update);
                } else {
                    // Not Available...
                    Toast.makeText(AdminTas.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


        findViewById(R.id.btn_view_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    Intent viewStudent= new Intent(getApplicationContext(), Parent_Search.class);
                    startActivity(viewStudent);
                } else {
                    // Not Available...
                    Toast.makeText(AdminTas.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);

        MenuItem getItem = menu.findItem(R.id.get_item);
        if (getItem != null) {
            Button button = (Button) getItem.getActionView();
            button.setText("Logout");
            button.setTextColor(getResources().getColor(R.color.text_color));
            button.setBackground(getResources().getDrawable(R.drawable.main_button));
            button.setMinHeight(0);
            button.setMinimumHeight(0);

            button.setHeight(70);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(logout);
                }
            });
            //Set a ClickListener, the text,
            //the background color or something like that
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser =mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }
}
