package com.github.bazukaa.nakshatra.noted.ui.displaytrashnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.adapter.TrashNoteAdapter;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import com.github.bazukaa.nakshatra.noted.ui.makeeditnote.MakeEditNoteActivity;
import com.github.bazukaa.nakshatra.noted.viewmodel.TrashNoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrashNotesActivity extends AppCompatActivity {

    public static final int EDIT_TRASH_NOTE_REQUEST = 10;

    private TrashNoteViewModel trashNoteViewModel;
    private RecyclerView.LayoutManager layoutManager;


    @BindView(R.id.act_trash_note_toolbar)
    Toolbar trashNoteToolbar;
    @BindView(R.id.act_trash_note_rv_notes)
    RecyclerView trashNoteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_notes);
        ButterKnife.bind(this);

        setSupportActionBar(trashNoteToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Trash");

        TrashNoteAdapter trashNoteAdapter = new TrashNoteAdapter();
        trashNoteRecyclerView.setAdapter(trashNoteAdapter);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        trashNoteRecyclerView.setLayoutManager(layoutManager);

        trashNoteViewModel = ViewModelProviders.of(this).get(TrashNoteViewModel.class);
        trashNoteViewModel.getTrashNotes().observe(this, trashNotes -> trashNoteAdapter.setTrashNotes(trashNotes));

        //To delete a note
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                trashNoteViewModel.delete(trashNoteAdapter.getTrashNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(TrashNotesActivity.this, "Note Removed Permanently", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(trashNoteRecyclerView);

        //To Edit a note
        trashNoteAdapter.setOnTrashNoteItemClickListener(trashNote -> {
            Intent intent = new Intent(TrashNotesActivity.this, MakeEditNoteActivity.class);
            intent.putExtra(MakeEditNoteActivity.EXTRA_ID, trashNote.getId());
            intent.putExtra(MakeEditNoteActivity.EXTRA_TITLE, trashNote.getTitle());
            intent.putExtra(MakeEditNoteActivity.EXTRA_NOTE, trashNote.getNote());
            intent.putExtra(MakeEditNoteActivity.EXTRA_COLOR, trashNote.getColor());
            intent.putExtra(MakeEditNoteActivity.EXTRA_WEB_LINK, trashNote.getWebLink());
            intent.putExtra(MakeEditNoteActivity.EXTRA_IMAGE_PATH, trashNote.getImgPath());

            //Formatting currentTimeMillis in desired form before sending to MakeEditNoteActivity
            long currentTimeMillis = trashNote.getTimestamp();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa MMM dd, yyyy");
            Date resultdate = new Date(currentTimeMillis);
            String timeStamp = "Last changed";
            timeStamp = timeStamp + " " + String.valueOf(sdf.format(resultdate));
            intent.putExtra(MakeEditNoteActivity.EXTRA_TIMESTAMP, timeStamp);

            startActivityForResult(intent, EDIT_TRASH_NOTE_REQUEST);
        });

        //To Restore a note
        trashNoteAdapter.setOnTrashNoteItemLongClickListener(trashNote -> {
            Note restoredNote = new Note(trashNote.getId(), trashNote.getTitle(), trashNote.getNote(), trashNote.getTimestamp());
            restoredNote.setColor(trashNote.getColor());
            restoredNote.setWebLink(trashNote.getWebLink());
            restoredNote.setImgPath(trashNote.getImgPath());
            trashNoteViewModel.insert(restoredNote);
            trashNoteViewModel.delete(trashNote);
            Toast.makeText(TrashNotesActivity.this, "Note Restored", Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TRASH_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MakeEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(MakeEditNoteActivity.EXTRA_TITLE);
            String note = data.getStringExtra(MakeEditNoteActivity.EXTRA_NOTE);
            long timeStamp = data.getLongExtra(MakeEditNoteActivity.EXTRA_TIMESTAMP, 10000);
            String color = data.getStringExtra(MakeEditNoteActivity.EXTRA_COLOR);
            String webLink = data.getStringExtra(MakeEditNoteActivity.EXTRA_WEB_LINK);
            String imagePath = data.getStringExtra(MakeEditNoteActivity.EXTRA_IMAGE_PATH);

            TrashNote notedTrashNote = new TrashNote(title, note, timeStamp);
            notedTrashNote.setColor(color);
            notedTrashNote.setWebLink(webLink);
            notedTrashNote.setId(id);
            notedTrashNote.setImgPath(imagePath);
            trashNoteViewModel.update(notedTrashNote);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_TRASH_NOTE_REQUEST) {
            Toast.makeText(this, "Note Unchanged", Toast.LENGTH_SHORT).show();
        }
    }
}
