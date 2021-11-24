package com.mainway.playwords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<CharactersPlaceHolder> placeHolderList=new ArrayList<>();

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<CharactersPlaceHolder> onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

   public CharacterAdapter(){

    }

    private OnRecyclerViewItemClickListener<CharactersPlaceHolder> onRecyclerViewItemClickListener;
    public CharacterAdapter(List<CharactersPlaceHolder> placeHolders){
        this.placeHolderList=placeHolders;
    }
    @NonNull
    @NotNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_char,parent,false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CharacterAdapter.CharacterViewHolder holder, int position) {
        holder.bindCharacter(placeHolderList.get(position));
    }

    @Override
    public int getItemCount() {
        return placeHolderList.size();
    }
    public void clear(){
        this.placeHolderList.clear();
        notifyDataSetChanged();
    }

    public String getWord(){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < placeHolderList.size(); i++) {
            stringBuilder.append(placeHolderList.get(i).getCharacter());
        }
        return stringBuilder.toString();
    }
    public  void add(CharactersPlaceHolder charactersPlaceHolder){
        this.placeHolderList.add(charactersPlaceHolder);
        notifyItemInserted(placeHolderList.size()-1);
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public CharacterViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_item_char);
        }

        public  void bindCharacter(CharactersPlaceHolder charactersPlaceHolder){
            if(charactersPlaceHolder.isVisible()){
                textView.setText(charactersPlaceHolder.getCharacter().toString());
                textView.setVisibility(View.VISIBLE);
            }else {
                textView.setVisibility(View.INVISIBLE);
            }

            if(charactersPlaceHolder.isNull()){
                itemView.setBackground(null);
            }else {
                itemView.setBackgroundResource(R.drawable.background_item_char);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerViewItemClickListener!=null){
                        onRecyclerViewItemClickListener.onClick(charactersPlaceHolder,getAdapterPosition());
                    }
                }
            });

        }
    }
    public void makeWordVisible(String word){
        for (int i = 0; i <placeHolderList.size() ; i++) {
            if (placeHolderList.get(i).getTag()!=null && placeHolderList.get(i).getTag().equals(word)){
                placeHolderList.get(i).setVisible(true);
                notifyItemChanged(i);
            }
        }
    }
}
