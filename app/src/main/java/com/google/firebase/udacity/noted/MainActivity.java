package com.google.firebase.udacity.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.act_main_rv_notes)
    RecyclerView noteRecyclerView;

    @BindView(R.id.act_main_fab_add)
    FloatingActionButton addButton;

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

        //To delete a note
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Removed", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        //To Edit a note
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, MakeEditNoteActivity.class);
                intent.putExtra(MakeEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(MakeEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(MakeEditNoteActivity.EXTRA_NOTE, note.getNote());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }
    //To create a new note
    @OnClick(R.id.act_main_fab_add)
    public void onFabClicked() {
        Intent intent = new Intent(MainActivity.this, MakeEditNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(MakeEditNoteActivity.EXTRA_TITLE);
            String note = data.getStringExtra(MakeEditNoteActivity.EXTRA_NOTE);

            Note notedNote = new Note(title, note, 10000);
            noteViewModel.insert(notedNote);

            Toast.makeText(this, "Note Saved successfully", Toast.LENGTH_SHORT).show();
        }else if(requestCode == ADD_NOTE_REQUEST) {
            Toast.makeText(this, "Empty Note not saved", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MakeEditNoteActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(MakeEditNoteActivity.EXTRA_TITLE);
            String note = data.getStringExtra(MakeEditNoteActivity.EXTRA_NOTE);

            Note notedNote = new Note(title, note, 10000);
            notedNote.setId(id);
            noteViewModel.update(notedNote);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        }else if(requestCode == EDIT_NOTE_REQUEST){
            Toast.makeText(this, "Note Unchanged", Toast.LENGTH_SHORT).show();
        }
    }
}
