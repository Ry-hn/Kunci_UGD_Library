package com.aditiaryohandoko.ugd_lib_x_yyyyy.view.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.databinding.FragmentChartDialogBinding;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Game;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.YearlyPeak;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChartDialogFragment extends DialogFragment {

    private FragmentChartDialogBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = FragmentChartDialogBinding.inflate(LayoutInflater.from(requireContext()));

        APIlib.getInstance().setActiveAnyChartView(binding.myChart);

        binding.myChart.setProgressBar(binding.progressBar);

        String json = getArguments().getString("yearlyJson", "");

        Gson gson = new Gson();

        YearlyPeak[] yearlyPeakList = gson.fromJson(json, YearlyPeak[].class);
        List<DataEntry> data = new ArrayList<>();

        for (YearlyPeak yp : yearlyPeakList) {
            data.add(new ValueDataEntry(yp.getYear(), yp.getValue()));
        }

        Cartesian cartesian = AnyChart.column();

        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");
        cartesian.animation(true);
        cartesian.title("Jumlah Pemain Tertinggi Pertahun");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Tahun");
        cartesian.yAxis(0).title("Jumlah Pemain");

        binding.myChart.setChart(cartesian);

        return new AlertDialog.Builder(requireActivity()).setView(binding.getRoot()).create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}