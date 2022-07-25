package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Create_Category extends AppCompatActivity {

    Button ColorPicker;
    TextView preview;
    EditText categoryTitle;
    int ChoosenColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        ColorPicker = findViewById(R.id.CategoryColorPicker);
        preview = findViewById(R.id.ColorPreview);
        categoryTitle = findViewById(R.id.TitleEdit);

        ChoosenColor = ContextCompat.getColor(this , com.google.android.material.R.color.design_default_color_background);

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
        AmbilWarnaDialog ambilWarnaDialog =new AmbilWarnaDialog(this, ChoosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                ChoosenColor = color; // this will save the color chosen on the dialog
                preview.setBackgroundColor(color); // set the preview to chosen color
            }
        });
        ambilWarnaDialog.show();
    }

    public void SaveButton(View view) { // write data into DB and then return to main activity



        Intent i2 = new Intent(Create_Category.this ,MainActivity.class);
        startActivity(i2);
        finish();

    }
}