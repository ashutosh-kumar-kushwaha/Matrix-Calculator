package ashutosh.matrixcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void rankActivity(View view){
        Intent intent = new Intent(getApplicationContext(), EnterOrderOfMatrixForRank.class);
        startActivity(intent);
    }

    public void inverseActivity(View view){
        Intent intent = new Intent(getApplicationContext(), EnterOrderOfMatrixForInverse.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}