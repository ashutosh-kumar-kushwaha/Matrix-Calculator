package ashutosh.matrixcalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayout;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;

public class EnterMatrixForRank extends Activity {
    int rows;
    int columns;
    int[][] textViewsId;

    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> matrixArrayList = new ArrayList<>();

    public void printMatrix(RationalNumber[][] matrix){

        int columns = matrix[0].length;
        ArrayList<ArrayList<String>> arr2d = new ArrayList<>();
        ArrayList<String> arr1d;
        for (RationalNumber[] rationalNumbers : matrix) {
            arr1d = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                arr1d.add(rationalNumbers[j].toString());
            }
            arr2d.add(arr1d);
        }

        stringArrayList.add("Matrix");
        matrixArrayList.add(arr2d);
    }

    public void interchangeRows(RationalNumber[][] matrix, int row1, int row2){
        RationalNumber[] tempRow = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = tempRow;
    }

    public void interchangeColumns(RationalNumber[][] matrix, int column1, int column2){
        int rows = matrix.length;
        int columns = matrix[0].length;
        RationalNumber[] tempColumn = new RationalNumber[rows];
        for (int i = 0; i < columns; i++){
            tempColumn[i] = matrix[i][column1];
        }
        for (int i = 0; i < columns; i++){
            matrix[i][column1] = matrix[i][column2];
            matrix[i][column2] = tempColumn[i];
        }
    }

    public void multiplyRow(RationalNumber[][] matrix, int row, RationalNumber number){
        int columns = matrix[0].length;
        for (int i = 0; i < columns; i++){
            matrix[row][i] = matrix[row][i].multiply(number);
        }
    }

    public void multiplyColumn(RationalNumber[][] matrix, int column, RationalNumber number){
        int rows = matrix.length;
        for (int i = 0; i < rows; i++){
            matrix[i][column] = matrix[i][column].multiply(number);
        }
    }

    public void elementaryRowOperation(RationalNumber[][] matrix, int row1, RationalNumber number,int row2){
        int columns = matrix[0].length;
        for (int i = 0; i < columns; i++){
            matrix[row1][i] = matrix[row1][i].add(matrix[row2][i].multiply(number));
        }
    }

    public void elementaryColumnOperation(RationalNumber[][] matrix, int column1, RationalNumber number, int column2){
        int rows = matrix.length;
        for (int i = 0; i < rows; i++){
            matrix[i][column1] = matrix[i][column1].add(matrix[i][column2].multiply(number));
        }
    }

    public int rank(RationalNumber[][] matrix){
        int rows = matrix.length;
        int columns = matrix[0].length;
        RationalNumber one = new RationalNumber(1,1);
        RationalNumber minusOne = new RationalNumber(-1,1);
        RationalNumber number;

        for (int i = 0; i < Math.min(rows, columns); i++){
            if (!matrix[i][i].equals(one)){
                boolean isInterchanged = false;
                if(matrix[i][i].num ==0){

                    // Interchange rows
                    for (int j = i+1; j < rows; j++){
                        if (matrix[j][i].num != 0){
                            interchangeRows(matrix, i, j);
                            stringArrayList.add("R" + (i+1) + "  <->  " + "R" + (j+1));
                            printMatrix(matrix);
                            isInterchanged = true;
                            break;
                        }
                    }

                    // Interchange columns
                    if (!isInterchanged){
                        for (int j = i + 1; j < columns; j++){
                            if (matrix[i][j].num != 0){
                                interchangeColumns(matrix, i, j);
                                stringArrayList.add("C" + (i+1) + "  <->  C" + (j+1));
                                printMatrix(matrix);
                                break;
                            }
                        }
                    }
                }

                // if matrix[i][i] != 1 then make matrix[i][i] == 1 by multiplying ith row
                if (matrix[i][i].num != 0 & !matrix[i][i].equals(one)) {
                    number = matrix[i][i].reciprocal();
                    multiplyRow(matrix, i, number);
                    stringArrayList.add("R" + (i + 1) + "  ->  (" + number.toString() + ") * R" + (i + 1));
                    printMatrix(matrix);
                }
            }

            // make all elements of ith column except aii = 0 by using row transformation
            for (int j = i + 1; j < rows; j++){
                if (i != j && matrix[j][i].num != 0){
                    number = matrix[j][i].multiply(minusOne);
                    elementaryRowOperation(matrix, j, number, i);
                    stringArrayList.add("R" + (j + 1) + "  ->  R" + (j+1) + "  +  (" + number.toString() + ") * R" + (i+1));
                    printMatrix(matrix);
                }
            }

            // make all elements of ith row except aii = 0 by using column transformation
            for (int j = i + 1; j < columns; j++){
                if (i != j && matrix[i][j].num != 0){
                    number = matrix[i][j].multiply(minusOne);
                    elementaryColumnOperation(matrix, j, number, i);
                    stringArrayList.add("C" + (j + 1) + "  ->  C" + (j+1) + "  +  (" + number.toString() + ") * C" + (i+1));
                    printMatrix(matrix);
                }
            }


        }

        int rank = 0;
        for (int i = 0; i < Math.min(rows, columns); i++){
            if (matrix[i][i].equals(one)){
                rank++;
            }
            else{
                stringArrayList.add("Rank : " + rank);
                return rank;
            }
        }
        stringArrayList.add("Rank : " + rank);
        return rank;
    }

    public void calculateRank(View view) {
        RationalNumber[][] matrix = new RationalNumber[rows][columns];

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                EditText editText =  findViewById(textViewsId[i][j]);
                RationalNumber number = new RationalNumber(Integer.parseInt(editText.getText().toString()));
                matrix[i][j] = number;
            }
        }
        printMatrix(matrix);
        rank(matrix);

        Intent intent = new Intent(getApplicationContext(), Rank.class);
        intent.putExtra("stringArrayList", stringArrayList);
        intent.putExtra("matrixArrayList", matrixArrayList);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_matrix_for_rank);
        Intent intent = getIntent();
        rows = intent.getIntArrayExtra("order")[0];
        columns = intent.getIntArrayExtra("order")[1];
        int gridLayoutHeight;
        int gridLayoutWidth;
        int textBoxSide;
        int fontSize;
        @SuppressLint("Recycle") TypedArray dimensions = getResources().obtainTypedArray(R.array.dimensions);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int max = Math.max(rows, columns);
        if(max == 1){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 85, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(max == 2){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 85, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(max == 3){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 85, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(max == 4){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_64);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 64, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 64, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_13);
        }
        else if(max == 5){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_51);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 51, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 51, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_10);
        }
        else{
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_43);
            gridLayoutHeight = (int) getResources().getDimension(dimensions.getResourceId(rows * 43, R.dimen.dp_1000));
            gridLayoutWidth = (int) getResources().getDimension(dimensions.getResourceId(columns * 43, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_7);
        }

        textViewsId = new int[rows][columns];
        ViewGroup.LayoutParams params = gridLayout.getLayoutParams();
        params.width = gridLayoutWidth;
        params.height = gridLayoutHeight;
        gridLayout.setLayoutParams(params);
        GridLayout.LayoutParams param;


        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                EditText editText = new EditText(this);
                editText.setBackgroundResource(R.drawable.square);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText.setTextColor(Color.parseColor("#000000"));
//                editText.setAutoSizeTextTypeWithDefaults(EditText.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                editText.setTextSize(fontSize);
                param = new GridLayout.LayoutParams();
                param.height = textBoxSide;
                param.width = textBoxSide;
                param.columnSpec = GridLayout.spec(j);
                param.rowSpec = GridLayout.spec(i);
                textViewsId[i][j] = View.generateViewId();
                editText.setId(textViewsId[i][j]);
                editText.setLayoutParams(param);
                gridLayout.addView(editText);
            }
        }


    }
}