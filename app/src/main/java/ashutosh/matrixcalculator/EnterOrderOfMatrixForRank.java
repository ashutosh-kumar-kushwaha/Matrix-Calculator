package ashutosh.matrixcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterOrderOfMatrixForRank extends Activity {

    public void next(View view){
        EditText editTextRow = findViewById(R.id.editTextRow);
        int rows = Integer.parseInt(editTextRow.getText().toString());
        EditText editTextColumn = findViewById(R.id.editTextColumn);
        int columns = Integer.parseInt(editTextColumn.getText().toString());
        Intent intent;
        intent = new Intent(getApplicationContext(), EnterMatrixForRank.class);
        intent.putExtra("order", new int[] {rows, columns});
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_order_of_matrix_for_rank);
    }
}