package com.example.flaforgue.geophone;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RadarActivity extends AppCompatActivity {

    private FloatingActionButton homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        this.homeBtn = (FloatingActionButton) this.findViewById(R.id.homeBtn);

        this.homeBtn.setOnClickListener(new HomeBtnClickListenerManager(this));
    }
}
