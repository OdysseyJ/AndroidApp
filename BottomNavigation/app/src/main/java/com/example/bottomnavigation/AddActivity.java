package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    String newName;
    String newPhone;
    String newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void onBackButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), "돌아가기 버튼이 눌렸어요.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onAddButtonClicked(View v) {
        EditText editname = (EditText) findViewById(R.id.editName) ;
        newName = editname.getText().toString();

        EditText editphone = (EditText) findViewById(R.id.editPhone) ;
        newPhone = editphone.getText().toString() ;

        EditText editemail = (EditText) findViewById(R.id.editEmail) ;
        newEmail = editemail.getText().toString() ;

        PageOne.AddData(newName, newPhone);

        finish();
    }

}
