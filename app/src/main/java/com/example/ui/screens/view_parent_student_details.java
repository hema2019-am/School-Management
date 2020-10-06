package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class view_parent_student_details extends AppCompatActivity {

    private Toolbar mToolbarAddStudents;


    TextView StudentName, StudentRoll, StudentClass, StudentFathername, StudentMothername, StudentContact, StudentExam;

    String IntentStudentName, IntentRoll, IntentClass, IntentFatherName, IntentMothername, IntentContact, IntentExamType;
    String EnglishIntent, EnglishLiteratureIntent, EnglishHandwritingIntent, ConversationIntent, MathsIntent, HindiIntent, BengaliIntent, EVSIntent, GKIntent, DrawingIntent, PTIntent, Moral_edIntent, RhymesIntent;
    Button btn_save;



    private ProgressDialog mProgressDialog;
    CircleImageView mStudeImage;
    StorageReference mStudentRef;

    String downlaod_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parent_student_details);


        mToolbarAddStudents = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbarAddStudents);

        getSupportActionBar().setTitle("Student Details");
        mToolbarAddStudents.setTitleTextColor(getResources().getColor(R.color.text_color));
        mStudentRef = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(view_parent_student_details.this);
        mProgressDialog.setTitle("Uploading Image");
        mProgressDialog.setMessage("Please wait while we upload and process the image");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        getViews();

        try{
            StorageReference mPhotoRef = mStudentRef.child(IntentClass).child(IntentClass+"_"+IntentRoll+".jpg");
            mPhotoRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        mProgressDialog.dismiss();
                        downlaod_url = task.getResult().toString();
                        Picasso.with(getApplicationContext()).load(downlaod_url).placeholder(R.drawable.baby).into(mStudeImage);

                    }else{
                        mProgressDialog.dismiss();
                        Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudeImage);
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    mProgressDialog.dismiss();
                    Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudeImage);
                }
            });
        }catch (Exception e){
            mProgressDialog.dismiss();

            Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudeImage);
            Log.d("Error",e.getMessage());
        }










        btn_save = (findViewById(R.id.btn_save_student));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddIntent = new Intent(getApplicationContext(), View_Parent_marks.class);
                AddIntent.putExtra("StudentName", IntentStudentName);
                AddIntent.putExtra("StudentRoll", IntentRoll);
                AddIntent.putExtra("StudentClass", IntentClass);
                AddIntent.putExtra("English", EnglishIntent);
                AddIntent.putExtra("EnglishLiterature", EnglishLiteratureIntent);
                AddIntent.putExtra("EnglishHandwriting", EnglishHandwritingIntent);
                AddIntent.putExtra("conversation", ConversationIntent);
                AddIntent.putExtra("Mathematics", MathsIntent);
                AddIntent.putExtra("Hindi", HindiIntent);
                AddIntent.putExtra("Bengali", BengaliIntent);
                AddIntent.putExtra("EVS", EVSIntent);
                AddIntent.putExtra("GK", GKIntent);
                AddIntent.putExtra("Drawing", DrawingIntent);
                AddIntent.putExtra("PT", PTIntent);
                AddIntent.putExtra("Moral_ed", Moral_edIntent);
                AddIntent.putExtra("Rhymes", RhymesIntent);
                AddIntent.putExtra("ExamType", IntentExamType);
                startActivity(AddIntent);


            }
        });


    }

    private void getViews() {

        mStudeImage = findViewById(R.id.student_image);

        StudentFathername = findViewById(R.id.txt_father_name);
        StudentClass = findViewById(R.id.txt_class);
        StudentMothername = findViewById(R.id.txt_mother_name);
        StudentContact = findViewById(R.id.txt_contact_details);
        StudentRoll = findViewById(R.id.txt_roll);
        StudentName = findViewById(R.id.txt_name);
        StudentExam = findViewById(R.id.txt_examType);


        IntentClass = getIntent().getStringExtra("StudentClass");
        IntentContact = getIntent().getStringExtra("Contact");
        IntentFatherName = getIntent().getStringExtra("FatherName");
        IntentMothername = getIntent().getStringExtra("MotherName");
        IntentRoll = getIntent().getStringExtra("StudentRoll");
        IntentStudentName = getIntent().getStringExtra("StudentName");
        IntentExamType = getIntent().getStringExtra("ExamType");


        EnglishIntent = getIntent().getStringExtra("English");
        EnglishLiteratureIntent = getIntent().getStringExtra("EnglishLiterature");
        EnglishHandwritingIntent = getIntent().getStringExtra("EnglishHandwriting");
        ConversationIntent = getIntent().getStringExtra("Conversation");
        MathsIntent = getIntent().getStringExtra("Mathematics");
        HindiIntent = getIntent().getStringExtra("Hindi");
        BengaliIntent = getIntent().getStringExtra("Bengali");
        EVSIntent = getIntent().getStringExtra("EVS");
        GKIntent = getIntent().getStringExtra("GK");
        DrawingIntent = getIntent().getStringExtra("Drawing");
        PTIntent = getIntent().getStringExtra("PT");
        Moral_edIntent = getIntent().getStringExtra("Moral_Ed");
        RhymesIntent = getIntent().getStringExtra("Rhymes");

        StudentName.setText("Name: "+IntentStudentName);
        StudentRoll.setText("Roll: " +IntentRoll);
        StudentContact.setText("Contact: " +IntentContact);
        StudentMothername.setText("Mother Name: "+IntentMothername);
        StudentFathername.setText("Father Name: "+IntentFatherName);
        StudentClass.setText("Class: "+IntentClass);
        StudentExam.setText("Exam: " + IntentExamType);






    }
}
