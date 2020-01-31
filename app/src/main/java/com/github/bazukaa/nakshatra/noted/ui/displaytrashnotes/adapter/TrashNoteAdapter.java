package com.github.bazukaa.nakshatra.noted.ui.displaytrashnotes.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.bazukaa.nakshatra.noted.R;

public class TrashNoteAdapter {

    public class TrashNoteHolder extends RecyclerView.ViewHolder{

        private TextView titleTv;
        private TextView noteTv;
        private TextView timeTv;

        public TrashNoteHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
