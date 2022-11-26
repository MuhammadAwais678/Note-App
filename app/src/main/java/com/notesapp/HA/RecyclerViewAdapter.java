package com.notesapp.HA;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

private  Context context;
private  ArrayList<Notes> arrayList;
DatabaseHelper databaseHelper;


    RecyclerViewAdapter(Context context , ArrayList<Notes> arrayList,DatabaseHelper databaseHelper){

        this.context = context;
        this.arrayList = arrayList;
        this.databaseHelper = databaseHelper;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_body, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.txtTitle.setText(arrayList.get(position).getTitle());
        holder.txtContent.setText(arrayList.get(position).getContent());

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItems(position);


                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle,txtContent;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            llRow = itemView.findViewById(R.id.llRow);

        }
    }

    public void deleteItems(int pos){

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.noteDao().deleteNotes(new Notes(arrayList.get(pos).getId(),arrayList.get(pos).getTitle(),arrayList.get(pos).getContent()));

                        ((MainActivity)context).showNotes();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}
