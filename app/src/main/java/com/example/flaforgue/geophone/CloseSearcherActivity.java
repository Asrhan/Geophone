package com.example.flaforgue.geophone;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CloseSearcherActivity extends AppCompatActivity {

    private FloatingActionButton homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_searcher);

        this.homeBtn = (FloatingActionButton) this.findViewById(R.id.homeBtn);

        this.homeBtn.setOnClickListener(new HomeBtnClickListenerManager(this));
    }
}
