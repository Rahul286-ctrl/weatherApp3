package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
 EditText fullName, phone,address1,address2,pin;
TextView district,state,date;
Button check, register;
ImageButton imageButton;
DatePickerDialog.OnDateSetListener setListener;
RadioGroup radioGroup;
RadioButton selectGender;
    String pinCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone=findViewById(R.id.phone);
        fullName=findViewById(R.id.fname);
        address1=findViewById(R.id.Address1);
        address2=findViewById(R.id.Address2);
        register=findViewById(R.id.Register);
        check=findViewById(R.id.check);
        date=findViewById(R.id.date);
        pin=findViewById(R.id.pin);
        state=findViewById(R.id.State);
        district=findViewById(R.id.District);
        radioGroup=findViewById(R.id.radiogroup);
        imageButton = findViewById(R.id.imagebutton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");

            }
        });
        check.setEnabled(false);
        pin.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                if (s.toString().equals("") && s.toString().length()<6) {
                    check.setEnabled(false);
                } else if(s.toString().length()==6){
                    check.setEnabled(true);
                }else{
                    check.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromPinCode(pinCode);
            }
        });

    }

    private void getDataFromPinCode(String pinCode) {
        pinCode=pin.getText().toString();

        String url = "https://api.postalpincode.in/pincode/"+pinCode;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                 try {
                    for(int i=0;i<response.length();i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("PostOffice");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String District = jsonObject1.getString("District");
                        String State = jsonObject1.getString("State");
                        state.setText("State : "+State);
                        district.setText("District : "+District);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("pincodesError",error.toString());
                Toast.makeText(MainActivity.this, "Pin code is not valid !", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(arrayRequest);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date.setText("DOB*: "+currentDateString);
    }

    private Boolean validateName(){
        String val=fullName.getText().toString();
    if (val.isEmpty()) {
        fullName.setError("Field is mandatory");
        return false;
    }
    else{
        fullName.setError(null);
        return true;
    }
}
    private Boolean validatePhone(){
        String val=phone.getText().toString();
        if (val.isEmpty()) {
            phone.setError("Field is mandetory");
            return false;
        }
        else{
            phone.setError(null);
            return true;
        }
    }
    private Boolean validateAddress1(){
        String val=address1.getText().toString();
        if (val.isEmpty()) {
            address1.setError("Field is mandetory");
            return false;
        }
        else{
            address1.setError(null);
            return true;
        }
    }



    private Boolean validateAge(){
        String val=date.getText().toString();
        if (val.isEmpty()) {
            address2.setError("Field is mandetory");
            return false;
        }
        else{
            address2.setError(null);
            return true;
        }
    }
    private Boolean validateGender(){
        if (radioGroup.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this, "Please select Gender!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    private Boolean validatePin(){
        String val=pin.getText().toString();
        if (val.isEmpty()) {
            pin.setError("Field is mandetory");
            return false;
        }
        else{
            pin.setError(null);
            return true;
        }
    }

    private Boolean validateDistrict(){
        String val=district.getText().toString();
        if (val.isEmpty()) {
            district.setError("Field is mandetory");
            return false;
        }
        else{
            district.setError(null);
            return true;
        }
    }
    private Boolean validateState(){
        String val=state.getText().toString();
        if (val.isEmpty()) {
            state.setError("Field is mandetory");
            return false;
        }
        else{
            state.setError(null);
            return true;
        }
    }
    private void validate() {
        String Name=fullName.getText().toString();
        String Phone=phone.getText().toString();
        String Address1=address1.getText().toString();
        String Address2=address2.getText().toString();

        if(!validatePhone() | !validateName() | !validateGender() | !validateAge() | !validateAddress1() | !validatePin() | !validateState() | !validateDistrict()){
            Toast.makeText(this, "Field with * sign are mandatory to fill.", Toast.LENGTH_SHORT).show();
            return;

        }
        else {
            Toast.makeText(this, "Registration Successful.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            finish();
        }

    }
}