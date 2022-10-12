package com.sunmi.samples.printerx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.printerx.PrinterSdk;
import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.enums.PrinterInfo;
import com.sunmi.samples.printerx.databinding.FragmentInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private ArrayAdapter<PrinterSdk.Printer> arrayAdapter;
    private PrinterSdk.Printer printer = null;


    public InfoFragment() { super();}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(arrayAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showPrinterInfo(arrayAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.change.setOnClickListener(view1 -> changeSelectPrinter());
        showPrinters();
    }

    /**
     * 获取当前可选择的打印机列表
     */
    private void showPrinters() {
        try {
            PrinterSdk.getInstance().getPrinter(getContext(), new PrinterSdk.PrinterListen() {
                @Override
                public void onDefPrinter(PrinterSdk.Printer printer) {

                }

                @Override
                public void onPrinters(List<PrinterSdk.Printer> printers) {
                    arrayAdapter.clear();
                    arrayAdapter.addAll(printers);
                }
            });
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示选中打印机详情
     */
    private void showPrinterInfo(PrinterSdk.Printer printer) {
        try {
            this.printer = printer;
            binding.infoStatusTxt.setText(printer.queryApi().getStatus().name());
            binding.infoNameTxt.setText(printer.queryApi().getInfo(PrinterInfo.NAME));
            binding.infoTypeTxt.setText(printer.queryApi().getInfo(PrinterInfo.TYPE));
            binding.infoPaperTxt.setText(printer.queryApi().getInfo(PrinterInfo.PAPER));
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定选中的打印机为当前控制的打印机
     */
    public void changeSelectPrinter() {
        ((MainActivity)getActivity()).selectPrinter = printer;
    }
}
