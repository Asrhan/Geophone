package com.example.flaforgue.geophone;

import android.app.Activity;
import android.view.View;

/**
 * Created by flaforgue on 21/11/2016.
 */

public class MenuBtnClickListenerManager implements View.OnClickListener {

    private ActivityWithMenu parentActivity;

    public MenuBtnClickListenerManager(ActivityWithMenu parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onClick(View v) {
        this.parentActivity.toggleMenu();
    }
}
