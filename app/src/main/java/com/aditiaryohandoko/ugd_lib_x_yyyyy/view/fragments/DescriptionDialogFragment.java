package com.aditiaryohandoko.ugd_lib_x_yyyyy.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.databinding.FragmentDescriptionDialogBinding;

public class DescriptionDialogFragment extends DialogFragment {

    private FragmentDescriptionDialogBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = FragmentDescriptionDialogBinding.inflate(LayoutInflater.from(getContext()));

        String desc = getArguments().getString("desc", "");
        binding.txtDesc.setText(desc);

        return new AlertDialog.Builder(requireActivity()).setView(binding.getRoot()).create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}