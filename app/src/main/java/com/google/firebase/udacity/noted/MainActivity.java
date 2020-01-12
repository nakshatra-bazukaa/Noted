package com.google.firebase.udacity.noted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.act_main_rv_notes)
    RecyclerView noteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Setting up the toolbar
        setSupportActionBar(toolbar);

        NoteAdapter adapter = new NoteAdapter();
        noteRecyclerView.setAdapter(adapter);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }
//    private void noteRecyclerView(){
//        NoteAdapter adapter = new NoteAdapter();
//        noteRecyclerView.setAdapter(adapter);
//        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
}
