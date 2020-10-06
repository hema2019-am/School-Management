package com.example.ui.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ui.R;

public class View_Parent_marks extends AppCompatActivity {
Toolbar mToolbarViewParentMarks;


    String StudentName, StudentRoll, StudentClass, StudentExam;
    TextView txt_StudentName, txt_StudentRoll, txt_Studentclass, txt_StudentExam;
    TextView txt_English, txt_English_literature, txt_EnglishHandwriting, txt_Conservation, txt_Maths, txt_MoralEd, txt_Bebgali, txt_hindi, txt_EVS, txt_drawing, txt_GK, txt_PT, txt_rhymes;
    String english, english_literature, english_handwriting, conversation, maths, moral_ed, bengali, hindi, evs, drawing, gk, pt, rhymes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);




        StudentName = getIntent().getStringExtra("StudentName");
        StudentRoll = getIntent().getStringExtra("StudentRoll");
        StudentClass = getIntent().getStringExtra("StudentClass");
        StudentExam = getIntent().getStringExtra("ExamType");

        //here the marks are stored in variable
        english = getIntent().getStringExtra("English");
        english_literature = getIntent().getStringExtra("EnglishLiterature");
        english_handwriting = getIntent().getStringExtra("EnglishHandwriting");
        conversation = getIntent().getStringExtra("Conversation");
        maths = getIntent().getStringExtra("Mathematics");
        moral_ed = getIntent().getStringExtra("Moral_Ed");
        bengali = getIntent().getStringExtra("Bengali");
        hindi = getIntent().getStringExtra("Hindi");
        evs = getIntent().getStringExtra("EVS");
        drawing = getIntent().getStringExtra("Drawing");
        gk = getIntent().getStringExtra("GK");
        pt = getIntent().getStringExtra("PT");
        rhymes = getIntent().getStringExtra("Rhymes");


        if (StudentClass.toLowerCase().equals("ukg")) {
            setContentView(R.layout.class_ukg_view);
            mToolbarViewParentMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarViewParentMarks);

            getSupportActionBar().setTitle("View Marks");
            mToolbarViewParentMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            getViews();
            txt_StudentName.setText("Name: " + StudentName);
            txt_StudentRoll.setText("Roll No: " + StudentRoll);
            txt_Studentclass.setText("Class: " + StudentClass);
            txt_Bebgali.setText("Bengali: " + bengali);
            txt_drawing.setText("Drawing: " + drawing);
            txt_English.setText("English: " + english);
            txt_hindi.setText("Hindi: " + hindi);
            txt_Maths.setText("Mathematics: " + maths);
            txt_rhymes.setText("Rhymes: " + rhymes);
            txt_StudentExam.setText("Exam: " + StudentExam);
        } else if (StudentClass.toLowerCase().equals("lkg")) {
            setContentView(R.layout.class_lkg_view);
            mToolbarViewParentMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarViewParentMarks);

            getSupportActionBar().setTitle("View Marks");
            mToolbarViewParentMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            getViews();
            txt_StudentName.setText("Name: " + StudentName);
            txt_StudentRoll.setText("Roll No: " + StudentRoll);
            txt_Studentclass.setText("Class: " + StudentClass);
            txt_drawing.setText("Drawing:" + drawing);
            txt_English.setText("English: " + english);
            txt_EVS.setText("EVS: " + evs);
            txt_Maths.setText("Mathematics: " + maths);
            txt_rhymes.setText("Rhymes: " + rhymes);
            txt_StudentExam.setText("Exam: " + StudentExam);

        } else if (StudentClass.toLowerCase().equals("nursery")) {
            setContentView(R.layout.class_nursery_view);
            mToolbarViewParentMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarViewParentMarks);

            getSupportActionBar().setTitle("View Marks");
            mToolbarViewParentMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            getViews();
            txt_StudentName.setText("Name: " + StudentName);
            txt_StudentRoll.setText("Roll No: " + StudentRoll);
            txt_Studentclass.setText("Class: " + StudentClass);
            txt_Conservation.setText("Conversation Class: " + conversation);
            txt_drawing.setText("Drawing: " + drawing);
            txt_English_literature.setText("English Literature: " + english_literature);
            txt_EnglishHandwriting.setText("English Handwriting: " + english_handwriting);
            txt_Maths.setText("Mathematics: " + maths);
            txt_rhymes.setText("Rhymes: " + rhymes);
            txt_PT.setText("PT: " + pt);
            txt_StudentExam.setText("Exam: " + StudentExam);


        } else if (StudentClass.toLowerCase().equals("1")) {
            setContentView(R.layout.class_first_view);
            mToolbarViewParentMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarViewParentMarks);

            getSupportActionBar().setTitle("View Marks");
            mToolbarViewParentMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            getViews();
            txt_StudentName.setText("Name: " + StudentName);
            txt_StudentRoll.setText("Roll No: " + StudentRoll);
            txt_Studentclass.setText("Class: " + StudentClass);
            txt_Bebgali.setText("Bengali: " + bengali);
            txt_drawing.setText("Drawing: " + drawing);
            txt_English.setText("English: " + english);
            txt_hindi.setText("Hindi: " + hindi);
            txt_Maths.setText("Mathematics: " + maths);
            txt_MoralEd.setText("Moral Ed: " + moral_ed);
            txt_StudentExam.setText("Exam: " + StudentExam);

        } else if (StudentClass.toLowerCase().equals("2") || StudentClass.toLowerCase().equals("3") || StudentClass.toLowerCase().equals("4") || StudentClass.toLowerCase().equals("5")) {
            setContentView(R.layout.class_primary_view);
            mToolbarViewParentMarks = findViewById(R.id.mainAppBar);
            setSupportActionBar(mToolbarViewParentMarks);

            getSupportActionBar().setTitle("View Marks");
            mToolbarViewParentMarks.setTitleTextColor(getResources().getColor(R.color.text_color));
            getViews();
            txt_StudentName.setText("Name: " + StudentName);
            txt_StudentRoll.setText("Roll No: " + StudentRoll);
            txt_Studentclass.setText("Class: " + StudentClass);
            txt_StudentExam.setText("Exam: " + StudentExam);

            txt_Bebgali.setText("Bengali: " + bengali);
            txt_drawing.setText("Drawing: " + drawing);
            txt_English.setText("English: " + english);
            txt_hindi.setText("Hindi: " + hindi);
            txt_Maths.setText("Mathematics: " + maths);
            txt_EVS.setText("EVS: " + evs);
            txt_GK.setText("GK: " + gk);
            txt_rhymes.setText("Rhymes: " + rhymes) ;

        }
    }

    private void getViews() {
        txt_English = findViewById(R.id.text_parent_english);
        txt_Bebgali = findViewById(R.id.text_parent_bengali);
        txt_Conservation = findViewById(R.id.text_parent_conversation);
        txt_drawing = findViewById(R.id.text_parent_drawing);
        txt_English_literature = findViewById(R.id.text_parent_english_literature);
        txt_EnglishHandwriting = findViewById(R.id.text_parent_english_handwriting);
        txt_EVS = findViewById(R.id.text_parent_EVS);
        txt_GK = findViewById(R.id.text_parent_GK);
        txt_hindi = findViewById(R.id.text_parent_hindi);
        txt_Maths = findViewById(R.id.text_parent_maths);
        txt_MoralEd = findViewById(R.id.text_parent_moralEd);
        txt_PT = findViewById(R.id.text_parent_PT);
        txt_rhymes = findViewById(R.id.text_parent_Rhymes);


        txt_StudentName = findViewById(R.id.nameText);
        txt_StudentRoll = findViewById(R.id.rollText);
        txt_Studentclass = findViewById(R.id.classText);
        txt_StudentExam = findViewById(R.id.examType);

    }
}
