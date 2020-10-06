package com.example.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class TableMainLayout extends RelativeLayout implements AdapterView.OnItemSelectedListener {

    public final String TAG = "TableMainLayout.java";

    Spinner classSpinner, examSpinner;

    DatabaseReference mStudentMarks;

    int EnglishLit0_40 =0, EnglishLit41_50=0, EnglishLit51_6= 0, EnglishLit61_70, EnglishLit71_80, EnglishLit81_90, EnglishLit91_100;
    int English0_40, English41_50, English51_60=0, English61_70, English71_80, English81_90, English91_100;
    int EnglishHandwriting0_40, EnglishHandwriting41_50, EnglishHandwriting51_60, EnglishHandwriting61_70, EnglishHandwriting71_80, EnglishHandwriting81_90, EnglishHandwriting91_100;
    int Conversation0_40, Conversation41_50, Conversation51_60, Conversation61_70, Conversation71_80, Conversation81_90, Conversation91_100;
    int Maths0_40, Maths41_50, Maths51_60, Maths61_70, Maths71_80, Maths81_90, Maths91_100;
    int EVS0_40, EVS41_50, EVS51_60, EVS61_70, EVS71_80, EVS81_90, EVS91_100;
    int Rhymes0_40, Rhymes41_50, Rhymes51_60, Rhymes61_70, Rhymes71_80, Rhymes81_90, Rhymes91_100;
    int Hindi0_40, Hindi41_50, Hindi51_60, Hindi61_70, Hindi71_80, Hindi81_90, Hindi91_100;
    int Bengali0_40, Bengali41_50, Bengali51_60, Bengali61_70, Bengali71_80, Bengali81_90, Bengali91_100;
    int Drawing0_40, Drawing41_50, Drawing51_60, Drawing61_70, Drawing71_80, Drawing81_90, Drawing91_100;
    int PT0_40, PT41_50, PT51_60, PT61_70, PT71_80, PT81_90, PT91_100;
    int GK0_40, GK41_50, GK51_60, GK61_70, GK71_80, GK81_90, GK91_100;
    int MoralEd0_40, MoralEd41_50, MoralEd51_60, MoralEd61_70, MoralEd71_80, MoralEd81_90, MoralEd91_100;


    // set the header titles
    String headers[] = {
            "Subject Name",
            "0-40",
            "41-50",
            "51-60",
            "61-70",
            "71-80",
            "81-90",
            "91-100"

    };

    String[] SchoolClass = {"Nursery", "UKG", "LKG", "1", "2", "3", "4", "5"};
    String[] ExamType = {"UT 1", "Half Yearly", "UT 2", "Final"};

    TableLayout tableA;
    TableLayout tableB;
    TableLayout tableC;
    TableLayout tableD;

    HorizontalScrollView horizontalScrollViewB;
    HorizontalScrollView horizontalScrollViewD;

    ScrollView scrollViewC;
    ScrollView scrollViewD;

    Toolbar toolbar;

    Context context;

    String classSpinnerItem = "Nursery", examSpinnerItem = "UT 1";

//    List<SampleObjects> sampleObjects = this.sampleObjects();

    int headerCellsWidth[] = new int[headers.length];

    ArrayList<SampleObjects> sampleObjectList = new ArrayList<>();

    ProgressDialog mProgress;
    public TableMainLayout(final Context context) {

        super(context);

        this.context = context;


        // initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
        this.initComponents();
        this.setComponentsId();





        this.setScrollViewAndHorizontalScrollViewTag();


        // no need to assemble component A, since it is just a table
        this.horizontalScrollViewB.addView(this.tableB);

        this.scrollViewC.addView(this.tableC);

        this.scrollViewD.addView(this.horizontalScrollViewD);
        this.horizontalScrollViewD.addView(this.tableD);

        // add the components to be part of the main layout
        this.addComponentToMainLayout();
        this.setBackground(getResources().getDrawable(R.drawable.toolbarcolor));


        // add some table rows
        this.addTableRowToTableA();
        this.addTableRowToTableB();

        this.resizeHeaderHeight();

        this.getTableRowHeaderCellWidth();









        this.generateTableC_AndTable_B();

        this.resizeBodyTableRowHeight();




    }

    // this is just the sample data
//    List<SampleObjects> sampleObjects() {
//
//        List<SampleObjects> sampleObjects = new ArrayList<SampleObjects>();
//        Log.v("sampleItem", classSpinnerItem);
//        Log.v("examItem", examSpinnerItem);
//
//
//
//
//
//        SampleObjects sampleObject = new SampleObjects(
//                "English Literature",
//                Integer.toString(English0_40),
//                Integer.toString(English41_50),
//                Integer.toString(English51_60),
//                Integer.toString(English61_70),
//                Integer.toString(English71_80),
//                Integer.toString(English81_90),
//                Integer.toString(English91_100)
//        );
//        sampleObjects.add(sampleObject);
//
////        for (int x = 1; x <= 20; x++) {
////
////            SampleObjects sampleObject = new SampleObjects(
////                    "Col 1, Row " + x,
////                    "Col 2, Row " + x + " - multi-lines",
////                    "Col 3, Row " + x,
////                    "Col 4, Row " + x,
////                    "Col 5, Row " + x,
////                    "Col 6, Row " + x,
////                    "Col 7, Row " + x,
////                    "Col 8, Row " + x
////
////            );
////
////            sampleObjects.add(sampleObject);
////        }
//
//        return sampleObjects;
//
//    }

    // initalized components
    private void initComponents() {

        this.tableA = new TableLayout(this.context);
        this.tableB = new TableLayout(this.context);
        this.tableC = new TableLayout(this.context);
        this.tableD = new TableLayout(this.context);

        toolbar = new Toolbar(this.context);
        classSpinner = new Spinner(this.context);
        examSpinner = new Spinner(this.context);

        this.horizontalScrollViewB = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewD = new MyHorizontalScrollView(this.context);

        this.scrollViewC = new MyScrollView(this.context);
        this.scrollViewD = new MyScrollView(this.context);

        this.tableA.setBackgroundColor(Color.GREEN);
        this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);


    }

    // set essential component IDs
    @SuppressLint("ResourceType")
    private void setComponentsId() {
        this.tableA.setId(3);
        this.horizontalScrollViewB.setId(4);
        this.scrollViewC.setId(5);
        this.scrollViewD.setId(6);
        this.toolbar.setId(1);
        this.classSpinner.setId(0);
        this.examSpinner.setId(2);
    }

    // set tags for some horizontal and vertical scroll view
    private void setScrollViewAndHorizontalScrollViewTag() {

        this.horizontalScrollViewB.setTag("horizontal scroll view b");
        this.horizontalScrollViewD.setTag("horizontal scroll view d");

        this.scrollViewC.setTag("scroll view c");
        this.scrollViewD.setTag("scroll view d");
    }

    // we add the components here in our TableMainLayout
    private void addComponentToMainLayout() {

        // RelativeLayout params were very useful here
        // the addRule method is the key to arrange the components properly

        RelativeLayout.LayoutParams componentToolbar_Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 150);
        componentToolbar_Params.setMargins(0, 50, 0, 0);

        toolbar.setLayoutParams(componentToolbar_Params);
        toolbar.setPopupTheme(R.style.ToolbarTheme);
        toolbar.setBackground(getResources().getDrawable(R.drawable.toolbarcolor));
        toolbar.setTitle("Class Result");
        toolbar.setVisibility(View.VISIBLE);


        RelativeLayout.LayoutParams componentSpinnerClass = new RelativeLayout.LayoutParams(250, 50);
        componentSpinnerClass.setMargins(30, 0, 0, 50);

        componentSpinnerClass.addRule(RelativeLayout.BELOW, this.toolbar.getId());
        ArrayAdapter<String> ClassTypes = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, SchoolClass);
        ClassTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(ClassTypes);


        classSpinner.setOnItemSelectedListener(this);


        RelativeLayout.LayoutParams componentSpinnerExam = new RelativeLayout.LayoutParams(250, 50);
        componentSpinnerExam.setMargins(400, 0, 0, 50);

        componentSpinnerExam.addRule(RelativeLayout.BELOW, this.toolbar.getId());
        componentSpinnerExam.addRule(RelativeLayout.RIGHT_OF, this.classSpinner.getId());
        ArrayAdapter<String> ExamTypes = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ExamType);
        ExamTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        examSpinner.setAdapter(ExamTypes);


        examSpinner.setOnItemSelectedListener(this);


        RelativeLayout.LayoutParams componentA_Params = new RelativeLayout.LayoutParams(190, LayoutParams.WRAP_CONTENT);
        componentA_Params.setMargins(0, 300, 0, 0);
        componentA_Params.addRule(RelativeLayout.BELOW, this.classSpinner.getId());


        RelativeLayout.LayoutParams componentB_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentB_Params.setMargins(0, 300, 0, 0);
        componentB_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());
        componentB_Params.addRule(RelativeLayout.BELOW, this.classSpinner.getId());

        RelativeLayout.LayoutParams componentC_Params = new RelativeLayout.LayoutParams(190, LayoutParams.WRAP_CONTENT);
        componentC_Params.addRule(RelativeLayout.BELOW, this.tableA.getId());

        RelativeLayout.LayoutParams componentD_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentD_Params.addRule(RelativeLayout.RIGHT_OF, this.scrollViewC.getId());
        componentD_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewB.getId());

        // 'this' is a relative layout,
        // we extend this table layout as relative layout as seen during the creation of this class
        this.addView(this.toolbar);
        this.addView(this.tableA, componentA_Params);
        this.addView(this.horizontalScrollViewB, componentB_Params);
        this.addView(this.scrollViewC, componentC_Params);
        this.addView(this.scrollViewD, componentD_Params);
        this.addView(this.classSpinner, componentSpinnerClass);
        this.addView(this.examSpinner, componentSpinnerExam);



    }



    private void addTableRowToTableA() {
        this.tableA.addView(this.componentATableRow());
    }

    private void addTableRowToTableB() {
        this.tableB.addView(this.componentBTableRow());
    }

    // generate table row of table A
    TableRow componentATableRow() {

        TableRow componentATableRow = new TableRow(this.context);

        TextView textView = this.headerTextView(this.headers[0]);
        componentATableRow.addView(textView);

        return componentATableRow;
    }

    // generate table row of table B
    TableRow componentBTableRow() {

        TableRow componentBTableRow = new TableRow(this.context);
        int headerFieldCount = this.headers.length;

        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 0);


        for (int x = 0; x < (headerFieldCount - 1); x++) {
            TextView textView = this.headerTextView(this.headers[x + 1]);
            textView.setLayoutParams(params);
            componentBTableRow.addView(textView);
        }

        return componentBTableRow;
    }

    // generate table row of table C and table D
    private void generateTableC_AndTable_B() {

        // just seeing some header cell width
        for (int i : this.headerCellsWidth) {
            Log.v("TableMainLayout.java", i + "");
        }



        Log.v("sampleItem", classSpinnerItem);
        Log.v("examItem", examSpinnerItem);
        Log.v("english", Integer.toString(English51_60));




        SampleObjects sampleObject1 = new SampleObjects(
                "English Literature",
                Integer.toString(English0_40),
                Integer.toString(English41_50),
                Integer.toString(English51_60),
                Integer.toString(English61_70),
                Integer.toString(English71_80),
                Integer.toString(English81_90),
                Integer.toString(English91_100)
        );

        sampleObjectList.add(sampleObject1);




        //        for (int x = 1; x <= 20; x++) {
        //
        //            SampleObjects sampleObject = new SampleObjects(
        //                    "Col 1, Row " + x,
        //                    "Col 2, Row " + x + " - multi-lines",
        //                    "Col 3, Row " + x,
        //                    "Col 4, Row " + x,
        //                    "Col 5, Row " + x,
        //                    "Col 6, Row " + x,
        //                    "Col 7, Row " + x,
        //                    "Col 8, Row " + x
        //
        //            );
        //
        //            sampleObjects.add(sampleObject);
        //        }



        for (SampleObjects sampleObject : sampleObjectList) {

            TableRow tableRowForTableC = this.tableRowForTableC(sampleObject);
            TableRow taleRowForTableD = this.taleRowForTableD(sampleObject);

            tableRowForTableC.setBackgroundColor(Color.LTGRAY);
            taleRowForTableD.setBackgroundColor(Color.LTGRAY);

            this.tableC.addView(tableRowForTableC);
            this.tableD.addView(taleRowForTableD);

        }
    }

    // a TableRow for table C
    TableRow tableRowForTableC(SampleObjects sampleObject) {

        TableRow.LayoutParams params = new TableRow.LayoutParams(this.headerCellsWidth[0], LayoutParams.MATCH_PARENT);
        params.setMargins(0, 2, 0, 0);

        TableRow tableRowForTableC = new TableRow(this.context);
        TextView textView = this.bodyTextView(sampleObject.header1);
        tableRowForTableC.addView(textView, params);

        return tableRowForTableC;
    }

    TableRow taleRowForTableD(SampleObjects sampleObject) {

        TableRow taleRowForTableD = new TableRow(this.context);

        int loopCount = ((TableRow) this.tableB.getChildAt(0)).getChildCount();
        String info[] = {
                sampleObject.header2,
                sampleObject.header3,
                sampleObject.header4,
                sampleObject.header5,
                sampleObject.header6,
                sampleObject.header7,
                sampleObject.header8,

        };

        for (int x = 0; x < loopCount; x++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(headerCellsWidth[x + 1], LayoutParams.MATCH_PARENT);
            params.setMargins(2, 2, 0, 0);

            TextView textViewB = this.bodyTextView(info[x]);
            taleRowForTableD.addView(textViewB, params);
        }

        return taleRowForTableD;

    }

    // table cell standard TextView
    TextView bodyTextView(String label) {

        TextView bodyTextView = new TextView(this.context);
        bodyTextView.setBackgroundColor(Color.WHITE);
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setPadding(5, 5, 5, 5);

        return bodyTextView;
    }

    // header standard TextView
    TextView headerTextView(String label) {

        TextView headerTextView = new TextView(this.context);

        headerTextView.setBackgroundColor(Color.WHITE);
        headerTextView.setText(label);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(5, 5, 5, 5);

        return headerTextView;
    }

    // resizing TableRow height starts here
    void resizeHeaderHeight() {

        TableRow productNameHeaderTableRow = (TableRow) this.tableA.getChildAt(0);
        TableRow productInfoTableRow = (TableRow) this.tableB.getChildAt(0);

        int rowAHeight = this.viewHeight(productNameHeaderTableRow);
        int rowBHeight = this.viewHeight(productInfoTableRow);

        TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
        int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

        this.matchLayoutHeight(tableRow, finalHeight);
    }

    void getTableRowHeaderCellWidth() {

        int tableAChildCount = ((TableRow) this.tableA.getChildAt(0)).getChildCount();
        int tableBChildCount = ((TableRow) this.tableB.getChildAt(0)).getChildCount();


        for (int x = 0; x < (tableAChildCount + tableBChildCount); x++) {

            if (x == 0) {
                this.headerCellsWidth[x] = this.viewWidth(((TableRow) this.tableA.getChildAt(0)).getChildAt(x));
            } else {
                this.headerCellsWidth[x] = this.viewWidth(((TableRow) this.tableB.getChildAt(0)).getChildAt(x - 1));
            }

        }
    }

    // resize body table row height
    void resizeBodyTableRowHeight() {

        int tableC_ChildCount = this.tableC.getChildCount();

        for (int x = 0; x < tableC_ChildCount; x++) {

            TableRow productNameHeaderTableRow = (TableRow) this.tableC.getChildAt(x);
            TableRow productInfoTableRow = (TableRow) this.tableD.getChildAt(x);

            int rowAHeight = this.viewHeight(productNameHeaderTableRow);
            int rowBHeight = this.viewHeight(productInfoTableRow);

            TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
            int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

            this.matchLayoutHeight(tableRow, finalHeight);
        }

    }

    // match all height in a table row
    // to make a standard TableRow height
    private void matchLayoutHeight(TableRow tableRow, int height) {

        int tableRowChildCount = tableRow.getChildCount();

        // if a TableRow has only 1 child
        if (tableRow.getChildCount() == 1) {

            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);

            return;
        }

        // if a TableRow has more than 1 child
        for (int x = 0; x < tableRowChildCount; x++) {

            View view = tableRow.getChildAt(x);

            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();

            if (!isTheHeighestLayout(tableRow, x)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                return;
            }
        }

    }

    // check if the view has the highest height in a TableRow
    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {

        int tableRowChildCount = tableRow.getChildCount();
        int heighestViewPosition = -1;
        int viewHeight = 0;

        for (int x = 0; x < tableRowChildCount; x++) {
            View view = tableRow.getChildAt(x);
            int height = this.viewHeight(view);

            if (viewHeight < height) {
                heighestViewPosition = x;
                viewHeight = height;
            }
        }

        return heighestViewPosition == layoutPosition;
    }

    // read a view's height
    private int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    private void getFirebaseData(){
        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Loading");
        mProgress.setCanceledOnTouchOutside(false);


        mStudentMarks = FirebaseDatabase.getInstance().getReference().child("Student_Marks");
        try {
            if (classSpinnerItem.equalsIgnoreCase("Nursery")) {
                if (examSpinnerItem.equalsIgnoreCase("UT 1") || examSpinnerItem.equalsIgnoreCase("UT 2") ||
                        examSpinnerItem.equalsIgnoreCase("Final") || examSpinnerItem.equalsIgnoreCase("Half Yearly")) {
                    mStudentMarks.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(classSpinnerItem)){
                                mStudentMarks.child(classSpinnerItem).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(examSpinnerItem)){
                                            mProgress.dismiss();
                                            mStudentMarks.child(classSpinnerItem).child(examSpinnerItem).addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                                    int englishLit = Integer.parseInt(snapshot.child("English_Literature").getValue().toString());
                                                    Toast.makeText(context,String.valueOf(englishLit), Toast.LENGTH_SHORT).show();
                                                    if (englishLit <= 40) {
                                                        English0_40++;
                                                        Toast.makeText(context, String.valueOf(English0_40), Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 40 && englishLit <= 50) {
                                                        English41_50++;
                                                        Toast.makeText(context, "English41-50"+String.valueOf(English41_50), Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 50 && englishLit <= 60) {
                                                        English51_60++;
                                                        Toast.makeText(context,"English51-60 "+String.valueOf(English51_60) , Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 60 && englishLit <= 70) {
                                                        English61_70++;
                                                        Toast.makeText(context,String.valueOf(English61_70) , Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 70 && englishLit <= 80) {
                                                        English71_80++;
                                                        Toast.makeText(context, String.valueOf(English71_80), Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 80 && englishLit <= 90) {
                                                        English81_90++;
                                                        Toast.makeText(context,String.valueOf(English81_90) , Toast.LENGTH_SHORT).show();
                                                    } else if (englishLit > 90 && englishLit <= 100) {
                                                        English91_100++;
                                                        Toast.makeText(context,String.valueOf(English91_100) , Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                    mProgress.dismiss();
                                                }

                                                @Override
                                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                                    mProgress.dismiss();
                                                }

                                                @Override
                                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                    mProgress.dismiss();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    mProgress.dismiss();
                                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    mProgress.dismiss();
                    Toast.makeText(context, "not in exam", Toast.LENGTH_SHORT).show();
                }
            }else {
                mProgress.dismiss();
                Toast.makeText(context, "not in class", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            mProgress.dismiss();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    // read a view's width
    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getId() == this.classSpinner.getId() || parent.getId() == this.examSpinner.getId()) {

            classSpinnerItem = SchoolClass[position];
            examSpinnerItem = ExamType[position];

            getFirebaseData();



        }







    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
  classSpinnerItem = SchoolClass[0];
  examSpinnerItem = ExamType[0];

    }

    // horizontal scroll view custom class
    class MyHorizontalScrollView extends HorizontalScrollView {

        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if (tag.equalsIgnoreCase("horizontal scroll view b")) {
                horizontalScrollViewD.scrollTo(l, 0);
            } else {
                horizontalScrollViewB.scrollTo(l, 0);
            }
        }

    }

    // scroll view custom class
    class MyScrollView extends ScrollView {

        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {

            String tag = (String) this.getTag();

            if (tag.equalsIgnoreCase("scroll view c")) {
                scrollViewD.scrollTo(0, t);
            } else {
                scrollViewC.scrollTo(0, t);
            }
        }

    }


}
