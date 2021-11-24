package com.mainway.playwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {
    private List<Level> levels=new ArrayList<>();
    private OnRecyclerViewItemClickListener<Level> onRecyclerViewItemClickListener;
    private Context context;

    public LevelAdapter(Context context,List<Level> levels,OnRecyclerViewItemClickListener<Level> onRecyclerViewItemClickListener){
        this.context = context;
        this.levels=levels;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_level,parent,false);
        return  new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LevelAdapter.LevelViewHolder holder, int position) {
        holder.bindLevel(levels.get(position),position);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder{
        private TextView levelNumber;

        public LevelViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            levelNumber=itemView.findViewById(R.id.tv_item_level_num);
        }
        public void bindLevel(Level level,int position){
                levelNumber.setText(String.valueOf(level.getId()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRecyclerViewItemClickListener.onClick(level,position);
                    }
                });
        }
    }
}
