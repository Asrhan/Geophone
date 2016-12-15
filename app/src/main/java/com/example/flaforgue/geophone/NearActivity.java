package com.example.flaforgue.geophone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NearActivity extends AppCompatActivity {

    private Button getItBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near);

        this.getItBtn = (Button) findViewById(R.id.getItBtn);
        this.getItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceComponentSwitch.turnOffFlash();
                DeviceComponentSwitch.stopVibrate();
                DeviceComponentSwitch.stopSound();
                //TODO fermer appli
            }
        });
    }
}
