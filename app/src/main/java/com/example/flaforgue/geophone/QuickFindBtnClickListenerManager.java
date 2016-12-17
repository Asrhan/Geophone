package com.example.flaforgue.geophone;

import android.view.View;
import android.widget.Button;

/**
 * Created by Baptite_Portable on 17/12/2016.
 */

public class QuickFindBtnClickListenerManager implements View.OnClickListener {
    private HomeActivity parentActivity;
    private String number;

    public QuickFindBtnClickListenerManager(HomeActivity parentActivity, String num) {
        this.parentActivity = parentActivity;
        this.number = num;
    }

    @Override
    public void onClick(View v) {
        this.parentActivity.localize(number);
    }
}
