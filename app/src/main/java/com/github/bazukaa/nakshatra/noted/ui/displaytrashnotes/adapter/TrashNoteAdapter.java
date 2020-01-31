package com.github.bazukaa.nakshatra.noted.ui.displaytrashnotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import com.github.bazukaa.nakshatra.noted.ui.displaynotes.adapter.NoteAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrashNoteAdapter extends RecyclerView.Adapter<TrashNoteAdapter.TrashNoteHolder> {

    private List<TrashNote> trashNotes = new ArrayList<>();
    private TrashNoteAdapter.OnTrashNoteItemClickListener listener;

    @NonNull
    @Override
    public TrashNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrashNoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false));    }

    @Override
    public void onBindViewHolder(@NonNull TrashNoteHolder holder, int position) {

        TrashNote trashNote = trashNotes.get(position);
        holder.titleTv.setText(trashNote.getTitle());
        holder.noteTv.setText(trashNote.getNote());

        //Formatting currentTimeMillis in desired form
        long currentTimeMillis = trashNote.getTimestamp();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa MMM dd, yyyy");
        Date resultdate = new Date(currentTimeMillis);
        String timeStamp = "Last changed";
        timeStamp = timeStamp+" "+String.valueOf(sdf.format(resultdate));
        holder.timeTv.setText(timeStamp);

    }

    @Override
    public int getItemCount() {
        return trashNotes.size();
    }

    public class TrashNoteHolder extends RecyclerView.ViewHolder{

        private TextView titleTv;
        private TextView noteTv;
        private TextView timeTv;

        public TrashNoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.act_main_tv_title);
            noteTv = itemView.findViewById(R.id.act_main_tv_note);
            timeTv = itemView.findViewById(R.id.act_main_tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onTrashNoteItemClick(trashNotes.get(position));
                    }
                }
            });
        }
    }

    public void setTrashNotes(List<TrashNote> trashNotes) {
        this.trashNotes = trashNotes;
        notifyDataSetChanged();
    }

    public TrashNote getTrashNotePosition(int position) {
        return trashNotes.get(position);
    }

    public interface OnTrashNoteItemClickListener {
        void onTrashNoteItemClick(TrashNote trashNote);
    }

    public void setOnTrashNoteItemClickListener(TrashNoteAdapter.OnTrashNoteItemClickListener listener) {
        this.listener = listener;
    }
}
