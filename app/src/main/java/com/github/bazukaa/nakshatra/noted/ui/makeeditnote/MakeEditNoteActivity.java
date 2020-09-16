package com.github.bazukaa.nakshatra.noted.ui.makeeditnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.util.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakeEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.github.bazukaa.nakshatra.noted.EXTRA_TITLE";
    public static final String EXTRA_NOTE = "com.github.bazukaa.nakshatra.noted.EXTRA_NOTE";
    public static final String EXTRA_TIMESTAMP = "com.github.bazukaa.nakshatra.noted.EXTRA_TIMESTAMP";
    public static final String EXTRA_COLOR = "com.github.bazukaa.nakshatra.noted.EXTRA_COLOR";
    public static final String EXTRA_ID = "com.github.bazukaa.nakshatra.noted.EXTRA_ID";

    @BindView(R.id.act_makeNote_toolbar)
    Toolbar actMakeNoteToolbar;

    @BindView(R.id.act_makeNote_et_title)
    EditText titleEditText;
    @BindView(R.id.act_makeNote_et_note)
    EditText noteEditText;
    @BindView(R.id.act_makeNote_tv_time)
    TextView timeTextView;

    @BindView(R.id.act_makeNote_img_options)
    ImageView optionsMenu;

    @BindView(R.id.color_img_1)
    ImageView imageColor1;
    @BindView(R.id.color_img_2)
    ImageView imageColor2;
    @BindView(R.id.color_img_3)
    ImageView imageColor3;
    @BindView(R.id.color_img_4)
    ImageView imageColor4;
    @BindView(R.id.color_img_5)
    ImageView imageColor5;

    @BindView(R.id.color_1)
    View viewColor1;
    @BindView(R.id.color_2)
    View viewColor2;
    @BindView(R.id.color_3)
    View viewColor3;
    @BindView(R.id.color_4)
    View viewColor4;
    @BindView(R.id.color_5)
    View viewColor5;

    private String selectedNoteColor;

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
            selectedNoteColor = intent.getStringExtra(EXTRA_COLOR);

            if(selectedNoteColor == null)
                selectedNoteColor = Constants.COLOR_1;
            if(selectedNoteColor.equals(Constants.COLOR_1)) color1Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_2)) color2Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_3)) color3Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_4)) color4Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_5)) color5Selected();

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
        data.putExtra(EXTRA_COLOR, selectedNoteColor);

        //To edit the note
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
    @OnClick(R.id.act_makeNote_img_options)
    public void onOptionsClicked(){
        LinearLayout linearLayout = findViewById(R.id.layout_options);
        if(linearLayout.getVisibility() == View.GONE)
            linearLayout.setVisibility(View.VISIBLE);
        else
            linearLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.color_1)
    public void view1Clicked(){
        color1Selected();
    }
    @OnClick(R.id.color_2)
    public void view2Clicked(){
        color2Selected();
    }
    @OnClick(R.id.color_3)
    public void view3Clicked(){
        color3Selected();
    }
    @OnClick(R.id.color_4)
    public void view4Clicked(){
        color4Selected();
    }
    @OnClick(R.id.color_5)
    public void view5Clicked(){
        color5Selected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote();
    }

    @Override
    public void onBackPressed() {
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

    private void color1Selected(){
        selectedNoteColor = Constants.COLOR_1;
        imageColor1.setImageResource(R.drawable.ic_save);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    private void color2Selected(){
        selectedNoteColor = Constants.COLOR_2;
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(R.drawable.ic_save);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    private void color3Selected(){
        selectedNoteColor = Constants.COLOR_3;
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(R.drawable.ic_save);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    private void color4Selected(){
        selectedNoteColor = Constants.COLOR_4;
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(R.drawable.ic_save);
        imageColor5.setImageResource(0);
    }

    private void color5Selected(){
        selectedNoteColor = Constants.COLOR_5;
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(R.drawable.ic_save);
    }

}
