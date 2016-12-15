package com.example.flaforgue.geophone;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by flaforgue on 21/11/2016.
 */

public class FindBtnClickListenerManager implements View.OnClickListener {

    private HomeActivity parentActivity;

    public FindBtnClickListenerManager(HomeActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onClick(View v) {
        String destination = this.parentActivity.getNumberInputValue();
        if (null != destination && ! destination.isEmpty()) {
            this.parentActivity.localize(destination);
        }
    }
}
