package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ui.screens.AdminLogin;
import com.example.ui.screens.AdminTas;
import com.example.ui.screens.Parent_Search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    /**
     * This is the first screen, where admin and parent are login.
     * if the parent or admin is already login then the login page is not again shown.
     * It takes directly to the main page of admin or parent
     */

    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        //here its check if user is already login or not

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser =mAuth.getCurrentUser();
        if(currentUser != null){
            Intent newIntent = new Intent(getApplicationContext(), AdminTas.class);
            startActivity(newIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here it takes to admin page

                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    Intent adminIntent = new Intent(getApplicationContext(), AdminLogin.class);
                    startActivity(adminIntent);
                } else {
                    // Not Available...
                    Toast.makeText(MainActivity.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }


            }
        });

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {

            //here it takes to parent page

            @Override
            public void onClick(View v) {


                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...

                    Intent parentIntent = new Intent(getApplicationContext(), Parent_Search.class);
                    startActivity(parentIntent);
                } else {
                    // Not Available...
                    Toast.makeText(MainActivity.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
