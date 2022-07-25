package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class WelcomePage extends AppCompatActivity {

    EditText name ;
    Button save ;
    TextView username;
    SharedPreferences sp ;
    String usrInput;
    String savedname;
    String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

    public WelcomePage() throws GeneralSecurityException, IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
       // getSupportActionBar().hide();
        name = findViewById(R.id.name_edit);
        save = findViewById(R.id.saveBTN);
        username = findViewById(R.id.UsernameWelcome);

//         sp = EncryptedSharedPreferences.create(
//                "encryptedUserSettings",
//                masterKeyAlias,
//                Context,
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        );

        sp = getSharedPreferences("UserSettings" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (!sp.getBoolean("UserNameAvailable",false)){ // To see if the user has a name in shared pref
                save.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                save.setClickable(true);
                name.setClickable(true);
            Intent intent1 = new Intent(WelcomePage.this ,Create_Event.class);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usrInput = name.getText().toString();

                    editor.putString("UserName",usrInput);
                    editor.putBoolean("UserNameAvailable",true);
                    editor.apply();
                   username.setText(usrInput);

                    startActivity(intent1);
                }
            });

        }else{
            savedname = sp.getString("UserName","");
            username.setText(savedname);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(WelcomePage.this , Create_Event.class);
                    startActivity(intent);
                    //finish();
                }
            } , 2000);

        }


        //editor.putBoolean("UserNameAvailable",false);


    }

}