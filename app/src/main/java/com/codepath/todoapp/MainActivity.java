package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtItemInfo;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private List<String> items;
    private CustomRecycler itemsAdapter;
    private CustomRecycler.OnClickListener onClickListener;
    private CustomRecycler.OnLongClickListener onLongClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting references to views
        txtItemInfo = findViewById(R.id.txtNewItem);
        btnAdd = findViewById(R.id.btnAddItem);
        recyclerView = findViewById(R.id.rcyItems);

        //Setting initial set of items by retrieving them from the file
        getItems();

        //Setting Click and Long Click listeners
        setCustomOnClickListener();
        setCustomOnLongClickListener();

        // Creating items adapter and setting them to the list
        itemsAdapter = new CustomRecycler(items, onLongClickListener, onClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);

        //Adding click listener to add button
        setAddOnClickListener();

    }

    private void setAddOnClickListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemString = txtItemInfo.getText().toString();
                items.add(itemString);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                saveItems();
                txtItemInfo.setText("");
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setCustomOnLongClickListener() {
        onLongClickListener = new CustomRecycler.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                saveItems();
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setCustomOnClickListener() {
        onClickListener = new CustomRecycler.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("item_name", items.get(position));
                intent.putExtra("item_position", position);
                startActivityForResult(intent, 100);
            }
        };
    }

    private File getFile() {
        return new File(getFilesDir(), "todo-data.txt");
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();

            items = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && resultCode == 200) {
            final int item_position;
            if (data != null) {
                item_position = data.getExtras().getInt("item_position");
                String edited_value = data.getExtras().getString("item_name");
                items.set(item_position, edited_value);
                itemsAdapter.notifyItemChanged(item_position);
                saveItems();
                Toast.makeText(getApplicationContext(), "Item modified", Toast.LENGTH_SHORT).show();
            } else {
                // Write code here
            }
        }
    }
}
