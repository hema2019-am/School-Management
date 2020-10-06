package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ui.ConnectingReceview;
import com.example.ui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminLogin extends AppCompatActivity {

    /**
     * Here, we have to add admin user manually in the firebase, first for security.
     */


    EditText edt_email, edt_password;
    String admin_email, admin_password, admin_name, firebaseEmail;
    Button btn_admin_login;
    ProgressDialog LoginProgress;

    FirebaseAuth mAuth;

    DatabaseReference mAdminLogin;
    TextView mAdminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        getAdminView();



        mAuth = FirebaseAuth.getInstance();  // firebase auth to check the authentication of user


        LoginProgress = new ProgressDialog(this);

        btn_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    admin_email = edt_email.getText().toString();
                    admin_password = edt_password.getText().toString();
                    admin_name = mAdminName.getText().toString();

                    if (!TextUtils.isEmpty(admin_email) || !TextUtils.isEmpty(admin_password)) {
                        LoginProgress.setTitle("Logging In");
                        LoginProgress.setMessage("Please wait while we check your credentials");
                        LoginProgress.setCanceledOnTouchOutside(false);
                        LoginProgress.show();

                        loginUser(admin_email, admin_password);
                    }
                } else {
                    // Not Available...
                    LoginProgress.hide();
                    Toast.makeText(AdminLogin.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void getAdminView() {
        edt_email = findViewById(R.id.edt_admin_email);
        edt_password = findViewById(R.id.edt_admin_password);
        btn_admin_login = findViewById(R.id.btn_admin_login);
        mAdminName = findViewById(R.id.edt_admin_name);

    }

    private void loginUser(final String mEmail, String password) {

        /**
         * user is login if he is the admin.
         */

        mAuth.signInWithEmailAndPassword(mEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    admin_name = mAdminName.getText().toString();

                    mAdminLogin = FirebaseDatabase.getInstance().getReference().child("Admin");
                    mAdminLogin.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(admin_name)) {
                                firebaseEmail = snapshot.child(admin_name).child("email").getValue().toString();
                                if (firebaseEmail.equals(mEmail)) {
                                    LoginProgress.dismiss();
                                    Intent mainImtent = new Intent(getApplicationContext(), AdminTas.class);
                                    mainImtent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainImtent);
                                    finish();
                                } else {
                                    LoginProgress.hide();
                                    Toast.makeText(AdminLogin.this, "can't access", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminLogin.this, "Can't access", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    // If sign in fails, display a message to the user.
                    LoginProgress.hide();
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
