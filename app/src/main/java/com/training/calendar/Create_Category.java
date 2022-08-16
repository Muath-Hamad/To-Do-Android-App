package com.training.calendar;

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

public class Create_Category extends AppCompatActivity {

    Button ColorPicker , saveButton , IconPicker;
    TextView preview; // this will help show the user what color is currently chosen
    EditText categoryTitle; // this will have the name of category
    int ChosenColor;
    AppDatabase db;
    CategoryData data ;
    ImageView iconChosen , colorChosen;
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
        categoryTitle = findViewById(R.id.TitleEdit);
        saveButton = findViewById(R.id.SaveCategoryBTN);
        colorChosen = findViewById(R.id.catgColorChosen);
        iconChosen = findViewById(R.id.catgIconChosen);
        iconChosen.setColorFilter(getResources().getColor(R.color.white));
        IconPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconsDialog();



            }
        });
        ColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               colorsDialog();
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
    public void colorsDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.category_colors);
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        ImageView blue = dialog.findViewById(R.id.catgColorBlue);
        ImageView black = dialog.findViewById(R.id.catgColorBlack);
                ImageView gray = dialog.findViewById(R.id.catgColorGray);
                ImageView green = dialog.findViewById(R.id.catgColorGreen);
                ImageView red = dialog.findViewById(R.id.catgColorRed);
                ImageView purple = dialog.findViewById(R.id.catgColorPurple);
                ImageView teal = dialog.findViewById(R.id.catgColorTeal);
                ImageView maroon = dialog.findViewById(R.id.catgColorMaroon);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.blue));
                dialog.dismiss();
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.black));
                dialog.dismiss();
            }
        });
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.gray));
                dialog.dismiss();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.green));
                dialog.dismiss();
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.red));
                dialog.dismiss();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.purple_500));
                dialog.dismiss();
            }
        });
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.teal_200));
                dialog.dismiss();
            }
        });
        maroon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChosen.setColorFilter(getResources().getColor(R.color.maroon));
                dialog.dismiss();
            }
        });

    }
    public void iconsDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.category_icons);
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        ImageView dumbbell = dialog.findViewById(R.id.Dumbbell);
        ImageView home = dialog.findViewById(R.id.Home);
        ImageView homeAddress = dialog.findViewById(R.id.Home_Address);
        ImageView homeOffice = dialog.findViewById(R.id.Home_Office);
        ImageView work = dialog.findViewById(R.id.Work);
        ImageView pcOnDesk = dialog.findViewById(R.id.pc_on_desk);
        ImageView briefcase = dialog.findViewById(R.id.Briefcase);
        ImageView people = dialog.findViewById(R.id.People);
        dumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_dumbbell);

                dialog.dismiss();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_home);

                dialog.dismiss();
            }
        });
        homeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_home_address);

                dialog.dismiss();
            }
        });
        homeOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_home_office);

                dialog.dismiss();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_work);

                dialog.dismiss();
            }
        });
        pcOnDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_pc_on_desk);

                dialog.dismiss();
            }
        });
        briefcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_briefcase);

                dialog.dismiss();
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconChosen.setColorFilter(null);
                iconChosen.setImageResource(R.drawable.ic_people);

                dialog.dismiss();
            }
        });
    }
}