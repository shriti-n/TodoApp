package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    Button btnEdit;
    EditText txtEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setTitle(R.string.edit_item);

        btnEdit = findViewById(R.id.btnEdit);
        txtEdit = findViewById(R.id.txtEdit);

        String item_name = getIntent().getExtras().getString("item_name");
        final int item_position = getIntent().getExtras().getInt("item_position");

        txtEdit.setText(item_name);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);

                intent.putExtra("item_name", txtEdit.getText().toString());
                intent.putExtra("item_position", item_position);
                setResult(200, intent);

                finish();
            }
        });

    }
}
