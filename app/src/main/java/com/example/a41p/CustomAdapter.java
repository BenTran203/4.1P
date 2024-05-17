package com.example.a41p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> task_id;
    private final ArrayList<String> task_title;
    private final ArrayList<String> task_description;
    private final ArrayList<String> task_duedate;

    public CustomAdapter(Activity activity, Context context, ArrayList<String> task_id, ArrayList<String> task_title, ArrayList<String> task_description, ArrayList<String> task_duedate) {
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_description = task_description;
        this.task_duedate = task_duedate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.task_title_txt.setText(task_title.get(position));
        holder.task_description_txt.setText(task_description.get(position));
        holder.task_duedate_txt.setText(task_duedate.get(position));

        holder.mainLayout.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", task_id.get(adapterPosition));
                intent.putExtra("title", task_title.get(adapterPosition));
                intent.putExtra("description", task_description.get(adapterPosition));
                intent.putExtra("due date", task_duedate.get(adapterPosition));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_title.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task_title_txt, task_description_txt, task_duedate_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title_txt = itemView.findViewById(R.id.task_title_txt);
            task_description_txt = itemView.findViewById(R.id.task_description_txt);
            task_duedate_txt = itemView.findViewById(R.id.task_duedate_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
