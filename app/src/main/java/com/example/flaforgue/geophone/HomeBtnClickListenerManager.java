package com.example.flaforgue.geophone;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;


public class HomeBtnClickListenerManager implements View.OnClickListener {

    private Activity parentActivity;

    public HomeBtnClickListenerManager(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onClick(View v) {
        Intent intentSettingsActivity = new Intent(this.parentActivity, HomeActivity.class);
        this.parentActivity.startActivity(intentSettingsActivity);
        this.parentActivity.overridePendingTransition(R.anim.slide_from_down, R.anim.slide_to_up);
        this.parentActivity.finish();
    }
}
