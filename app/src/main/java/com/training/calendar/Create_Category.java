package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Create_Category extends AppCompatActivity {

    Button ColorPicker , saveButton;
    TextView preview; // this will help show the user what color is currently chosen
    EditText categoryTitle; // this will have the name of category
    int ChosenColor;
    AppDatabase db;
    CategoryData data ;
    int IDtoUpdate;
    // this at first will have default color until changed by user
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        initViews();
        initDB();
        loadAndExcute();


    }

    private void loadAndExcute() { // this method will determine if user is updating or creating new category

        if (getIntent().getBooleanExtra("EXTRA_CATEGORY_UPDATE",false)){

            preview.setBackgroundColor(getIntent().getIntExtra("EXTRA_CATEGORY_COLOR", ChosenColor));
            categoryTitle.setText(getIntent().getStringExtra("EXTRA_CATEGORY_TITLE"));
            IDtoUpdate = getIntent().getIntExtra("EXTRA_CATEGORY_ID",-1); // This default value should never be used ,if used produce error

            if (IDtoUpdate == -1){
                throw new java.lang.Error("ERROR!!: Default ID received -1 ");
            }else{ SaveUpdateButton();}

        }else{
            SaveNewButton();
        }
    }

    private void SaveUpdateButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = categoryTitle.getText().toString().trim();
                if (!sText.equals("")) {
                    db.categoryDao().update(IDtoUpdate, sText, ChosenColor);

                    Intent i2 = new Intent(Create_Category.this, MainActivity.class);
                    startActivity(i2);
                    finish();
                }
            }
        });
    }

    private void initDB() {
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        data = new CategoryData();
    }
    private void initViews() {
        ColorPicker = findViewById(R.id.CategoryColorPicker);
        preview = findViewById(R.id.ColorPreview);
        categoryTitle = findViewById(R.id.TitleEdit);
        saveButton = findViewById(R.id.SaveCategoryBTN);
        ChosenColor = ContextCompat.getColor(this , com.google.android.material.R.color.design_default_color_background);
        ColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();

            }
        });
        categoryTitle.setOnClickListener(new View.OnClickListener() { // clear field on first use
            @Override
            public void onClick(View view) {
                if (getString(R.string.eventTitleText).equals(categoryTitle.getText().toString())){
                    categoryTitle.setText("");
                }
            }
        });
    }

    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog =new AmbilWarnaDialog(this, ChosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                ChosenColor = color; // this will save the color chosen on the dialog
                preview.setBackgroundColor(color); // set the preview to chosen color
            }
        });
        ambilWarnaDialog.show();
    }

    public void SaveNewButton() { // write data into DB and then return to main activity

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = categoryTitle.getText().toString().trim();

                if (!sText.equals("")){


                    data.setTitle(sText);
                    data.setColor(ChosenColor);
                    db.categoryDao().insert(data);
                }
                Intent i2 = new Intent(Create_Category.this ,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

    }
}