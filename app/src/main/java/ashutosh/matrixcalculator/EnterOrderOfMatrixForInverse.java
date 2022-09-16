package ashutosh.matrixcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterOrderOfMatrixForInverse extends Activity {

    public void next(View view) {
        EditText editTextOrder = findViewById(R.id.editTextOrder);
        int order = Integer.parseInt(editTextOrder.getText().toString());
        Intent intent;
        intent = new Intent(getApplicationContext(), EnterMatrixForInverse.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_order_of_matrix_for_inverse);
    }
}