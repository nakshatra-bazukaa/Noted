package com.github.bazukaa.nakshatra.noted.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.bazukaa.nakshatra.noted.R;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import com.github.bazukaa.nakshatra.noted.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrashNoteAdapter extends RecyclerView.Adapter<TrashNoteAdapter.TrashNoteHolder> {

    private List<TrashNote> trashNotes = new ArrayList<>();
    private TrashNoteAdapter.OnTrashNoteItemClickListener listener;
    private TrashNoteAdapter.OnTrashNoteItemLongClickListener longClickListener;

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

        // Card bg color
        GradientDrawable gradientDrawable = (GradientDrawable) holder.noteCard.getBackground();

        if(trashNote.getColor() != null) {
            gradientDrawable.setColor(Color.parseColor(trashNote.getColor()));
        }
        else {
            gradientDrawable.setColor(Color.parseColor(Constants.COLOR_1));
        }

    }

    @Override
    public int getItemCount() {
        return trashNotes.size();
    }

    public class TrashNoteHolder extends RecyclerView.ViewHolder{

        private TextView titleTv, noteTv, timeTv;
        private LinearLayout noteCard;


        public TrashNoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.act_main_tv_title);
            noteTv = itemView.findViewById(R.id.act_main_tv_note);
            timeTv = itemView.findViewById(R.id.act_main_tv_time);
            noteCard = itemView.findViewById(R.id.note_item_card);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onTrashNoteItemClick(trashNotes.get(position));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onTrashNoteItemLongClick(trashNotes.get(position));
                    }
                    return true;
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

    public interface OnTrashNoteItemLongClickListener{
        void onTrashNoteItemLongClick(TrashNote trashNote);
    }

    public void setOnTrashNoteItemLongClickListener(TrashNoteAdapter.OnTrashNoteItemLongClickListener listener){
        this.longClickListener = listener;
    }
}
