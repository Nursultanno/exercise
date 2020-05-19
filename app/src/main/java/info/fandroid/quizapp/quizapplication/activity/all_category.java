package info.fandroid.quizapp.quizapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.adapters.CategoryAdapter;
import info.fandroid.quizapp.quizapplication.utilities.ActivityUtilities;

public class all_category extends AppCompatActivity  {
    private Context mContext;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         mActivity = all_category.this;
         mContext = mActivity.getApplicationContext();
        ConstraintLayout buttom3 = (ConstraintLayout) findViewById(R.id.bnt1);
        buttom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(all_category.this, predmet.class);

                intent.putExtra("os1", "1");
                startActivity(intent);
            }

            ;
        });
        ConstraintLayout buttom2 = (ConstraintLayout) findViewById(R.id.bnt2);
        buttom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(all_category.this, predmet.class);

                intent.putExtra("os1", "2");
                startActivity(intent);

            }

            ;
        });

    }
    @Override
    public void onBackPressed() {
        ActivityUtilities.getInstance().invokeNewActivity(mActivity, predmet.class, true);
    }
}
