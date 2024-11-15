package com.example.botiquin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewItemsActivity.class);
            startActivity(intent);
        });
    }
}
