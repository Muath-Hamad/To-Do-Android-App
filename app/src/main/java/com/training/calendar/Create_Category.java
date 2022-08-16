package com.training.calendar;

import static com.training.calendar.R.id.catgColorBlue;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Create_Category extends AppCompatActivity {

    Button ColorPicker , saveButton , IconPicker;
    TextView preview; // this will help show the user what color is currently chosen
    EditText categoryTitle; // this will have the name of category
    int ChosenColor;
    AppDatabase db;
    CategoryData data ;
    int IDtoUpdate;
    String oldCat;
    String sText;
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
            }else{
                String oldCat = getIntent().getStringExtra("EXTRA_CATEGORY_TITLE");
                sText = categoryTitle.getText().toString();
                db.userDao().updateCat(oldCat, sText);
                SaveUpdateButton();}

        }else{
            SaveNewButton();
        }
    }

    private void SaveUpdateButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sText = categoryTitle.getText().toString();
                    db.categoryDao().update(IDtoUpdate, sText, ChosenColor);
                    oldCat = getIntent().getStringExtra("EXTRA_CATEGORY_TITLE");
                    db.userDao().updateCat(oldCat,sText);
                    Intent i2 = new Intent(Create_Category.this, MainActivity.class);
                    startActivity(i2);
                    finish();

            }
        });
    }

    private void initDB() {
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        data = new CategoryData();
    }
    private void initViews() {
        ColorPicker = findViewById(R.id.colorsBTN);
        IconPicker = findViewById(R.id.iconBTN);
        preview = findViewById(R.id.ColorPreview);
        categoryTitle = findViewById(R.id.TitleEdit);
        saveButton = findViewById(R.id.SaveCategoryBTN);
        ChosenColor = ContextCompat.getColor(this , com.google.android.material.R.color.design_default_color_background);
        ColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Create_Category.this);
                dialog.setContentView(R.layout.category_colors);
                int width = WindowManager.LayoutParams.WRAP_CONTENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                ImageView dumbbell = findViewById(R.id.Dumbbell);
                ImageView home = findViewById(R.id.Home);
                ImageView homeAddress = findViewById(R.id.Home_Address);
                ImageView homeOffice = findViewById(R.id.Home_Office);
                ImageView work = findViewById(R.id.Work);
                ImageView pcOnDesk = findViewById(R.id.pc_on_desk);
                ImageView briefcase = findViewById(R.id.Briefcase);
                ImageView people = findViewById(R.id.People);


            }
        });
        IconPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Create_Category.this);
                dialog.setContentView(R.layout.category_icons);
                int width = WindowManager.LayoutParams.WRAP_CONTENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                ImageView blue = findViewById(catgColorBlue);
                ImageView black = findViewById(R.id.catgColorBlack);
                ImageView gray = findViewById(R.id.catgColorGray);
                ImageView green = findViewById(R.id.catgColorGreen);
                ImageView red = findViewById(R.id.catgColorRed);
                ImageView purple = findViewById(R.id.catgColorPurple);
                ImageView teal = findViewById(R.id.catgColorTeal);
                ImageView maroon = findViewById(R.id.catgColorMaroon);



            }
        });
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