package com.sunmi.samples.printerx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.printerx.PrinterSdk;
import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.api.FileApi;
import com.sunmi.printerx.api.PrintResult;
import com.sunmi.samples.printerx.databinding.FragmentFileBinding;

public class FileFragment extends Fragment {

    private FragmentFileBinding binding;

    public FileFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fileUrl.setOnClickListener(view1 -> {
            printFile("https://img0.baidu.com/it/u=285137529,1114434781&fm=253&fmt=auto&app=138&f=JPEG?w=489&h=396");
        });

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                printFile(result.getData().getData().toString());
            }
        });

        binding.fileLocal.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            launcher.launch(intent);
        });


    }

    private void printFile(String url) {
        try {
            FileApi api = ((MainActivity)getActivity()).selectPrinter.fileApi();
            api.printFile(url, new PrintResult() {
                @Override
                public void onResult(int resultCode, String message) {
                    if(resultCode == 0) {
                        //打印完成
                    } else {
                        //打印失败
                    }
                }
            });
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }
}
