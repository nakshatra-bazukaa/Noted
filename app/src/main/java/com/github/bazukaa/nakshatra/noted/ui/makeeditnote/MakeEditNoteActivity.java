package com.github.bazukaa.nakshatra.noted.ui.makeeditnote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.util.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakeEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.github.bazukaa.nakshatra.noted.EXTRA_TITLE";
    public static final String EXTRA_NOTE = "com.github.bazukaa.nakshatra.noted.EXTRA_NOTE";
    public static final String EXTRA_TIMESTAMP = "com.github.bazukaa.nakshatra.noted.EXTRA_TIMESTAMP";
    public static final String EXTRA_COLOR = "com.github.bazukaa.nakshatra.noted.EXTRA_COLOR";
    public static final String EXTRA_WEB_LINK = "com.github.bazukaa.nakshatra.noted.EXTRA_WEB_LINK";
    public static final String EXTRA_ID = "com.github.bazukaa.nakshatra.noted.EXTRA_ID";

    @BindView(R.id.act_makeNote_toolbar)
    Toolbar actMakeNoteToolbar;

    @BindView(R.id.act_makeNote_et_title)
    EditText titleEditText;
    @BindView(R.id.act_makeNote_et_note)
    EditText noteEditText;
    @BindView(R.id.act_makeNote_tv_time)
    TextView timeTextView;
    @BindView(R.id.act_makeNote_img_save_image)
    ImageView imageNote;
    @BindView(R.id.act_makeNote_web_url)
    LinearLayout webUrlLayout;
    @BindView(R.id.act_makeNote_tv_web_url)
    TextView tvWebUrl;

    @BindView(R.id.act_makeNote_img_options)
    ImageView optionsMenu;
    @BindView(R.id.layout_options_note_image)
    LinearLayout saveImage;
    @BindView(R.id.layout_options_note_url)
    LinearLayout saveWebUrl;

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
    private AlertDialog dialogAddUrl;
    private String webUrl;
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
            webUrl = intent.getStringExtra(EXTRA_WEB_LINK);

            if(selectedNoteColor == null)
                selectedNoteColor = Constants.COLOR_1;
            if(selectedNoteColor.equals(Constants.COLOR_1)) color1Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_2)) color2Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_3)) color3Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_4)) color4Selected();
            else if (selectedNoteColor.equals(Constants.COLOR_5)) color5Selected();

            if(webUrl != null){
                tvWebUrl.setText(webUrl);
                webUrlLayout.setVisibility(View.VISIBLE);
            }
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
        if(webUrlLayout.getVisibility() == View.VISIBLE){
            data.putExtra(EXTRA_WEB_LINK, tvWebUrl.getText().toString());
        }

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
    @OnClick(R.id.layout_options_note_image)
    public void saveImageClicked(){
        findViewById(R.id.layout_options).setVisibility(View.GONE);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MakeEditNoteActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.REQUEST_CODE_STORAGE_PERMISSION);
        }else{
            selectImage();
        }
    }
    @OnClick(R.id.layout_options_note_url)
    public void onWebClicked(){
        findViewById(R.id.layout_options).setVisibility(View.GONE);
        if(dialogAddUrl == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MakeEditNoteActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_add_url,
                    findViewById(R.id.layout_add_url_cl)
            );
            builder.setView(view);

            dialogAddUrl = builder.create();
            if(dialogAddUrl.getWindow() != null)
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            final EditText inputUrl = view.findViewById(R.id.layout_add_url_et_url);
            inputUrl.requestFocus();

            view.findViewById(R.id.layout_add_url_tv_add).setOnClickListener(v -> {
                if(inputUrl.getText().toString().trim().isEmpty())
                    Toast.makeText(MakeEditNoteActivity.this, "Add Url", Toast.LENGTH_SHORT).show();
                else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches())
                    Toast.makeText(MakeEditNoteActivity.this, "Enter valid Url", Toast.LENGTH_SHORT).show();
                else{
                    tvWebUrl.setText(inputUrl.getText());
                    webUrlLayout.setVisibility(View.VISIBLE);
                    dialogAddUrl.dismiss();
                }

            });

            view.findViewById(R.id.layout_add_url_tv_cancel).setOnClickListener(v -> {
                dialogAddUrl.dismiss();
            });
        }
        dialogAddUrl.show();
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
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_IMAGE);
        Toast.makeText(this, "Good here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE_SELECT_IMAGE && requestCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        Toast.makeText(this, "All set", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            Toast.makeText(this, "Shit", Toast.LENGTH_SHORT).show();
        }
    }
}
