package com.github.bazukaa.nakshatra.noted.ui.displaynotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

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
        timeStamp = timeStamp+" "+String.valueOf(sdf.format(resultdate));
        holder.timeTv.setText(timeStamp);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private TextView noteTv;
        private TextView timeTv;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.act_main_tv_title);
            noteTv = itemView.findViewById(R.id.act_main_tv_note);
            timeTv = itemView.findViewById(R.id.act_main_tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotePosition(int position) {
        return notes.get(position);
    }

    //To edit a note
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
