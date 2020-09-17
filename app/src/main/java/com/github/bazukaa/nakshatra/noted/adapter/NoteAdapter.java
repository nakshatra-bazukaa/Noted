package com.github.bazukaa.nakshatra.noted.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.util.Constants;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;
    private Timer timer;
    private List<Note> notesSource;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleTv.setText(note.getTitle());
        holder.noteTv.setText(note.getNote());

        //Formatting currentTimeMillis in desired form
        long currentTimeMillis = note.getTimeStamp();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa MMM dd, yyyy");
        Date resultdate = new Date(currentTimeMillis);
        String timeStamp = "Last changed";
        timeStamp = timeStamp + " " + String.valueOf(sdf.format(resultdate));
        holder.timeTv.setText(timeStamp);

        // Setting Card bg color
        GradientDrawable gradientDrawable = (GradientDrawable) holder.noteCard.getBackground();
        if(note.getColor() != null)
            gradientDrawable.setColor(Color.parseColor(note.getColor()));
        else
            gradientDrawable.setColor(Color.parseColor(Constants.COLOR_1));

        // Set image
        if(note.getImgPath() != null){
            holder.imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImgPath()));
            holder.imageNote.setVisibility(View.VISIBLE);
        }else
            holder.imageNote.setVisibility(View.GONE);

    }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView titleTv, noteTv, timeTv;
        private LinearLayout noteCard;
        private RoundedImageView imageNote;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.act_main_tv_title);
            noteTv = itemView.findViewById(R.id.act_main_tv_note);
            timeTv = itemView.findViewById(R.id.act_main_tv_time);
            noteCard = itemView.findViewById(R.id.note_item_card);
            imageNote = itemView.findViewById(R.id.note_item_image);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notesSource = notes;
        notifyDataSetChanged();
    }
    public Note getNotePosition(int position) { return notes.get(position); }
    public void searchNotes(final String searchKeyword){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyword.trim().isEmpty()){
                    notes = notesSource;
                }else{
                    ArrayList<Note> temp = new ArrayList<>();
                    for(Note note : notes){
                        if(note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase()) || note.getNote().toLowerCase().contains(searchKeyword.toLowerCase())){
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }
    public void cancelTimer(){
        if(timer != null)
            timer.cancel();
    }
    //To edit a note
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
