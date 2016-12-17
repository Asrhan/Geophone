package com.example.flaforgue.geophone;

import android.content.Intent;
import android.view.View;

/**
 * Created by Baptite_Portable on 16/12/2016.
 */

public class OptionBtnClickListenerManager implements View.OnClickListener {

    private ActivityWithMenu parentActivity;

    public OptionBtnClickListenerManager(ActivityWithMenu parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onClick(View v) {
        Intent settingsIntent = new Intent(this.parentActivity, SettingsActivity.class);
        this.parentActivity.startActivity(settingsIntent);
    }
}
