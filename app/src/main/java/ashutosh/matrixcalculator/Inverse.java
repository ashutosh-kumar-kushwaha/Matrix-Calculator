package ashutosh.matrixcalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Inverse extends Activity {

    int[] maxLengthArray;
    int[] widthArray;
    int[] widthDimenArray;
    int order;

    public void maximumLength(ArrayList<ArrayList<String>> matrix){
        int max;
        int length;
        for (int i = 0; i < order; i++){
            max = 1;
            for (int j = 0; j < order; j++){
                length = matrix.get(j).get(i).length();
                if ( length > max){
                    max = length;
                }
            }
            maxLengthArray[i] = max;
            widthArray[i] = max * 10;
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse);

        Intent intent = getIntent();
        ArrayList<String> stringArrayList = intent.getStringArrayListExtra("stringArrayList");
        ArrayList<ArrayList<ArrayList<String>>> originalMatrixArrayList = (ArrayList<ArrayList<ArrayList<String>>>) intent.getSerializableExtra("originalMatrixArrayList");
        ArrayList<ArrayList<ArrayList<String>>> identityMatrixArrayList = (ArrayList<ArrayList<ArrayList<String>>>) intent.getSerializableExtra("identityMatrixArrayList");

        order = originalMatrixArrayList.get(0).size();
        maxLengthArray = new int[order];
        widthArray = new int[order];
        widthDimenArray = new int[order];
        ArrayList<ArrayList<String>> originalMatrix;
        ArrayList<ArrayList<String>> identityMatrix;

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintSet set = new ConstraintSet();

        int index = 0;
        int sum;

        @SuppressLint("Recycle") TypedArray dimensions = getResources().obtainTypedArray(R.array.dimensions);
        int previousViewTop;
        int previousViewLeft;
        GridLayout gridLayout;
        ViewGroup.LayoutParams params;
        TextView textView;
        GridLayout.LayoutParams param2;
        ConstraintLayout.LayoutParams param;







        //--------------------------------matrix-----------------------------------------------------------------------------

        originalMatrix = originalMatrixArrayList.get(0);
        maximumLength(originalMatrix);
        sum = 0;
        for (int i = 0; i < order; i++) {
            sum += widthArray[i];
            widthDimenArray[i] = (int) getResources().getDimension(dimensions.getResourceId(widthArray[i], R.dimen.dp_1000));
        }
        gridLayout = new GridLayout(this);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setRowCount(order);
        gridLayout.setColumnCount(order);
        gridLayout.setBackgroundResource(R.drawable.matrix);
        gridLayout.setId(View.generateViewId());
        params = new ViewGroup.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000)), (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000)));
        params.height = (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000));
        params.width = (int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000));

        gridLayout.setLayoutParams(params);


        constraintLayout.addView(gridLayout);
        set.clone(constraintLayout);

        set.connect(gridLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) getResources().getDimension(R.dimen.dp_20));
        set.connect(gridLayout.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_70));
        set.applyTo(constraintLayout);


        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {

                textView = new TextView(this);
                textView.setText(originalMatrix.get(i).get(j));
                gridLayout.addView(textView);
                param2 = new GridLayout.LayoutParams();
                param2.height = (int) getResources().getDimension(R.dimen.dp_22);
                param2.width = widthDimenArray[j];
                param2.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                param2.setGravity(Gravity.CENTER);
                param2.columnSpec = GridLayout.spec(j);
                param2.rowSpec = GridLayout.spec(i);
                textView.setLayoutParams(param2);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                textView.setMaxLines(1);
                textView.setTextColor(Color.parseColor("#000000"));
                        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            }
        }
        previousViewTop = gridLayout.getId();

        // A =

        textView = new TextView(this);
        textView.setText("A =");
        param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_30), (int) getResources().getDimension(R.dimen.dp_22));
        param.width = (int) getResources().getDimension(R.dimen.dp_30);
        param.height = (int) getResources().getDimension(R.dimen.dp_22);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(param);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
        textView.setMaxLines(1);
        textView.setTextColor(Color.parseColor("#000000"));
                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setId(View.generateViewId());
        constraintLayout.addView(textView);
        set.clone(constraintLayout);

        set.connect(textView.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.TOP);
        set.connect(textView.getId(), ConstraintSet.BOTTOM, previousViewTop, ConstraintSet.BOTTOM);
        set.connect(textView.getId(), ConstraintSet.RIGHT, previousViewTop, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_20));
        set.applyTo(constraintLayout);

        //------ A ~ I ------------------------------------------------------------------------------

        textView = new TextView(this);
        textView.setText("A ~ I");
        param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_50), (int) getResources().getDimension(R.dimen.dp_22));
        param.width = (int) getResources().getDimension(R.dimen.dp_50);
        param.height = (int) getResources().getDimension(R.dimen.dp_22);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(param);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
        textView.setMaxLines(1);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setId(View.generateViewId());
        constraintLayout.addView(textView);
        set.clone(constraintLayout);

        set.connect(textView.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.dp_20));
        set.connect(textView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_20));
        set.applyTo(constraintLayout);
        previousViewTop = textView.getId();

//--------------------------------------------------------------------------------------------------------



        for (int k = 0; k < stringArrayList.size(); k++){

            if (stringArrayList.get(k).equals("Matrix")){
                originalMatrix = originalMatrixArrayList.get(index);
                maximumLength(originalMatrix);
                sum = 0;
                for (int i = 0; i < order; i++) {
                    sum += widthArray[i];
                    widthDimenArray[i] = (int) getResources().getDimension(dimensions.getResourceId(widthArray[i], R.dimen.dp_1000));
                }

                gridLayout = new GridLayout(this);
                gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
                gridLayout.setRowCount(order);
                gridLayout.setColumnCount(order);
                gridLayout.setBackgroundResource(R.drawable.matrix);
                gridLayout.setId(View.generateViewId());
                params = new ViewGroup.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000)), (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000)));
                params.height = (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000));
                params.width = (int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000));

                gridLayout.setLayoutParams(params);


                constraintLayout.addView(gridLayout);
                set.clone(constraintLayout);

                set.connect(gridLayout.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.dp_20));


                set.connect(gridLayout.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_20));
                set.applyTo(constraintLayout);


                for (int i = 0; i < order; i++) {
                    for (int j = 0; j < order; j++) {

                        textView = new TextView(this);
                        textView.setText(originalMatrix.get(i).get(j));
                        gridLayout.addView(textView);
                        param2 = new GridLayout.LayoutParams();
                        param2.height = (int) getResources().getDimension(R.dimen.dp_22);
                        param2.width = widthDimenArray[j];
                        param2.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                        param2.setGravity(Gravity.CENTER);
                        param2.columnSpec = GridLayout.spec(j);
                        param2.rowSpec = GridLayout.spec(i);
                        textView.setLayoutParams(param2);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                        textView.setMaxLines(1);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                }
                previousViewLeft = gridLayout.getId();

                textView = new TextView(this);
                textView.setText("~");
                param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_22));
                param.width = (int) getResources().getDimension(R.dimen.dp_10);
                param.height = (int) getResources().getDimension(R.dimen.dp_22);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(param);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                textView.setMaxLines(1);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                textView.setId(View.generateViewId());
                constraintLayout.addView(textView);
                set.clone(constraintLayout);

                set.connect(textView.getId(), ConstraintSet.TOP, previousViewLeft, ConstraintSet.TOP);
                set.connect(textView.getId(), ConstraintSet.BOTTOM, previousViewLeft, ConstraintSet.BOTTOM);
                set.connect(textView.getId(), ConstraintSet.LEFT, previousViewLeft, ConstraintSet.RIGHT, (int) getResources().getDimension(R.dimen.dp_20));
                set.applyTo(constraintLayout);
                previousViewLeft = textView.getId();



                identityMatrix = identityMatrixArrayList.get(index);
                maximumLength(identityMatrix);
                sum = 0;
                for (int i = 0; i < order; i++) {
                    sum += widthArray[i];
                    widthDimenArray[i] = (int) getResources().getDimension(dimensions.getResourceId(widthArray[i], R.dimen.dp_1000));
                }

                gridLayout = new GridLayout(this);
                gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
                gridLayout.setRowCount(order);
                gridLayout.setColumnCount(order);
                gridLayout.setBackgroundResource(R.drawable.matrix);
                gridLayout.setId(View.generateViewId());
                params = new ViewGroup.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000)), (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000)));
                params.height = (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000));
                params.width = (int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000));

                gridLayout.setLayoutParams(params);


                constraintLayout.addView(gridLayout);
                set.clone(constraintLayout);

                set.connect(gridLayout.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.dp_20));

                set.connect(gridLayout.getId(), ConstraintSet.LEFT, previousViewLeft, ConstraintSet.RIGHT, (int) getResources().getDimension(R.dimen.dp_20));
                set.applyTo(constraintLayout);

                for (int i = 0; i < order; i++) {
                    for (int j = 0; j < order; j++) {

                        textView = new TextView(this);
                        textView.setText(identityMatrix.get(i).get(j));
                        gridLayout.addView(textView);
                        param2 = new GridLayout.LayoutParams();
                        param2.height = (int) getResources().getDimension(R.dimen.dp_22);
                        param2.width = widthDimenArray[j];
                        param2.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                        param2.setGravity(Gravity.CENTER);
                        param2.columnSpec = GridLayout.spec(j);
                        param2.rowSpec = GridLayout.spec(i);
                        textView.setLayoutParams(param2);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                        textView.setMaxLines(1);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                }






                previousViewTop = gridLayout.getId();
                index++;

            }
            else{
                textView = new TextView(this);
                textView.setText(stringArrayList.get(k));
                param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(stringArrayList.get(k).length() * 10, 1000)),(int) getResources().getDimension(R.dimen.dp_22));
                param.height = (int) getResources().getDimension(R.dimen.dp_22);
                param.width = (int) getResources().getDimension(dimensions.getResourceId(stringArrayList.get(k).length() * 10, 1000));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(param);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                textView.setMaxLines(1);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                textView.setId(View.generateViewId());
                constraintLayout.addView(textView);
                set.clone(constraintLayout);

                set.connect(textView.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.dp_20));
                set.connect(textView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_20));
                set.applyTo(constraintLayout);
                previousViewTop = textView.getId();
            }

        }











        //--------------------------------matrix-----------------------------------------------------------------------------

        identityMatrix = identityMatrixArrayList.get(identityMatrixArrayList.size() - 1);
        maximumLength(identityMatrix);
        sum = 0;
        for (int i = 0; i < order; i++) {
            sum += widthArray[i];
            widthDimenArray[i] = (int) getResources().getDimension(dimensions.getResourceId(widthArray[i], R.dimen.dp_1000));
        }
        gridLayout = new GridLayout(this);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setRowCount(order);
        gridLayout.setColumnCount(order);
        gridLayout.setBackgroundResource(R.drawable.matrix);
        gridLayout.setId(View.generateViewId());
        params = new ViewGroup.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000)), (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000)));
        params.height = (int) getResources().getDimension(dimensions.getResourceId(order * 42, R.dimen.dp_1000));
        params.width = (int) getResources().getDimension(dimensions.getResourceId(sum + order * 2 * 10, R.dimen.dp_1000));

        gridLayout.setLayoutParams(params);


        constraintLayout.addView(gridLayout);
        set.clone(constraintLayout);

        set.connect(gridLayout.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.dp_20));
        set.connect(gridLayout.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen.dp_140));
        set.applyTo(constraintLayout);


        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {

                textView = new TextView(this);
                textView.setText(identityMatrix.get(i).get(j));
                gridLayout.addView(textView);
                param2 = new GridLayout.LayoutParams();
                param2.height = (int) getResources().getDimension(R.dimen.dp_22);
                param2.width = widthDimenArray[j];
                param2.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                param2.setGravity(Gravity.CENTER);
                param2.columnSpec = GridLayout.spec(j);
                param2.rowSpec = GridLayout.spec(i);
                textView.setLayoutParams(param2);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                textView.setMaxLines(1);
                textView.setTextColor(Color.parseColor("#000000"));
                        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            }
        }

        previousViewTop = gridLayout.getId();


        // Inverse of A =

        textView = new TextView(this);
        textView.setText("Inverse of A =");
        param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_140), (int) getResources().getDimension(R.dimen.dp_22));
        param.width = (int) getResources().getDimension(R.dimen.dp_140);
        param.height = (int) getResources().getDimension(R.dimen.dp_22);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(param);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
        textView.setMaxLines(1);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setId(View.generateViewId());
        constraintLayout.addView(textView);
        set.clone(constraintLayout);

        set.connect(textView.getId(), ConstraintSet.TOP, previousViewTop, ConstraintSet.TOP);
        set.connect(textView.getId(), ConstraintSet.BOTTOM, previousViewTop, ConstraintSet.BOTTOM);
        set.connect(textView.getId(), ConstraintSet.RIGHT, previousViewTop, ConstraintSet.LEFT);
        set.applyTo(constraintLayout);
//--------------------------------------------------------------------------------------------------------






//        set.clone(constraintLayout);
    }
}