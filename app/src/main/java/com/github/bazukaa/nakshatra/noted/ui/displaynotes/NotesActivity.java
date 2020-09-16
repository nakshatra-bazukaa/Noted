package com.github.bazukaa.nakshatra.noted.ui.displaynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import com.github.bazukaa.nakshatra.noted.adapter.NoteAdapter;
import com.github.bazukaa.nakshatra.noted.ui.displaytrashnotes.TrashNotesActivity;
import com.github.bazukaa.nakshatra.noted.ui.makeeditnote.MakeEditNoteActivity;
import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.util.Constants;
import com.github.bazukaa.nakshatra.noted.util.PreferenceManager;
import com.github.bazukaa.nakshatra.noted.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;
    private PreferenceManager preferenceManager;
    private boolean isGrid;

    private RecyclerView.LayoutManager layoutManager;

    private MenuItem grid;
    private MenuItem list;
    private MenuItem trash;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.act_main_rv_notes)
    RecyclerView noteRecyclerView;

    @BindView(R.id.act_main_fab_add)
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        preferenceManager = new PreferenceManager(this);

        NoteAdapter adapter = new NoteAdapter();
        noteRecyclerView.setAdapter(adapter);

        // Setting grid/list mode
        isGrid = preferenceManager.getBoolean(Constants.KEY_GRID_STATE);
        if(isGrid) setGrid();
        else setList();

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, notes -> adapter.setNotes(notes));

        //To delete a note
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { return false; }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = adapter.getNotePosition(viewHolder.getAdapterPosition());
                TrashNote trashNote = new TrashNote(note.getTitle(), note.getNote(), note.getTimeStamp());
                trashNote.setColor(note.getColor());
                noteViewModel.insert(trashNote);
                noteViewModel.delete(note);
                Toast.makeText(NotesActivity.this, "Note moved to trash", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        //To Edit a note
        adapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(NotesActivity.this, MakeEditNoteActivity.class);
            intent.putExtra(MakeEditNoteActivity.EXTRA_ID, note.getId());
            intent.putExtra(MakeEditNoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(MakeEditNoteActivity.EXTRA_NOTE, note.getNote());
            intent.putExtra(MakeEditNoteActivity.EXTRA_COLOR, note.getColor());

            //Formatting currentTimeMillis in desired form before sending to MakeEditNoteActivity
            long currentTimeMillis = note.getTimeStamp();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa MMM dd, yyyy");
            Date resultdate = new Date(currentTimeMillis);
            String timeStamp = "Last changed";
            timeStamp = timeStamp+" "+String.valueOf(sdf.format(resultdate));
            intent.putExtra(MakeEditNoteActivity.EXTRA_TIMESTAMP, timeStamp);

            startActivityForResult(intent, EDIT_NOTE_REQUEST);
        });
    }
    //To create a new note
    @OnClick(R.id.act_main_fab_add)
    public void onFabClicked() {
        Intent intent = new Intent(NotesActivity.this, MakeEditNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(MakeEditNoteActivity.EXTRA_TITLE);
            String note = data.getStringExtra(MakeEditNoteActivity.EXTRA_NOTE);
            long timeStamp = data.getLongExtra(MakeEditNoteActivity.EXTRA_TIMESTAMP, 10000);
            String color = data.getStringExtra(MakeEditNoteActivity.EXTRA_COLOR);

            Note notedNote = new Note(title, note, timeStamp);
            notedNote.setColor(color);
            noteViewModel.insert(notedNote);

            Toast.makeText(this, "Note Saved successfully", Toast.LENGTH_SHORT).show();
        } else if (requestCode == ADD_NOTE_REQUEST) {
            Toast.makeText(this, "Empty Note not saved", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MakeEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(MakeEditNoteActivity.EXTRA_TITLE);
            String note = data.getStringExtra(MakeEditNoteActivity.EXTRA_NOTE);
            long timeStamp = data.getLongExtra(MakeEditNoteActivity.EXTRA_TIMESTAMP, 10000);
            String color = data.getStringExtra(MakeEditNoteActivity.EXTRA_COLOR);

            Note notedNote = new Note(title, note, timeStamp);
            notedNote.setColor(color);
            notedNote.setId(id);
            noteViewModel.update(notedNote);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST) {
            Toast.makeText(this, "Note Unchanged", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_main, menu);

        grid = menu.findItem(R.id.set_layout_grid);
        list = menu.findItem(R.id.set_layout_list);
        trash = menu.findItem(R.id.trash);

        // To initially set list/grid view
        if(isGrid){
            grid.setVisible(false);
            list.setVisible(true);
        }else{
            grid.setVisible(true);
            list.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_layout_grid:
                setGrid();
                preferenceManager.putBoolean(Constants.KEY_GRID_STATE, Constants.GRID_MODE);
                grid.setVisible(false);
                list.setVisible(true);
                return true;
            case R.id.set_layout_list:
                setList();
                preferenceManager.putBoolean(Constants.KEY_GRID_STATE, Constants.LIST_MODE);
                grid.setVisible(true);
                list.setVisible(false);
                return true;
            case R.id.trash:
                Intent intent = new Intent(NotesActivity.this, TrashNotesActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // To set to grid mode
    private void setGrid(){
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        noteRecyclerView.setLayoutManager(layoutManager);
    }
    // To set to list mode
    private void setList(){
        layoutManager = new LinearLayoutManager(NotesActivity.this);
        noteRecyclerView.setLayoutManager(layoutManager);
    }
}
