package com.sunmi.samples.printerx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.api.LcdApi;
import com.sunmi.printerx.enums.Command;
import com.sunmi.samples.printerx.databinding.FragmentLcdBinding;

public class LcdFragment extends Fragment {

    private FragmentLcdBinding binding;
    
    public LcdFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLcdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.lcdCtrl.setOnClickListener(view1 -> {
            try {
                LcdApi api = ((MainActivity)getActivity()).selectPrinter.lcdApi();
                api.config(Command.INIT);
                api.config(Command.CLEAR);
            }catch (SdkException e){
                e.printStackTrace();
            }
        });
        binding.lcdLine.setOnClickListener(view12 -> {
            try {
                LcdApi api = ((MainActivity)getActivity()).selectPrinter.lcdApi();
                api.config(Command.CLEAR);
                api.showText("SUNMI", 32, true);
            }catch (SdkException e){
                e.printStackTrace();
            }
        });
        binding.lcdLines.setOnClickListener(view13 -> {
            try {
                LcdApi api = ((MainActivity)getActivity()).selectPrinter.lcdApi();
                api.config(Command.CLEAR);
                api.showTexts(new String[]{"ABCDEF", "123456"}, new int[]{1,1});
            }catch (SdkException e){
                e.printStackTrace();
            }
        });
        binding.lcdLogo.setOnClickListener(view14 -> {
            try {
                LcdApi api = ((MainActivity)getActivity()).selectPrinter.lcdApi();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
                api.config(Command.CLEAR);
                api.showBitmap(bitmap);
            }catch (SdkException e){
                e.printStackTrace();
            }
        });
    }
}
