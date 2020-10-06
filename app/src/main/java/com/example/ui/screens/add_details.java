package com.example.ui.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import id.zelory.compressor.Compressor;

import com.example.ui.MainActivity;
import com.example.ui.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * in this java file. The code runs both for add and update student accordingly.
     * In short both task is in this same file
     */

    private Toolbar mToolbarAddStudents;

    EditText edt_name, edt_roll, edt_fathername, edt_mothername, edt_contach;
    String StudentName , StudentRoll, StudentClass, StudentFathername, StudentMothername, StudentContact, StudentExam;

    String IntentStudentName, IntentRoll, IntentClass, IntentFatherName, IntentMothername, IntentContact, IntentEaxam;
    String EnglishIntent, EnglishLiteratureIntent, EnglishHandwritingIntent, ConversationIntent, MathsIntent, HindiIntent, BengaliIntent, EVSIntent, GKIntent, DrawingIntent, PTIntent, Moral_edIntent, RhymesIntent;
    Button btn_save ;

    FirebaseDatabase studentDb;
    DatabaseReference studentRef;
    StorageReference mStudentStorageRef;

    private ProgressDialog mProgressDialog;
    CircleImageView mStudentImage;
    String downlaod_url;
    Spinner edt_class, edt_examType;

    private static final int Gallery_pick = 1;

    String[] SchoolClass = { "Nursery", "LKG", "UKG", "1", "2", "3", "4", "5" };
    String[] ExamType = { "UT 1", "Half Yearly", "UT 2", "Final"};

    private Uri filePath;
    Compressor compressorFile;

    Bitmap thumb_bitmap;

    byte[] thumb_bite ;

    int imageExtra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        mToolbarAddStudents = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbarAddStudents);

        mStudentStorageRef = FirebaseStorage.getInstance().getReference();

        getSupportActionBar().setTitle("Add Students");
        mToolbarAddStudents.setTitleTextColor(getResources().getColor(R.color.text_color));

        getViews();
        getImage();

        mStudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), Gallery_pick);
            }
        });





        //this part is used when we want to update a particular student marks rather than its not used
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
        Moral_edIntent = getIntent().getStringExtra("Moral_ed");
        RhymesIntent = getIntent().getStringExtra("Rhymes");
        StudentExam = getIntent().getStringExtra("ExamType");












        btn_save = (findViewById(R.id.btn_save_student));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here it save the student details
                mProgressDialog = new ProgressDialog(add_details.this);
                mProgressDialog.setTitle("Uploading Image");
                mProgressDialog.setMessage("Please wait while we upload and process the data");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                StudentName = edt_name.getText().toString();
                StudentRoll = edt_roll.getText().toString();

                StudentFathername = edt_fathername.getText().toString();
                StudentMothername = edt_mothername.getText().toString();
                StudentContact = edt_contach.getText().toString();

                if(TextUtils.isEmpty(StudentName) || TextUtils.isEmpty(StudentRoll) ||
                        TextUtils.isEmpty(StudentClass)
                       ){
                    mProgressDialog.dismiss();
                    Toast.makeText(add_details.this, "Please fill the empty spaces", Toast.LENGTH_SHORT).show();

                }else{

                    // it checks condition if the class is between nursery - 5th
                    if(StudentClass.toLowerCase().equals("nursery")
                            || StudentClass.toLowerCase().equals("lkg") ||
                            StudentClass.toLowerCase().equals("ukg") ||
                            StudentClass.toLowerCase().equals("1") || StudentClass.toLowerCase().equals("2") ||
                            StudentClass.toLowerCase().equals("3") || StudentClass.toLowerCase().equals("4") ||
                            StudentClass.toLowerCase().equals("5")){
                        studentDb = FirebaseDatabase.getInstance();
                        studentRef = studentDb.getReference().child("Student").child(StudentClass).child(StudentRoll);
                        HashMap<String, String> StudentDetails = new HashMap<>();
                        StudentDetails.put("Name", StudentName);
                        StudentDetails.put("Roll", StudentRoll);
                        StudentDetails.put("Class", StudentClass);
                        StudentDetails.put("FatherName", StudentFathername);
                        StudentDetails.put("MotherName", StudentMothername);
                        StudentDetails.put("Contact", StudentContact);
try{

        StorageReference ref = mStudentStorageRef.child(StudentClass).child(StudentClass+"_"+edt_roll.getText().toString()+".jpg");
        UploadTask uploadTask = ref.putBytes(thumb_bite);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mProgressDialog.dismiss();

                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(add_details.this, "error in uploading", Toast.LENGTH_SHORT).show();
                }
            }
        });


}catch (Exception e){
e.printStackTrace();
}



                        studentRef.setValue(StudentDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(add_details.this, "Details Saved", Toast.LENGTH_SHORT).show();

                                    mProgressDialog.dismiss();



                                    Intent AddIntent = new Intent(getApplicationContext(), Add_update_marks.class);
                                    AddIntent.putExtra("StudentName", edt_name.getText().toString());
                                    AddIntent.putExtra("StudentRoll",edt_roll.getText().toString());
                                    AddIntent.putExtra("StudentClass", StudentClass);
                                    AddIntent.putExtra("English", EnglishIntent);
                                    AddIntent.putExtra("EnglishLiterature", EnglishLiteratureIntent);
                                    AddIntent.putExtra("EnglishHandwriting", EnglishHandwritingIntent);
                                    AddIntent.putExtra("conversation", ConversationIntent);
                                    AddIntent.putExtra("Maths", MathsIntent);
                                    AddIntent.putExtra("Hindi", HindiIntent);
                                    AddIntent.putExtra("Bengali", BengaliIntent);
                                    AddIntent.putExtra("EVS", EVSIntent);
                                    AddIntent.putExtra("GK", GKIntent);
                                    AddIntent.putExtra("Drawing", DrawingIntent);
                                    AddIntent.putExtra("PT", PTIntent);
                                    AddIntent.putExtra("Moral_ed", Moral_edIntent);
                                    AddIntent.putExtra("Rhymes", RhymesIntent);
                                    AddIntent.putExtra("Contact", StudentContact);
                                    AddIntent.putExtra("FatherName", StudentFathername);
                                    AddIntent.putExtra("MotherName", StudentMothername);
                                    AddIntent.putExtra("ExamType", StudentExam);



                                    try{
                                        if(!IntentStudentName.isEmpty()){
                                            AddIntent.putExtra("saveOrNot", 1);

                                        }


                                    }catch (Exception e){
                                        Log.d("intent", e.getMessage());
                                    }




                                    startActivity(AddIntent);



                                    edt_contach.setText("");
                                    edt_fathername.setText("");
                                    edt_mothername.setText("");
                                    edt_name.setText("");
                                    edt_roll.setText("");
                                    Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudentImage);
                                }



                            }
                        });


                    }else{
                        mProgressDialog.dismiss();
                        Toast.makeText(add_details.this, "not a class", Toast.LENGTH_SHORT).show();
                    }









                }



            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_pick && resultCode == RESULT_OK){
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setAspectRatio(1,1)
                    .start(this);




            //Toast.makeText(SettingsActivity.this, ImageUri, Toast.LENGTH_SHORT).show();
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){


                Uri resultUri = result.getUri();

                File thum_file = new File(resultUri.getPath());





                thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this).setMaxHeight(200).setMaxWidth(200).setQuality(75).compressToBitmap(thum_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                thumb_bite = baos.toByteArray();

                Picasso.with(getApplicationContext()).load(thum_file).placeholder(R.drawable.baby).into(mStudentImage);






            }
        }


    }

    private void getViews(){
        edt_name = findViewById(R.id.edt_name);
        edt_roll = findViewById(R.id.edt_roll);
        edt_class = findViewById(R.id.edt_class);
        edt_fathername = findViewById(R.id.edt_father_name);
        edt_mothername = findViewById(R.id.edt_mother_name);
        edt_contach = findViewById(R.id.edt_contact_details);
        mStudentImage = findViewById(R.id.img_student);

        IntentClass = getIntent().getStringExtra("StudentClass");
        IntentContact = getIntent().getStringExtra("Contact");
        IntentFatherName = getIntent().getStringExtra("FatherName");
        IntentMothername = getIntent().getStringExtra("MotherName");
        IntentRoll = getIntent().getStringExtra("StudentRoll");
        IntentStudentName = getIntent().getStringExtra("StudentName");
        imageExtra = getIntent().getIntExtra("saveOrNot", 0);


        StudentClass = IntentClass;

        ArrayAdapter<String> Class = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SchoolClass);
        Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edt_class.setAdapter(Class);
        edt_class.setOnItemSelectedListener(this);



        edt_name.setText(IntentStudentName);
        edt_roll.setText(IntentRoll);
        edt_contach.setText(IntentContact);
        edt_mothername.setText(IntentMothername);
        edt_fathername.setText(IntentFatherName);



    }




    private void getImage(){
        try{
            StorageReference mPhotoRef = mStudentStorageRef.child(StudentClass).child(StudentClass+"_"+edt_roll.getText().toString()+".jpg");
            mPhotoRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){


                        downlaod_url = task.getResult().toString();
                        Picasso.with(getApplicationContext()).load(downlaod_url).placeholder(R.drawable.baby).into(mStudentImage);

                    }else{

                        Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudentImage);
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {

                    Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudentImage);
                }
            });
        }catch (Exception e){


            Picasso.with(getApplicationContext()).load(R.drawable.baby).into(mStudentImage);
            Log.d("Error",e.getMessage());
        }
    }

    private int getIndexFromClass(Spinner spinner, String myString){

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
        StudentClass = SchoolClass[position];
        if (parent.getId() == R.id.edt_class) {

            try {

                if (IntentClass!= null) {
                    edt_class.setSelection(getIndexFromClass(edt_class, IntentClass));
                    IntentClass = null;
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
