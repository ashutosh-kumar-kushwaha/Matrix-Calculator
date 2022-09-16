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
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Rank extends Activity {

    int[] maxLengthArray;
    int[] widthArray;
    int[] widthDimenArray;
    int rows;
    int columns;

    public void maximumLength(ArrayList<ArrayList<String>> matrix){
        int max;
        int length;
        for (int i = 0; i < columns; i++){
            max = 1;
            for (int j = 0; j < rows; j++){
                length = matrix.get(j).get(i).length();
                if ( length > max){
                    max = length;
                }
            }
            maxLengthArray[i] = max;
            widthArray[i] = max * 10;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Intent intent = getIntent();
        ArrayList<String> stringArrayList = intent.getStringArrayListExtra("stringArrayList");
        ArrayList<ArrayList<ArrayList<String>>> matrixArrayList = (ArrayList<ArrayList<ArrayList<String>>>) intent.getSerializableExtra("matrixArrayList");

        rows = matrixArrayList.get(0).size();
        columns = matrixArrayList.get(0).get(0).size();
        maxLengthArray = new int[columns];  // for maximum length element in a column
        widthArray = new int[columns];      // for width of each column in dp
        widthDimenArray = new int[columns];  // for dimensions of each column

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
//        ConstraintSet set = new ConstraintSet();

        int index = 0;

        int sum;

        @SuppressLint("Recycle") TypedArray dimensions = getResources().obtainTypedArray(R.array.dimensions);
//        int previousView = R.id.constraintLayout;
        for (int k = 0; k < stringArrayList.size(); k++){

            if (stringArrayList.get(k).equals("Matrix")){
                ArrayList<ArrayList<String>> matrix = matrixArrayList.get(index);
                maximumLength(matrix);
                sum = 0;
                for (int i = 0; i < columns; i++) {
                    sum += widthArray[i];
                    widthDimenArray[i] = (int) getResources().getDimension(dimensions.getResourceId(widthArray[i], R.dimen.dp_1000));
                }

                GridLayout gridLayout = new GridLayout(this);
                gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
                gridLayout.setRowCount(rows);
                gridLayout.setColumnCount(columns);
                gridLayout.setBackgroundResource(R.drawable.matrix);
                gridLayout.setId(View.generateViewId());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(sum + columns * 2 * 10, R.dimen.dp_1000)), (int) getResources().getDimension(dimensions.getResourceId(rows * 42, R.dimen.dp_1000)));
                params.height = (int) getResources().getDimension(dimensions.getResourceId(rows * 42, R.dimen.dp_1000));
                params.width = (int) getResources().getDimension(dimensions.getResourceId(sum + columns * 2 * 10, R.dimen.dp_1000));
                params.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
//              params.setGravity();

                gridLayout.setLayoutParams(params);

                linearLayout.addView(gridLayout);


//                constraintLayout.addView(gridLayout);
//                set.connect(gridLayout.getId(), ConstraintSet.TOP, previousView, ConstraintSet.TOP, 18);
//                set.connect(gridLayout.getId(), ConstraintSet.LEFT, previousView, ConstraintSet.LEFT, 18);
//                set.connect(gridLayout.getId(), ConstraintSet.RIGHT, previousView, ConstraintSet.RIGHT, 18);
//                set.applyTo(constraintLayout);
//                previousView = gridLayout.getId();
                index++;


                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {

                        TextView textView = new TextView(this);
                        textView.setText(matrix.get(i).get(j));
                        gridLayout.addView(textView);
                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                        param.height = (int) getResources().getDimension(R.dimen.dp_22);
                        param.width = widthDimenArray[j];
                        param.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                        param.setGravity(Gravity.CENTER);
                        param.columnSpec = GridLayout.spec(j);
                        param.rowSpec = GridLayout.spec(i);
                        textView.setLayoutParams(param);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                        textView.setMaxLines(1);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
//                      textView.setBackgroundResource(R.drawable.square);
                    }

                }

            }
            else{
                TextView textView = new TextView(this);
                textView.setText(stringArrayList.get(k));
                ConstraintLayout.LayoutParams param = new ConstraintLayout.LayoutParams((int) getResources().getDimension(dimensions.getResourceId(stringArrayList.get(k).length() * 10, R.dimen.dp_1000)),(int) getResources().getDimension(R.dimen.dp_42));
                param.height = (int) getResources().getDimension(R.dimen.dp_22);
                param.width = (int) getResources().getDimension(dimensions.getResourceId(stringArrayList.get(k).length() * 10, R.dimen.dp_1000));
                param.setMargins((int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_10));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(param);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//                textView.setTextSize((int) getResources().getDimension(R.dimen.sp_8));
                textView.setMaxLines(1);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                textView.setId(View.generateViewId());
                linearLayout.addView(textView);

//                set.connect(textView.getId(), ConstraintSet.TOP, previousView, ConstraintSet.TOP, 18);
//                set.connect(textView.getId(), ConstraintSet.LEFT, previousView, ConstraintSet.LEFT, 18);
//                set.connect(textView.getId(), ConstraintSet.RIGHT, previousView, ConstraintSet.RIGHT, 18);
//                set.applyTo(constraintLayout);
//                previousView = textView.getId();
            }

        }


//        set.clone(constraintLayout);

    }


}