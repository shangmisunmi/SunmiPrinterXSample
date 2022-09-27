package com.sunmi.samples.printerx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.printerx.SdkException;
import com.sunmi.samples.printerx.databinding.FragmentCashBinding;


public class CashFragment extends Fragment {

    private FragmentCashBinding binding;

    public CashFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cashSwitch.setOnClickListener(view1 -> {
            try {
                ((MainActivity)getActivity()).selectPrinter.cashDrawerApi().open(null);
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.cashStatus.setOnClickListener(view12 -> {
            try {
                boolean ret = ((MainActivity)getActivity()).selectPrinter.cashDrawerApi().isOpen();
                if(ret) {
                    binding.cashStatusShow.setText("on");
                } else {
                    binding.cashStatusShow.setText("off");
                }
            } catch (SdkException e) {
                //若接口不支持，可以认为设备不支持钱箱
                binding.cashStatusShow.setText("none");
            }
        });
    }
}
