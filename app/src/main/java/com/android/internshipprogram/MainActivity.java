package com.android.internshipprogram;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2, ed4;
    TextView ed3;
    Button bt1;
    ImageButton ib1;
    Spinner sp;
    DatePickerDialog dp;
    String str=""; int strOldlen=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  // showing the back button in action bar

        getSupportActionBar().hide();

        ed1= (EditText)findViewById(R.id.editText);
        ed2= (EditText)findViewById(R.id.editText2);
        ed3= (TextView) findViewById(R.id.editText3);
        ed4= (EditText)findViewById(R.id.editText4);
        bt1= (Button)findViewById(R.id.button);

        ib1= (ImageButton)findViewById(R.id.imageButton3);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });


        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                str = ed1.getText().toString();
                int strLen = str.length();

                if(strOldlen<strLen) {
                    if (strLen > 0) {
                        if (strLen == 4 || strLen == 9) {
                            str=str+" ";
                            ed1.setText(str);
                            ed1.setSelection(ed1.getText().length());
                        }
                        else{
                            if(strLen==5){
                                if(!str.contains(" ")){
                                    String tempStr=str.substring(0,strLen-1);
                                    tempStr +=" "+str.substring(strLen-1,strLen);
                                    ed1.setText(tempStr);
                                    ed1.setSelection(ed1.getText().length());
                                }
                            }
                            if(strLen==10){
                                if(str.lastIndexOf(" ")!=9){
                                    String tempStr=str.substring(0,strLen-1);
                                    tempStr +=" "+str.substring(strLen-1,strLen);
                                    ed1.setText(tempStr);
                                    ed1.setSelection(ed1.getText().length());
                                }
                            }
                            strOldlen = strLen;
                        }
                    }
                    else    return;
                }
                else   strOldlen = strLen;
            }
        });


        setDateTimeField();
        ed3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dp.show();
                return false;
            }
        });


        String s[]= {"Select your Gender","Male", "Female", "Others"};
        sp= (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> aa= new ArrayAdapter(this, android.R.layout.simple_spinner_item, s){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)   return false;
                else    return true;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#7F8C8D"));
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0)   tv.setTextColor(Color.GRAY);
                else    tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setPrompt("");
        sp.setAdapter(aa);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0;
                if(ed1.getText().toString().isEmpty() || ed1.length()!=14){ ed1.setError("Enter Valid Aadhaar Number of 12 digits!"); flag=1;}
                if(ed2.getText().toString().isEmpty()){ ed2.setError("Enter Valid Name!"); flag=1;}
                if(ed3.getText().toString().isEmpty()){ ed3.setError("Enter Valid Data!"); flag=1;}
                if(ed4.getText().toString().isEmpty()  || !ed4.getText().toString().contains(".") || !ed4.getText().toString().contains("@")){
                    ed4.setError("Enter Valid Email ID!"); flag=1;}
                if(sp.getId()==0 || sp.equals("Select your Gender")){ ((TextView)sp.getSelectedView()).setError("Enter Valid Data!"); flag=1;}

                if(flag==0)
                    Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);
                ed3.setText(fdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dp.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
}