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
import android.widget.Toast;

import java.util.ArrayList;

public class EnterMatrixForInverse extends Activity {
    int order;
    int[][] textViewsId;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> originalMatrixArrayList = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> identityMatrixArrayList = new ArrayList<>();

    public ArrayList<ArrayList<String>> returnMatrix(RationalNumber[][] matrix){
        int order = matrix.length;
        ArrayList<ArrayList<String>> arr2d = new ArrayList<>();
        ArrayList<String> arr1d;
        for (RationalNumber[] rationalNumbers : matrix) {
            arr1d = new ArrayList<>();
            for (int j = 0; j < order; j++) {
                arr1d.add(rationalNumbers[j].toString());
            }
            arr2d.add(arr1d);
        }
        return arr2d;
    }

    public static int[][] deleteRowAndColumn(int[][] matrix, int row, int column){
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[][] result = new int[rows - 1][columns - 1];
        int i2 = 0;
        int j2;
        for (int i = 0; i < rows - 1; i++){
            j2 = 0;
            for (int j = 0; j < columns - 1; j++){
                if (i2 == row){
                    i2++;
                }
                if (j2 == column){
                    j2++;
                }
                result[i][j] = matrix[i2][j2];
                j2++;
            }
            i2++;
        }
        return result;
    }

    public static int determinant(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int det = 0;
        if (rows == columns) {
            if (rows == 2) {
                return (matrix[1][1] * matrix[0][0] - matrix[0][1] * matrix[1][0]);
            }
            else{
                for (int i = 0; i < columns; i++){
                    det += Math.pow(-1, i) * (matrix[0][i] * determinant(deleteRowAndColumn(matrix, 0, i)));
                }
            }
        }
        return det;
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

    public void inverse(RationalNumber[][] matrix){
        int order = matrix.length;
        RationalNumber[][] identityMatrix = new RationalNumber[order][order];
        for (int i = 0; i < order; i++){
            for (int j = 0; j < order; j++){
                if (i==j){
                    identityMatrix[i][j] = new RationalNumber(1);
                }
                else{
                    identityMatrix[i][j] = new RationalNumber();
                }
            }
        }

        stringArrayList.add("Matrix");
        originalMatrixArrayList.add(returnMatrix(matrix));
        identityMatrixArrayList.add(returnMatrix(identityMatrix));

        RationalNumber one = new RationalNumber(1,1);
        RationalNumber minusOne = new RationalNumber(-1,1);
        RationalNumber number;

        for (int i = 0; i < order; i++){
            if (!matrix[i][i].equals(one)){
                if(matrix[i][i].num ==0){

                    // Interchange rows
                    for (int j = i+1; j < order; j++){
                        if (matrix[j][i].num != 0){
                            interchangeRows(matrix, i, j);
                            interchangeRows(identityMatrix, i, j);
                            stringArrayList.add("R" + (i+1) + "  <->  " + "R" + (j+1));
                            stringArrayList.add("Matrix");
                            originalMatrixArrayList.add(returnMatrix(matrix));
                            identityMatrixArrayList.add(returnMatrix(identityMatrix));
                            break;
                        }
                    }
                }

                // if matrix[i][i] != 1 then make matrix[i][i] == 1 by multiplying ith row
                if (matrix[i][i].num != 0 & !matrix[i][i].equals(one)) {
                    number = matrix[i][i].reciprocal();
                    multiplyRow(matrix, i, number);
                    multiplyRow(identityMatrix, i, number);
                    stringArrayList.add("R" + (i + 1) + "  ->  (" + number.toString() + ") * R" + (i + 1));
                    stringArrayList.add("Matrix");
                    originalMatrixArrayList.add(returnMatrix(matrix));
                    identityMatrixArrayList.add(returnMatrix(identityMatrix));
                }
            }

            // make all elements of ith column except aii = 0 by using row transformation
            for (int j = 0; j < order; j++){
                if (i != j && matrix[j][i].num != 0){
                    number = matrix[j][i].multiply(minusOne);
                    elementaryRowOperation(matrix, j, number, i);
                    elementaryRowOperation(identityMatrix, j, number, i);
                    stringArrayList.add("R" + (j + 1) + "  ->  R" + (j+1) + "  +  (" + number.toString() + ") * R" + (i+1));
                    stringArrayList.add("Matrix");
                    originalMatrixArrayList.add(returnMatrix(matrix));
                    identityMatrixArrayList.add(returnMatrix(identityMatrix));
                }
            }
        }
    }

    public void calculateInverse(View view) {
        int[][] intMatrix = new int[order][order];
        RationalNumber[][] matrix = new RationalNumber[order][order];
        for (int i = 0; i < order; i++){
            for (int j = 0; j <order; j++){
                EditText editText =  findViewById(textViewsId[i][j]);
                intMatrix[i][j] = Integer.parseInt(editText.getText().toString());
                RationalNumber number = new RationalNumber(intMatrix[i][j]);
                matrix[i][j] = number;
            }
        }

        if (determinant(intMatrix) == 0){
            Toast.makeText(getApplicationContext(), "Given matrix is a non-invertible matrix", Toast.LENGTH_LONG).show();
        }
        else {


            inverse(matrix);

            Intent intent = new Intent(getApplicationContext(), Inverse.class);
            intent.putExtra("stringArrayList", stringArrayList);
            intent.putExtra("originalMatrixArrayList", originalMatrixArrayList);
            intent.putExtra("identityMatrixArrayList", identityMatrixArrayList);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_matrix_for_inverse);

        Intent intent = getIntent();
        order = intent.getIntExtra("order", 0);
        int gridLayoutSide;
        int textBoxSide;
        int fontSize;
        @SuppressLint("Recycle") TypedArray dimensions = getResources().obtainTypedArray(R.array.dimensions);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        if(order == 1){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(order == 2){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(order == 3){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_85);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 85, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_19);
        }
        else if(order == 4){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_64);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 64, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_13);
        }
        else if(order == 5){
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_51);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 51, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_10);
        }
        else{
            textBoxSide = (int) getResources().getDimension(R.dimen.dp_43);
            gridLayoutSide = (int) getResources().getDimension(dimensions.getResourceId(order * 43, R.dimen.dp_1000));
            fontSize = (int) getResources().getDimension(R.dimen.sp_7);
        }

        textViewsId = new int[order][order];
        ViewGroup.LayoutParams params = gridLayout.getLayoutParams();
        params.width = gridLayoutSide;
        params.height = gridLayoutSide;
        gridLayout.setLayoutParams(params);
        GridLayout.LayoutParams param;


        for(int i = 0; i < order; i++){
            for(int j = 0; j < order; j++){
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