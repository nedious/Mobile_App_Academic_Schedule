package com.example.termv30.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.termv30.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.go_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityTermHome.class);
                intent.putExtra("test", "Information sent");
                startActivity(intent);
            }
        });
    }
//
//    public void goToTermList(View view) {
//        Intent intent = new Intent(MainActivity.this, TermListActivity.class);
//        startActivity(intent);
//    }
}

