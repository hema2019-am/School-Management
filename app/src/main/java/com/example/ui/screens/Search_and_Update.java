package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ui.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_and_Update extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * Student is search, for which update is taken place
     */

    private Toolbar mToolbarSearchUpdate;
    EditText edt_name, edt_roll, edt_class;
    Button btn_update_search;
    String StudentName, StudentRoll, StudentClass, FatherName, MotherName, Contact, StudentExam, Class, Exam;
    String English, EnglishLiterature, EnglishHandwrting, Hindi, Conversation, Mathematics, PT, Rhymes, Drawing, EVS, Bengali, Moral_ed, GK;
    DatabaseReference mStudentRef, mclassRef;
    String Name;
    Spinner spinner_class, spinner_examType;

    String[] SchoolClass = { "Nursery", "LKG", "UKG", "1", "2", "3", "4", "5" };
    String[] ExamType = { "UT 1", "Half Yearly", "UT 2", "Final"};

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_update);

        mToolbarSearchUpdate = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbarSearchUpdate);

        mToolbarSearchUpdate.setTitleTextColor(getResources().getColor(R.color.text_color));
        getSupportActionBar().setTitle("Search Student");

        edt_name = findViewById(R.id.edt_update_name);
        edt_roll = findViewById(R.id.edt_update_roll_call);
        spinner_class = findViewById(R.id.edt_update_class_update);
        spinner_examType = findViewById(R.id.edt_Search_examType);
        btn_update_search = findViewById(R.id.btn_search_update_student);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait to get the data");
        mProgressDialog.setCanceledOnTouchOutside(false);


        final ArrayAdapter<String> Class = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SchoolClass);
        Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_class.setAdapter(Class);
        spinner_class.setOnItemSelectedListener(this);

        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);

        btn_update_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                StudentName = edt_name.getText().toString();
                StudentRoll = edt_roll.getText().toString();


                if (TextUtils.isEmpty(StudentName) || TextUtils.isEmpty(StudentRoll)) {
                    mProgressDialog.hide();
                    Toast.makeText(Search_and_Update.this, "empty Fields", Toast.LENGTH_SHORT).show();
                }else {
                    mStudentRef = FirebaseDatabase.getInstance().getReference().child("Student").child(StudentClass);
                    mStudentRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(StudentRoll)) {

                                Name = snapshot.child(StudentRoll).child("Name").getValue().toString();
                                FatherName = snapshot.child(StudentRoll).child("FatherName").getValue().toString();
                                MotherName = snapshot.child(StudentRoll).child("MotherName").getValue().toString();
                                Contact = snapshot.child(StudentRoll).child("Contact").getValue().toString();


                                mclassRef = FirebaseDatabase.getInstance().getReference().child("Student_Marks").child(StudentClass);


                                mclassRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(StudentExam)){
                                            mProgressDialog.dismiss();
                                            if (StudentClass.equalsIgnoreCase("nursery")) {
                                                Conversation = snapshot.child(StudentExam).child(StudentRoll).child("EnglishConversation").getValue().toString();
                                                Drawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                EnglishLiterature = snapshot.child(StudentExam).child(StudentRoll).child("English_Literature").getValue().toString();
                                                EnglishHandwrting = snapshot.child(StudentExam).child(StudentRoll).child("EnglishConversation").getValue().toString();
                                                Mathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                Rhymes = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                PT = snapshot.child(StudentExam).child(StudentRoll).child("PT").getValue().toString();


                                                GetData();
                                            } else if (StudentClass.equalsIgnoreCase("lkg")) {
                                                Drawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                English = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                EVS = snapshot.child(StudentExam).child(StudentRoll).child("EVS").getValue().toString();
                                                Mathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                Rhymes = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                GetData();
                                            } else if (StudentClass.equalsIgnoreCase("ukg")) {
                                                Bengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                Drawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                English = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                Hindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                Mathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                Rhymes = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                GetData();
                                            } else if (StudentClass.equalsIgnoreCase("1")) {
                                                Bengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                Drawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                English = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                Hindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                Mathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                Moral_ed = snapshot.child(StudentExam).child(StudentRoll).child("Moral_ed").getValue().toString();
                                                GetData();
                                            } else if (StudentClass.equalsIgnoreCase("2") || StudentClass.equalsIgnoreCase("3") || StudentClass.equalsIgnoreCase("4") || StudentClass.equalsIgnoreCase("5")) {
                                                Bengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                Drawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                English = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                Hindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                Mathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                EVS = snapshot.child(StudentExam).child(StudentRoll).child("EVS").getValue().toString();
                                                Rhymes = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                GK = snapshot.child(StudentExam).child(StudentRoll).child("GK").getValue().toString();
                                                GetData();

                                            }

                                        }else {
                                            mProgressDialog.dismiss();
                                            Toast.makeText(Search_and_Update.this, "No data", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "The student is not present", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }
        });

    }

    private void GetData() {
        Intent updateIntent = new Intent(getApplicationContext(), add_details.class);
        updateIntent.putExtra("StudentName", Name);
        updateIntent.putExtra("StudentRoll", StudentRoll);
        updateIntent.putExtra("StudentClass", StudentClass);
        updateIntent.putExtra("FatherName", FatherName);
        updateIntent.putExtra("MotherName", MotherName);
        updateIntent.putExtra("Contact", Contact);

        updateIntent.putExtra("Bengali", Bengali);
        updateIntent.putExtra("Drawing", Drawing);
        updateIntent.putExtra("English", English);
        updateIntent.putExtra("Hindi", Hindi);
        updateIntent.putExtra("Mathematics", Mathematics);
        updateIntent.putExtra("EVS", EVS);
        updateIntent.putExtra("GK", GK);
        updateIntent.putExtra("Rhymes", Rhymes);
        updateIntent.putExtra("EnglishLiterature", EnglishLiterature);
        updateIntent.putExtra("EnglishHandwriting", EnglishHandwrting);
        updateIntent.putExtra("Moral_ed", Moral_ed);
        updateIntent.putExtra("Conversation", Conversation);
        updateIntent.putExtra("PT", PT);
        updateIntent.putExtra("saveOrNot", 1);
        updateIntent.putExtra("ExamType", StudentExam);
        startActivity(updateIntent);
        edt_name.setText("");

        edt_roll.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.edt_update_class_update:

                StudentClass = SchoolClass[position];

                break;

            case R.id.edt_Search_examType:

                StudentExam = ExamType[position];


                break;



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
