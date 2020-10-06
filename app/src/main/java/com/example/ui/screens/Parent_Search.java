package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ui.ConnectingReceview;
import com.example.ui.MainActivity;
import com.example.ui.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Parent_Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /**
     * student is searched and marks are obtain from the  firebase and taken to the viewing page
     */

    Toolbar mToolbarSearchParent;
    EditText edt_StudentName, edt_Student_roll, edt_studentClass;
    String StudentName, StudentRoll, StudentClass, StudentFather, StudentMother, StudentContact, StudentExam;
    String SubEnglish, SubEnglishLitearture, SubEnglisgHandwriting, SubConversation, SubMoral_ed, SubBengali, SubHindi, SubMathematics, SubEVS, SubRhyme, SubDrawing, SubGK, SubPT;
    Button btn_parent_search_student;
    DatabaseReference mStudentMarksViewRef, mClassMarksRef;

    Spinner parent_spinner_class, parent_spinner_examType;

    String[] SchoolClass = { "Nursery", "LKG", "UKG", "1", "2", "3", "4", "5" };
    String[] ExamType = { "UT 1", "Half Yearly", "UT 2", "Final"};

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * Student are search by the parent
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__search);


        mToolbarSearchParent = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbarSearchParent);
        getSupportActionBar().setTitle("Search Student");
        mToolbarSearchParent.setTitleTextColor(getResources().getColor(R.color.text_color));
        getParentSearchStudent();


mProgress = new ProgressDialog(this);
mProgress.setTitle("Loading...");
mProgress.setMessage("Please wait to get the data");
mProgress.setCanceledOnTouchOutside(false);

        final ArrayAdapter<String> Class = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SchoolClass);
        Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parent_spinner_class.setAdapter(Class);
        parent_spinner_class.setOnItemSelectedListener(this);

        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parent_spinner_examType.setAdapter(ExamTypes);
        parent_spinner_examType.setOnItemSelectedListener(this);


        btn_parent_search_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mProgress.show();
                if (ConnectingReceview.checkConnection(getApplicationContext())) {
                    // Its Available...
                    StudentName = edt_StudentName.getText().toString();
                    StudentRoll = edt_Student_roll.getText().toString();


                    if (TextUtils.isEmpty(StudentName) && TextUtils.isEmpty(StudentRoll)) {
                        mProgress.hide();
                        Toast.makeText(Parent_Search.this, "empty Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        mStudentMarksViewRef = FirebaseDatabase.getInstance().getReference().child("Student").child(StudentClass);
                        mStudentMarksViewRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(StudentRoll)) {


                                    StudentFather = snapshot.child(StudentRoll).child("FatherName").getValue().toString();
                                    StudentMother = snapshot.child(StudentRoll).child("MotherName").getValue().toString();
                                    StudentContact = snapshot.child(StudentRoll).child("Contact").getValue().toString();
                                    StudentName = snapshot.child(StudentRoll).child("Name").getValue().toString();
                                    StudentRoll = snapshot.child(StudentRoll).child("Roll").getValue().toString();
                                    mClassMarksRef = FirebaseDatabase.getInstance().getReference().child("Student_Marks").child(StudentClass);
                                    mClassMarksRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            if(snapshot.hasChild(StudentExam)){
                                                if(snapshot.child(StudentExam).hasChild(StudentRoll)){
                                                    mProgress.dismiss();
                                                    if ("ukg".equals(StudentClass)) {
                                                        SubBengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                        SubDrawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                        SubEnglish = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                        SubHindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                        SubMathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                        SubRhyme = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                        Intent ukgInteny = new Intent(getApplicationContext(), view_parent_student_details.class);
                                                        ukgInteny.putExtra("StudentName", StudentName);
                                                        ukgInteny.putExtra("StudentClass", StudentClass);
                                                        ukgInteny.putExtra("StudentRoll", StudentRoll);
                                                        ukgInteny.putExtra("FatherName", StudentFather);
                                                        ukgInteny.putExtra("MotherName", StudentMother);
                                                        ukgInteny.putExtra("Contact", StudentContact);
                                                        ukgInteny.putExtra("Bengali", SubBengali);
                                                        ukgInteny.putExtra("Drawing", SubDrawing);
                                                        ukgInteny.putExtra("English", SubEnglish);
                                                        ukgInteny.putExtra("Hindi", SubHindi);
                                                        ukgInteny.putExtra("Mathematics", SubMathematics);
                                                        ukgInteny.putExtra("Rhymes", SubRhyme);
                                                        ukgInteny.putExtra("ExamType", StudentExam);
                                                        startActivity(ukgInteny);
                                                        edt_Student_roll.setText("");

                                                        edt_StudentName.setText("");
                                                    } else if ("lkg".equals(StudentClass)) {
                                                        SubDrawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                        SubEnglish = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                        SubEVS = snapshot.child(StudentExam).child(StudentRoll).child("EVS").getValue().toString();
                                                        SubMathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                        SubRhyme = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();

                                                        Intent lkgIntent = new Intent(getApplicationContext(), view_parent_student_details.class);
                                                        lkgIntent.putExtra("StudentName", StudentName);
                                                        lkgIntent.putExtra("StudentClass", StudentClass);
                                                        lkgIntent.putExtra("StudentRoll", StudentRoll);
                                                        lkgIntent.putExtra("FatherName", StudentFather);
                                                        lkgIntent.putExtra("MotherName", StudentMother);
                                                        lkgIntent.putExtra("Contact", StudentContact);
                                                        lkgIntent.putExtra("Drawing", SubDrawing);
                                                        lkgIntent.putExtra("English", SubEnglish);
                                                        lkgIntent.putExtra("EVS", SubEVS);
                                                        lkgIntent.putExtra("Mathematics", SubMathematics);
                                                        lkgIntent.putExtra("Rhymes", SubRhyme);
                                                        lkgIntent.putExtra("ExamType",StudentExam);

                                                        startActivity(lkgIntent);

                                                        edt_Student_roll.setText("");

                                                        edt_StudentName.setText("");
                                                    } else if ("nursery".equals(StudentClass)) {
                                                        SubConversation = snapshot.child(StudentExam).child(StudentRoll).child("EnglishConversation").getValue().toString();
                                                        SubDrawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                        SubEnglishLitearture = snapshot.child(StudentExam).child(StudentRoll).child("English_Literature").getValue().toString();
                                                        SubEnglisgHandwriting = snapshot.child(StudentExam).child(StudentRoll).child("EnglishConversation").getValue().toString();
                                                        SubMathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                        SubRhyme = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                        SubPT = snapshot.child(StudentExam).child(StudentRoll).child("PT").getValue().toString();

                                                        Intent nurseryIntent = new Intent(getApplicationContext(), view_parent_student_details.class);
                                                        nurseryIntent.putExtra("StudentName", StudentName);
                                                        nurseryIntent.putExtra("StudentClass", StudentClass);
                                                        nurseryIntent.putExtra("StudentRoll", StudentRoll);
                                                        nurseryIntent.putExtra("FatherName", StudentFather);
                                                        nurseryIntent.putExtra("MotherName", StudentMother);
                                                        nurseryIntent.putExtra("Contact", StudentContact);
                                                        nurseryIntent.putExtra("Conversation", SubConversation);
                                                        nurseryIntent.putExtra("Drawing", SubDrawing);
                                                        nurseryIntent.putExtra("EnglishLiterature", SubEnglishLitearture);
                                                        nurseryIntent.putExtra("EnglishHandwriting", SubEnglisgHandwriting);
                                                        nurseryIntent.putExtra("Mathematics", SubMathematics);
                                                        nurseryIntent.putExtra("Rhymes", SubRhyme);
                                                        nurseryIntent.putExtra("PT", SubPT);
                                                        nurseryIntent.putExtra("ExamType", StudentExam);
                                                        startActivity(nurseryIntent);

                                                        edt_Student_roll.setText("");

                                                        edt_StudentName.setText("");
                                                    } else if ("1".equals(StudentClass)) {
                                                        SubBengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                        SubDrawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                        SubEnglish = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                        SubHindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                        SubMathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                        SubMoral_ed = snapshot.child(StudentExam).child(StudentRoll).child("Moral_Edu").getValue().toString();

                                                        Intent FirstIntent = new Intent(getApplicationContext(), view_parent_student_details.class);
                                                        FirstIntent.putExtra("StudentName", StudentName);
                                                        FirstIntent.putExtra("StudentClass", StudentClass);
                                                        FirstIntent.putExtra("StudentRoll", StudentRoll);

                                                        FirstIntent.putExtra("FatherName", StudentFather);
                                                        FirstIntent.putExtra("MotherName", StudentMother);
                                                        FirstIntent.putExtra("Contact", StudentContact);

                                                        FirstIntent.putExtra("Bengali", SubBengali);
                                                        FirstIntent.putExtra("Drawing", SubDrawing);
                                                        FirstIntent.putExtra("English", SubEnglish);
                                                        FirstIntent.putExtra("Hindi", SubHindi);
                                                        FirstIntent.putExtra("Mathematics", SubMathematics);
                                                        FirstIntent.putExtra("Moral_Ed", SubMoral_ed);
                                                        FirstIntent.putExtra("ExamType", StudentExam);


                                                        startActivity(FirstIntent);

                                                        edt_Student_roll.setText("");

                                                        edt_StudentName.setText("");
                                                    } else if ("2".equals(StudentClass) || "3".equals(StudentClass) || "4".equals(StudentClass) || "5".equals(StudentClass)) {
                                                        SubBengali = snapshot.child(StudentExam).child(StudentRoll).child("Bengali").getValue().toString();
                                                        SubDrawing = snapshot.child(StudentExam).child(StudentRoll).child("Drawing").getValue().toString();
                                                        SubEnglish = snapshot.child(StudentExam).child(StudentRoll).child("English").getValue().toString();
                                                        SubHindi = snapshot.child(StudentExam).child(StudentRoll).child("Hindi").getValue().toString();
                                                        SubMathematics = snapshot.child(StudentExam).child(StudentRoll).child("Mathematics").getValue().toString();
                                                        SubEVS = snapshot.child(StudentExam).child(StudentRoll).child("EVS").getValue().toString();
                                                        SubGK = snapshot.child(StudentExam).child(StudentRoll).child("GK").getValue().toString();
                                                        SubRhyme = snapshot.child(StudentExam).child(StudentRoll).child("Rhymes").getValue().toString();
                                                        getPrimaryMarks();

                                                        edt_Student_roll.setText("");

                                                        edt_StudentName.setText("");
                                                    }
                                                }else{
                                                    mProgress.dismiss();
                                                    Toast.makeText(Parent_Search.this, "No data for this student", Toast.LENGTH_SHORT).show();
                                                }


                                            }else {
                                                mProgress.dismiss();
                                                Toast.makeText(Parent_Search.this, "No data for this exam", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    mProgress.dismiss();
                                    Toast.makeText(getApplicationContext(), "it's not", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } else {
                    // Not Available...
                    mProgress.dismiss();
                    Toast.makeText(Parent_Search.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }




    private void getParentSearchStudent() {
        edt_Student_roll = findViewById(R.id.edt_parent_roll_call);
        parent_spinner_class = findViewById(R.id.edt_Parent_class);
        edt_StudentName = findViewById(R.id.edt_parent_name);
        btn_parent_search_student = findViewById(R.id.btn_search_parent_student);
        parent_spinner_examType = findViewById(R.id.edt_Parent_exam);


    }

    private void getPrimaryMarks() {
        Intent primaryIntent = new Intent(getApplicationContext(), view_parent_student_details.class);
        primaryIntent.putExtra("StudentName", StudentName);
        primaryIntent.putExtra("StudentClass", StudentClass);
        primaryIntent.putExtra("StudentRoll", StudentRoll);

        primaryIntent.putExtra("FatherName", StudentFather);
        primaryIntent.putExtra("MotherName", StudentMother);
        primaryIntent.putExtra("Contact", StudentContact);


        primaryIntent.putExtra("Bengali", SubBengali);
        primaryIntent.putExtra("Drawing", SubDrawing);
        primaryIntent.putExtra("English", SubEnglish);
        primaryIntent.putExtra("Hindi", SubHindi);
        primaryIntent.putExtra("Mathematics", SubMathematics);
        primaryIntent.putExtra("EVS", SubEVS);
        primaryIntent.putExtra("GK", SubGK);
        primaryIntent.putExtra("Rhymes", SubRhyme);
        primaryIntent.putExtra("ExamType", StudentExam);
        startActivity(primaryIntent);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.edt_Parent_class:

                StudentClass = SchoolClass[position];

                break;

            case R.id.edt_Parent_exam:

                StudentExam = ExamType[position];


                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
