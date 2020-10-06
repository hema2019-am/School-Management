package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Add_update_marks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * here the code runs both the add student and update student screen.
     * The screen layout is change according to the Student class grade.
     */

    private Toolbar mToolbarAddUpdateMarks;

    String Studentname, Studentroll, Studentclass, StudentExam,
            marksEnglish = null, marksLiterature = null, marksHandwriting = null,
            marksCoversation = null, marksMoralEd = null, marksBengali = null, marksHindi = null,
            marksEVS = null, marksRhymes = null, marksDrawing = null, marksPT = null, marksMathematics = null, marks_gk = null;
    TextView nameTextAdmin, rollTextAdmin, classTextAdmin;
    EditText edt_english, edt_literature, edt_handwriting,
            edt_converstion, edt_moral_ed, edt_bengali, edt_hindi,
            edt_evs, edt_rhymes, edt_drawing, edt_pt, edt_mathematics, edt_gk;



    String IntentExam, EnglishIntent, EnglishLiteratureIntent, EnglishHandwritingIntent, ConversationIntent, MathsIntent, HindiIntent, BengaliIntent, EVSIntent, GKIntent, DrawingIntent, PTIntent, Moral_edIntent, RhymesIntent;

    FirebaseDatabase mClassDB;
    DatabaseReference mClassRef;

    Button btn_add_update_marks;
    boolean dataSaved = false;
    int newStudentorOld;


    Spinner spinner_examType;


    String[] ExamType = { "UT 1", "Half Yearly", "UT 2", "Final"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Studentname = getIntent().getStringExtra("StudentName");
        Studentroll = getIntent().getStringExtra("StudentRoll");
        Studentclass = getIntent().getStringExtra("StudentClass");
        IntentExam = getIntent().getStringExtra("ExamType");



        EnglishIntent = getIntent().getStringExtra("English");
        EnglishLiteratureIntent = getIntent().getStringExtra("EnglishLiterature");
        EnglishHandwritingIntent = getIntent().getStringExtra("EnglishHandwriting");
        ConversationIntent = getIntent().getStringExtra("Conversation");
        MathsIntent = getIntent().getStringExtra("Maths");
        HindiIntent = getIntent().getStringExtra("Hindi");
        BengaliIntent = getIntent().getStringExtra("Bengali");
        EVSIntent = getIntent().getStringExtra("EVS");
        GKIntent = getIntent().getStringExtra("GK");
        DrawingIntent = getIntent().getStringExtra("Drawing");
        PTIntent = getIntent().getStringExtra("PT");
        Moral_edIntent = getIntent().getStringExtra("Moral_ed");
        RhymesIntent = getIntent().getStringExtra("Rhymes");
        newStudentorOld = getIntent().getIntExtra("saveOrNot", 0);


        String s = Studentclass.toLowerCase();
        if ("nursery".equals(s)) {
            setContentView(R.layout.class_nursery);
            getNursery();
            mToolbarAddUpdateMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarAddUpdateMarks);

            getSupportActionBar().setTitle("Add / Update Marks");
            mToolbarAddUpdateMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            nameTextAdmin.setText("Name: " + Studentname);
            rollTextAdmin.setText("Roll No: " + Studentroll);
            classTextAdmin.setText("Class: " + Studentclass);


            edt_converstion.setText(ConversationIntent);
            edt_drawing.setText(DrawingIntent);
            edt_literature.setText(EnglishLiteratureIntent);
            edt_handwriting.setText(EnglishHandwritingIntent);
            edt_mathematics.setText(MathsIntent);
            edt_pt.setText(PTIntent);
            edt_rhymes.setText(RhymesIntent);
        } else if ("lkg".equals(s)) {
            setContentView(R.layout.class_lkg);
            getlkg();
            mToolbarAddUpdateMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarAddUpdateMarks);

            getSupportActionBar().setTitle("Add / Update Marks");
            mToolbarAddUpdateMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            nameTextAdmin.setText("Name: " + Studentname);
            rollTextAdmin.setText("Roll No: " + Studentroll);
            classTextAdmin.setText("Class: " + Studentclass);

            edt_drawing.setText(DrawingIntent);
            edt_english.setText(EnglishIntent);
            edt_evs.setText(EVSIntent);
            edt_mathematics.setText(MathsIntent);
            edt_rhymes.setText(RhymesIntent);
        } else if ("ukg".equals(s)) {
            setContentView(R.layout.class_ukg);
            getukg();
            mToolbarAddUpdateMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarAddUpdateMarks);

            getSupportActionBar().setTitle("Add / Update Marks");
            mToolbarAddUpdateMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            nameTextAdmin.setText("Name: " + Studentname);
            rollTextAdmin.setText("Roll No: " + Studentroll);
            classTextAdmin.setText("Class: " + Studentclass);


            edt_bengali.setText(BengaliIntent);
            edt_drawing.setText(DrawingIntent);
            edt_english.setText(EnglishIntent);
            edt_hindi.setText(HindiIntent);
            edt_mathematics.setText(MathsIntent);
            edt_rhymes.setText(RhymesIntent);
        } else if ("1".equals(s)) {
            setContentView(R.layout.class_first);
            getFirst();
            mToolbarAddUpdateMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarAddUpdateMarks);

            getSupportActionBar().setTitle("Add / Update Marks");
            mToolbarAddUpdateMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            nameTextAdmin.setText("Name: " + Studentname);
            rollTextAdmin.setText("Roll No: " + Studentroll);
            classTextAdmin.setText("Class: " + Studentclass);


            edt_bengali.setText(BengaliIntent);
            edt_drawing.setText(DrawingIntent);
            edt_english.setText(EnglishIntent);
            edt_hindi.setText(HindiIntent);
            edt_mathematics.setText(MathsIntent);
            edt_moral_ed.setText(Moral_edIntent);
        } else if (s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5")) {
            setContentView(R.layout.class_primary);
            getPrimrary();
            mToolbarAddUpdateMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarAddUpdateMarks);

            getSupportActionBar().setTitle("Add / Update Marks");
            mToolbarAddUpdateMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            nameTextAdmin.setText("Name: " + Studentname);
            rollTextAdmin.setText("Roll No: " + Studentroll);
            classTextAdmin.setText("Class: " + Studentclass);

            edt_bengali.setText(BengaliIntent);
            edt_drawing.setText(DrawingIntent);
            edt_english.setText(EnglishIntent);
            edt_hindi.setText(HindiIntent);
            edt_mathematics.setText(MathsIntent);
            edt_evs.setText(EVSIntent);
            edt_gk.setText(GKIntent);
            edt_rhymes.setText(RhymesIntent);
        }

    }

    private void getNursery() {
        nameTextAdmin = findViewById(R.id.nameTextAdmin);
        rollTextAdmin = findViewById(R.id.rollTextAdmin);
        classTextAdmin = findViewById(R.id.classTextAdmin);
        
        spinner_examType = findViewById(R.id.spinner_exam);
        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);


        edt_literature = findViewById(R.id.edt_marks_englishLiterature);
        edt_handwriting = findViewById(R.id.edt_marks_englishHandwriting);
        edt_converstion = findViewById(R.id.edt_marks_conversationClass);
        edt_mathematics = findViewById(R.id.edt_marks_mathematics);
        edt_rhymes = findViewById(R.id.edt_marks_Rhymes);
        edt_drawing = findViewById(R.id.edt_marks_drawing);
        edt_pt = findViewById(R.id.edt_marks_PT);
        btn_add_update_marks = findViewById(R.id.btn_add_update_marks);
        btn_add_update_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassDB = FirebaseDatabase.getInstance();
                mClassRef = mClassDB.getReference().child("Student_Marks").child(Studentclass).child(StudentExam).child(Studentroll);
                HashMap<String, String> StudentMarks = new HashMap<>();


                marksLiterature = edt_literature.getText().toString();
                marksHandwriting = edt_handwriting.getText().toString();
                marksCoversation = edt_converstion.getText().toString();
                marksMathematics = edt_mathematics.getText().toString();
                marksRhymes = edt_rhymes.getText().toString();
                marksDrawing = edt_drawing.getText().toString();
                marksPT = edt_pt.getText().toString();


                StudentMarks.put("English_Literature", marksLiterature);
                StudentMarks.put("English_Handwriting", marksHandwriting);
                StudentMarks.put("EnglishConversation", marksCoversation);
                StudentMarks.put("Mathematics", marksMathematics);
                StudentMarks.put("Rhymes", marksRhymes);
                StudentMarks.put("Drawing", marksDrawing);
                StudentMarks.put("PT", marksPT);

                mClassRef.setValue(StudentMarks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dataSaved = true;
                            Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                            finish();
                            edt_literature.setText("");
                            edt_handwriting.setText("");
                            edt_converstion.setText("");
                            edt_mathematics.setText("");
                            edt_rhymes.setText("");
                            edt_drawing.setText("");
                            edt_pt.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }


    private void getlkg() {
        nameTextAdmin = findViewById(R.id.nameTextAdmin);
        rollTextAdmin = findViewById(R.id.rollTextAdmin);
        classTextAdmin = findViewById(R.id.classTextAdmin);
        
        spinner_examType = findViewById(R.id.spinner_exam);


        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);

        edt_english = findViewById(R.id.edt_marks_english);
        edt_mathematics = findViewById(R.id.edt_marks_mathematics);
        edt_evs = findViewById(R.id.edt_marks_EVS);
        edt_rhymes = findViewById(R.id.edt_marks_Rhymes);
        edt_drawing = findViewById(R.id.edt_marks_drawing);
        btn_add_update_marks = findViewById(R.id.btn_add_update_marks);
        btn_add_update_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassDB = FirebaseDatabase.getInstance();
                mClassRef = mClassDB.getReference().child("Student_Marks").child(Studentclass).child(StudentExam).child(Studentroll);
                HashMap<String, String> StudentMarks = new HashMap<>();

                marksEnglish = edt_english.getText().toString();
                marksMathematics = edt_mathematics.getText().toString();
                marksRhymes = edt_rhymes.getText().toString();
                marksDrawing = edt_drawing.getText().toString();
                marksEVS = edt_evs.getText().toString();


                StudentMarks.put("English", marksEnglish);
                StudentMarks.put("Mathematics", marksMathematics);
                StudentMarks.put("Rhymes", marksRhymes);
                StudentMarks.put("Drawing", marksDrawing);
                StudentMarks.put("EVS", marksEVS);

                mClassRef.setValue(StudentMarks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dataSaved = true;
                            Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                            finish();
                            edt_english.setText("");
                            edt_mathematics.setText("");
                            edt_rhymes.setText("");
                            edt_drawing.setText("");
                            edt_evs.setText("");

                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void getukg() {
        nameTextAdmin = findViewById(R.id.nameTextAdmin);
        rollTextAdmin = findViewById(R.id.rollTextAdmin);
        classTextAdmin = findViewById(R.id.classTextAdmin);
       
        spinner_examType = findViewById(R.id.spinner_exam);

        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);
        edt_english = findViewById(R.id.edt_marks_english);
        edt_mathematics = findViewById(R.id.edt_marks_mathematics);
        edt_rhymes = findViewById(R.id.edt_marks_Rhymes);
        edt_drawing = findViewById(R.id.edt_marks_drawing);
        edt_bengali = findViewById(R.id.edt_marks_Bengali);
        edt_hindi = findViewById(R.id.edt_marks_Hindi);
        btn_add_update_marks = findViewById(R.id.btn_add_update_marks);

        btn_add_update_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassDB = FirebaseDatabase.getInstance();
                mClassRef = mClassDB.getReference().child("Student_Marks").child(Studentclass).child(StudentExam).child(Studentroll);
                HashMap<String, String> StudentMarks = new HashMap<>();

                marksEnglish = edt_english.getText().toString();
                marksMathematics = edt_mathematics.getText().toString();
                marksRhymes = edt_rhymes.getText().toString();
                marksDrawing = edt_drawing.getText().toString();
                marksBengali = edt_bengali.getText().toString();
                marksHindi = edt_hindi.getText().toString();


                StudentMarks.put("English", marksEnglish);
                StudentMarks.put("Mathematics", marksMathematics);
                StudentMarks.put("Rhymes", marksRhymes);
                StudentMarks.put("Drawing", marksDrawing);
                StudentMarks.put("Bengali", marksBengali);
                StudentMarks.put("Hindi", marksHindi);

                mClassRef.setValue(StudentMarks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dataSaved = true;
                            Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                            finish();
                            edt_english.setText("");
                            edt_mathematics.setText("");
                            edt_rhymes.setText("");
                            edt_drawing.setText("");
                            edt_bengali.setText("");
                            edt_hindi.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void getFirst() {
        nameTextAdmin = findViewById(R.id.nameTextAdmin);
        rollTextAdmin = findViewById(R.id.rollTextAdmin);
        classTextAdmin = findViewById(R.id.classTextAdmin);
        spinner_examType = findViewById(R.id.spinner_exam);

        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);
        
        
        edt_english = findViewById(R.id.edt_marks_english);
        edt_mathematics = findViewById(R.id.edt_marks_mathematics);


        edt_drawing = findViewById(R.id.edt_marks_drawing);
        edt_bengali = findViewById(R.id.edt_marks_Bengali);
        edt_hindi = findViewById(R.id.edt_marks_Hindi);
        edt_moral_ed = findViewById(R.id.edt_marks_MoralEdu);
        btn_add_update_marks = findViewById(R.id.btn_add_update_marks);

        btn_add_update_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClassDB = FirebaseDatabase.getInstance();
                mClassRef = mClassDB.getReference().child("Student_Marks").child(Studentclass).child(StudentExam).child(Studentroll);
                HashMap<String, String> StudentMarks = new HashMap<>();
                marksEnglish = edt_english.getText().toString();
                marksMathematics = edt_mathematics.getText().toString();
                marksMoralEd = edt_moral_ed.getText().toString();
                marksDrawing = edt_drawing.getText().toString();
                marksBengali = edt_bengali.getText().toString();
                marksHindi = edt_hindi.getText().toString();


                StudentMarks.put("English", marksEnglish);
                StudentMarks.put("Mathematics", marksMathematics);
                StudentMarks.put("Moral_Edu", marksMoralEd);
                StudentMarks.put("Drawing", marksDrawing);
                StudentMarks.put("Bengali", marksBengali);
                StudentMarks.put("Hindi", marksHindi);
                mClassRef.setValue(StudentMarks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dataSaved = true;
                            Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                            finish();
                            edt_english.setText("");
                            edt_mathematics.setText("");
                            edt_moral_ed.setText("");
                            edt_drawing.setText("");
                            edt_bengali.setText("");
                            edt_hindi.setText("");

                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void getPrimrary() {
        nameTextAdmin = findViewById(R.id.nameTextAdmin);
        rollTextAdmin = findViewById(R.id.rollTextAdmin);
        classTextAdmin = findViewById(R.id.classTextAdmin);
        spinner_examType = findViewById(R.id.spinner_exam);

        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_examType.setAdapter(ExamTypes);
        spinner_examType.setOnItemSelectedListener(this);

        edt_english = findViewById(R.id.edt_marks_english);
        edt_mathematics = findViewById(R.id.edt_marks_mathematics);
        edt_gk = findViewById(R.id.edt_marks_GK);
        edt_drawing = findViewById(R.id.edt_marks_drawing);
        edt_bengali = findViewById(R.id.edt_marks_Bengali);
        edt_hindi = findViewById(R.id.edt_marks_Hindi);
        edt_evs = findViewById(R.id.edt_marks_EVS);
        edt_rhymes = findViewById(R.id.edt_marks_Rhymes);

        btn_add_update_marks = findViewById(R.id.btn_add_update_marks);
        btn_add_update_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassDB = FirebaseDatabase.getInstance();
                mClassRef = mClassDB.getReference().child("Student_Marks").child(Studentclass).child(StudentExam).child(Studentroll);
                HashMap<String, String> StudentMarks = new HashMap<>();
                marksEnglish = edt_english.getText().toString();
                marksMathematics = edt_mathematics.getText().toString();
                marks_gk = edt_gk.getText().toString();
                marksDrawing = edt_drawing.getText().toString();
                marksBengali = edt_bengali.getText().toString();
                marksHindi = edt_hindi.getText().toString();
                marksRhymes = edt_rhymes.getText().toString();
                marksEVS = edt_evs.getText().toString();


                StudentMarks.put("English", marksEnglish);
                StudentMarks.put("Mathematics", marksMathematics);
                StudentMarks.put("GK", marks_gk);
                StudentMarks.put("Drawing", marksDrawing);
                StudentMarks.put("Bengali", marksBengali);
                StudentMarks.put("Hindi", marksHindi);
                StudentMarks.put("Rhymes", marksRhymes);
                StudentMarks.put("EVS", marksEVS);

                mClassRef.setValue(StudentMarks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dataSaved = true;
                            Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                            finish();
                            edt_english.setText("");
                            edt_mathematics.setText("");
                            edt_gk.setText("");
                            edt_drawing.setText("");
                            edt_bengali.setText("");
                            edt_hindi.setText("");
                            edt_rhymes.setText("");
                            edt_evs.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {


        if (newStudentorOld == 0) {
            Toast.makeText(this, "Student marks must be saved or with default value", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }

    }



    private int getIndexFromExam(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;

            }
        }
        return index;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        if (parent.getId() == R.id.spinner_exam) {
            StudentExam = ExamType[position];
            try {

                if (!IntentExam.isEmpty()) {
                    spinner_examType.setSelection(getIndexFromExam(spinner_examType, IntentExam));
                    IntentExam = null;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
