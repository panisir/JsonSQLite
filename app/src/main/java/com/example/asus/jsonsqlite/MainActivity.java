package com.example.asus.jsonsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnDisplay ,btnSave ,btnDisplayDB;
    EditText etCityName; // will get the city which wanted to know coordinates and will be used as part of url as urlCity one-down row
    String urlCity;
    static TextView tvResult;
    JsonGetter jsongetter;
    DatabaseHelper mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB=new DatabaseHelper(this); // created the database

        btnSave= (Button) findViewById(R.id.btnSave);
        btnDisplayDB= (Button) findViewById(R.id.btndisplayDB);
        btnDisplay= (Button) findViewById(R.id.btnDisplay);

        etCityName= (EditText) findViewById(R.id.etCityName);
        tvResult= (TextView) findViewById(R.id.tvResult);

        btnDisplayDB.setOnClickListener(clickDisplayDB);
        btnSave.setOnClickListener(clickSave);
        btnDisplay.setOnClickListener(clickDisplay);
    }

    public View.OnClickListener clickDisplay=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            urlCity= etCityName.getText().toString();
            jsongetter=new JsonGetter();
            jsongetter.execute("http://maps.google.com/maps/api/geocode/json?address=%22%20+%20"+urlCity+"%20+%20%22&sensor=false"); // will be sent as params[0] to the jsonGetter
                                                                                                                                    // and used while getting json data
        }
    };

    public View.OnClickListener clickSave=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isInserted=mDB.insertData(jsongetter.longName, // the parametres will be sent to insertData method in the DatabaseHelper class
                                                                    // and if Data is inserted the boolean value returns true and Toast message will be displayed On the screen
                    Float.toString(jsongetter.lat),
                    Float.toString(jsongetter.lng));
            if(isInserted==true)
                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
        }
    };

    public View.OnClickListener clickDisplayDB=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor res=mDB.displayAllData();
            if(res.getCount()==0){
                //show message
                showMessage("Error", "Nothing Found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(res.moveToNext()){   // res is a indicator derived Cursor class enable us to move on the database table
                buffer.append("Id:"+res.getString(0)+"\n");
                buffer.append("City:"+res.getString(1)+"\n");
                buffer.append("Lat:"+res.getString(2)+"\n");
                buffer.append("Lng:"+ res.getString(3)+"\n\n");
            }

            showMessage("Data", buffer.toString()); // saved datas will be displayed using showMessage method defined below
        }
    };


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
