package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    EditText name ;
    Button save , test1;
    TextView username;
    SharedPreferences sp ;
    String usrInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        name = findViewById(R.id.name_edit);
        save = findViewById(R.id.saveBTN);
        username = findViewById(R.id.UsernameWelcome);
        test1 = findViewById(R.id.tempBTN);
        sp = getSharedPreferences("UserSettings" , Context.MODE_PRIVATE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usrInput = name.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("UserName",usrInput);
                editor.commit();
            }
        });
//        String nametest;
//        sp = getApplicationContext().getSharedPreferences("UserSettings",Context.MODE_PRIVATE);
//        nametest = sp.getString("UserName","");
//        test1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                username.setText(nametest);
//            }
//        });

    }


}