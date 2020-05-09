package info.fandroid.quizapp.quizapplication.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import info.fandroid.quizapp.quizapplication.R;

public class all_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        ConstraintLayout buttom3 = (ConstraintLayout) findViewById(R.id.bnt1);
        buttom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(all_category.this, predmet.class);
                startActivity(intent);
            }

            ;
        });
    }
}
