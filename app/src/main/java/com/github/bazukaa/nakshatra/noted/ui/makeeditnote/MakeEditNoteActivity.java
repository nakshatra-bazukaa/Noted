package com.github.bazukaa.nakshatra.noted.ui.makeeditnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.github.bazukaa.nakshatra.noted.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakeEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.github.bazukaa.nakshatra.noted.EXTRA_TITLE";

    public static final String EXTRA_NOTE = "com.github.bazukaa.nakshatra.noted.EXTRA_NOTE";

    public static final String EXTRA_TIMESTAMP = "com.github.bazukaa.nakshatra.noted.EXTRA_TIMESTAMP";

    public static final String EXTRA_ID = "com.github.bazukaa.nakshatra.noted.EXTRA_ID";



    @BindView(R.id.act_makeNote_toolbar)
    Toolbar actMakeNoteToolbar;

    @BindView(R.id.act_makeNote_et_title)
    EditText titleEditText;

    @BindView(R.id.act_makeNote_et_note)
    EditText noteEditText;

    @BindView(R.id.act_makeNote_tv_time)
    TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        ButterKnife.bind(this);

        //Setting up the toolbar
        setSupportActionBar(actMakeNoteToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        //To edit the note
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE));
            noteEditText.setText(intent.getStringExtra(EXTRA_NOTE));
            timeTextView.setText(intent.getStringExtra(EXTRA_TIMESTAMP));
        }
    }

    private void saveNote(){
        String title = titleEditText.getText().toString();
        String note = noteEditText.getText().toString();
        long timeStamp = System.currentTimeMillis();

        if(title.trim().isEmpty() && note.trim().isEmpty()){
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_NOTE, note);
        data.putExtra(EXTRA_TIMESTAMP, timeStamp);

        //To edit the note
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.act_make_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note: saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
