package com.aditiaryohandoko.ugd_lib_x_yyyyy.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.adapter.AdapterCardGame;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.adapter.OnCardClickListener;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.databinding.FragmentStoreBinding;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Game;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.view.MainViewModel;
import com.google.gson.Gson;

import java.util.List;

public class StoreFragment extends Fragment implements OnCardClickListener {

    private FragmentStoreBinding binding;
    private MainViewModel viewModel;

    private AdapterCardGame adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.loadResponse();

        viewModel.getGameListLiveData().observe(getViewLifecycleOwner(), this::setupRecyclerView);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    private void setupRecyclerView(List<Game> data) {
        adapter = new AdapterCardGame(data, this);
        binding.rvStorePage.setAdapter(adapter);
        binding.rvStorePage.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(Game game) {
        viewModel.addToCart(game);
        Toast.makeText(getContext(), "Game Behasil ditambahkan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChartClick(Game game) {
        FragmentManager fm = getParentFragmentManager();
        ChartDialogFragment dialog = new ChartDialogFragment();
        dialog.show(fm, "dialog");

        Gson gson = new Gson();
        String json = gson.toJson(game.getPeakYearlyOnlineUser());

        Bundle bundle = new Bundle();
        bundle.putString("yearlyJson", json);
        dialog.setArguments(bundle);
    }

    @Override
    public void onDescriptionClick(String desc) {
        FragmentManager fm = getParentFragmentManager();
        DescriptionDialogFragment dialog = new DescriptionDialogFragment();
        dialog.show(fm, "dialog");

        Bundle args = new Bundle();
        args.putString("desc", desc);
        dialog.setArguments(args);
    }
}