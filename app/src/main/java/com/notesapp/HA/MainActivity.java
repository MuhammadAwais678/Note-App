package com.notesapp.HA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fbtnadd;
    Button btncreate;
    RecyclerView recView;
    LinearLayout llNotes;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intVar();

        showNotes();

        fbtnadd.setOnClickListener(v -> {

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_note);

            EditText edtTitle,edtContent;
            Button btnadd;

            edtTitle = dialog.findViewById(R.id.edtTitle);
            edtContent = dialog.findViewById(R.id.edtContent);
            btnadd = dialog.findViewById(R.id.btnadd);

            btnadd.setOnClickListener(v1 -> {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if (!content.equals("")){

                    databaseHelper.noteDao().addNotes(new Notes(title,content));
                    showNotes();

                    dialog.dismiss();

                }else {

                    Toast.makeText(MainActivity.this, "Please Enter Somthing!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();

        });

        btncreate.setOnClickListener(v -> fbtnadd.performClick());

    }

    public void showNotes() {

       ArrayList<Notes> arrayList = (ArrayList<Notes>) databaseHelper.noteDao().getNotes();

       if (arrayList.size()>0){

           llNotes.setVisibility(View.GONE);
           recView.setVisibility(View.VISIBLE);

           recView.setAdapter(new RecyclerViewAdapter(this,arrayList,databaseHelper));



       }
       else {
           llNotes.setVisibility(View.VISIBLE);
           recView.setVisibility(View.GONE);
       }

    }

    private void intVar() {

        fbtnadd = findViewById(R.id.fbtnadd);
        btncreate = findViewById(R.id.btncreate);
        llNotes = findViewById(R.id.llNotes);
        recView = findViewById(R.id.recView);

        recView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = DatabaseHelper.getInstance(this);

    }
}