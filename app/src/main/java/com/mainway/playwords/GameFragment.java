package com.mainway.playwords;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
    private static final String TAG = "GameFragment";
    private Level level;
    private Context context;
    private View guessActionContainer;
    private View cancel;
    private View accept;
    private CharacterAdapter guessCharacterAdapter;
    private CharacterAdapter wordsCharacterAdapter;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level=getArguments().getParcelable("level");
        Log.i(TAG, "onCreate:  get level");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guessActionContainer=view.findViewById(R.id.frameLayout_game_guessWords);
        accept=view.findViewById(R.id.btn_game_ok);
        cancel=view.findViewById(R.id.btn_game_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessActionContainer.setVisibility(View.GONE);
                guessCharacterAdapter.clear();
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word=guessCharacterAdapter.getWord();
                for (int i = 0; i < level.getWords().size(); i++) {
                    if(word.equalsIgnoreCase(level.getWords().get(i))){
                        wordsCharacterAdapter.makeWordVisible(word);
                        Toast.makeText(context, "  کلمه را درست حدس زدی :"+word, Toast.LENGTH_SHORT).show();
                        cancel.performClick();
                        return;
                    }

                }
                cancel.performClick();
                Toast.makeText(context, "کلمه رو درست حدس نزدی", Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView wordRecyclerView=view.findViewById(R.id.rv_game_characters);
      
        List<Character> uniqueCharacters=GamePlayUtil.extractCharactersOfWord(level.getWords());
        List<CharactersPlaceHolder> placeHolderList=new ArrayList<>();
        for (int i = 0; i < uniqueCharacters.size(); i++) {
            CharactersPlaceHolder charactersPlaceHolder=new CharactersPlaceHolder();
            charactersPlaceHolder.setVisible(true);
            charactersPlaceHolder.setCharacter(uniqueCharacters.get(i));
            placeHolderList.add(charactersPlaceHolder);
        }
        
        
        CharacterAdapter characterAdapter=new CharacterAdapter(placeHolderList);
        wordRecyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        wordRecyclerView.setAdapter(characterAdapter);
        characterAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener<CharactersPlaceHolder>() {
            @Override
            public void onClick(CharactersPlaceHolder item, int position) {
                guessActionContainer.setVisibility(View.VISIBLE);
                guessCharacterAdapter.add(item);
            }
        });

        guessCharacterAdapter =new CharacterAdapter();
        RecyclerView guessRecyclerView=view.findViewById(R.id.rv_game_guess);
        guessRecyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        guessRecyclerView.setAdapter(guessCharacterAdapter);



        int maxLength=0;
        for (int i = 0; i < level.getWords().size(); i++) {
            if (level.getWords().get(i).length()>maxLength){
                maxLength=level.getWords().get(i).length();
            }
        }
        RecyclerView wordRV=view.findViewById(R.id.rv_gameFragment_words);
        wordRV.setLayoutManager(new GridLayoutManager(context,maxLength,RecyclerView.VERTICAL,false));
        List<CharactersPlaceHolder> wordsPlaceHolder=new ArrayList<>();
        for (int i = 0; i < level.getWords().size(); i++) {

            for (int j = 0; j < maxLength; j++) {
                CharactersPlaceHolder charactersPlaceHolder=new CharactersPlaceHolder();
                if(j<level.getWords().get(i).length()){
                    charactersPlaceHolder.setCharacter(level.getWords().get(i).charAt(j));
                    charactersPlaceHolder.setVisible(false);
                    charactersPlaceHolder.setNull(false);
                    charactersPlaceHolder.setTag(level.getWords().get(i));
                }else{
                    charactersPlaceHolder.setNull(true);
                }
                wordsPlaceHolder.add(charactersPlaceHolder);
            }

        }
        wordsCharacterAdapter=new CharacterAdapter(wordsPlaceHolder);
        wordRV.setAdapter(wordsCharacterAdapter);






    }
}
