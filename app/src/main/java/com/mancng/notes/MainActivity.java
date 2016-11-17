package com.mancng.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<String> notesContent = new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.mancng.notes", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if(set == null) {

            notesContent.add("new noteContent");

        } else {

            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), NotesActivity.class);
                i.putExtra("noteId", position);
                startActivity(i);
            }
        });

        //Delete Note
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        notes.remove(position);
                                        arrayAdapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.mancng.notes", Context.MODE_PRIVATE);

                                        HashSet<String> set = new HashSet<String>(MainActivity.notes);

                                        sharedPreferences.edit().putStringSet("notes", set).apply();

                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

//        menu.findItem(R.id.addNotes).setVisible(true);
        menu.findItem(R.id.saveNotes).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (item.getItemId() == R.id.add_note) ;

        Intent i = new Intent(getApplicationContext(), NotesActivity.class);
        startActivity(i);

//        if (id == R.id.addNotes) {
//
//            Intent i = new Intent(getApplicationContext(), NotesActivity.class);
//            startActivity(i);
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
