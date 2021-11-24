package com.mainway.playwords;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {
     private Context context;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView= (RecyclerView) view;
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        LevelAdapter adapter=new LevelAdapter(context, GamePlayUtil.createLevels(), new OnRecyclerViewItemClickListener<Level>() {
            @Override
            public void onClick(Level item, int position) {
                Bundle bundle=new Bundle();
                bundle.putParcelable("level", item);
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_game_fragment,bundle);

            }
        });
        recyclerView.setAdapter(adapter);
    }
}
