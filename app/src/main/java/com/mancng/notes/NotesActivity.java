package com.mancng.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {


    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText txtTitle = (EditText) findViewById(R.id.txtNoteTitle);
        final EditText txtContent = (EditText) findViewById(R.id.txtContent);

        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if (noteId != -1) {

            txtTitle.setText(MainActivity.notes.get(noteId));
        } else {

            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() -1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.mancng.notes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<String>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        menu.findItem(R.id.addNotes).setVisible(false);
        menu.findItem(R.id.saveNotes).setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveNotes) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
