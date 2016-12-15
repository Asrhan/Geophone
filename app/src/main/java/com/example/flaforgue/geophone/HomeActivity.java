package com.example.flaforgue.geophone;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends ActivityWithMenu {

    private Button findBtn;
    private EditText numberInput;

    private boolean binded;
    private MessagesManager messagesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // background service listening to messages of others Geophone applications
        Intent mIntent = new Intent(this, MessagesManager.class);
        bindService(mIntent, messagesManagerConnection, BIND_AUTO_CREATE);

        this.numberInput = (EditText) findViewById(R.id.numberInput);
        this.findBtn = (Button) findViewById(R.id.findBtn);
        this.findBtn.setOnClickListener(new FindBtnClickListenerManager(this));
    }

    ServiceConnection messagesManagerConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            binded = false;
            messagesManager = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            binded = true;
            MessagesManager.LocalBinder mLocalBinder = (MessagesManager.LocalBinder)service;
            messagesManager = mLocalBinder.getMessagesManagerInstance();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(binded) {
            unbindService(messagesManagerConnection);
            binded = false;
        }
    };

    public String getNumberInputValue() {
        return this.numberInput.getText().toString();
    }

    public void localize(String destination) {
        this.messagesManager.sendLocationRequest(destination);
        this.numberInput.setText("");

        Intent intentSettingsActivity = new Intent(this, RadarActivity.class);
        this.startActivity(intentSettingsActivity);
        overridePendingTransition(R.anim.slide_from_down, R.anim.slide_to_up);
        this.finish();
    }
}
