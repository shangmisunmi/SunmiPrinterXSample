package com.sunmi.samples.printerx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.api.CanvasApi;
import com.sunmi.printerx.api.PrintResult;
import com.sunmi.printerx.enums.HumanReadable;
import com.sunmi.printerx.enums.ImageAlgorithm;
import com.sunmi.printerx.enums.Rotate;
import com.sunmi.printerx.enums.Shape;
import com.sunmi.printerx.style.AreaStyle;
import com.sunmi.printerx.style.BarcodeStyle;
import com.sunmi.printerx.style.BaseStyle;
import com.sunmi.printerx.style.BitmapStyle;
import com.sunmi.printerx.style.QrStyle;
import com.sunmi.printerx.style.TextStyle;
import com.sunmi.samples.printerx.databinding.FragmentLabelBinding;

public class LabelFragment extends Fragment {

    private FragmentLabelBinding binding;

    public LabelFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLabelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.labelTest1.setOnClickListener(view1 -> printLabel1(Integer.parseInt(binding.labelCountInput.getEditableText().toString())));
        binding.labelTest2.setOnClickListener(view12 -> printLabel2(Integer.parseInt(binding.labelCountInput.getEditableText().toString())));
        binding.labelTest3.setOnClickListener(view13 -> printLabel3(Integer.parseInt(binding.labelCountInput.getEditableText().toString())));
    }

    /**
     * 打印一张30*20mm 只包含内容的小标签
     * 商米打印机可根据1mm = 8像素的关系构建标签内容
     */
    private void printLabel1(int count) {
        try {
            CanvasApi api = ((MainActivity)getActivity()).selectPrinter.canvasApi();
            api.initCanvas(BaseStyle.getStyle().setWidth(240).setHeight(160));
            api.renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(240).setHeight(159));
            api.renderText("品牌：SUNMI", TextStyle.getStyle().setTextSize(32).enableBold(true).setPosX(10).setPosY(10));
            api.renderText("商米是一家以“利他心”为核心价值观的物联网科技公司", TextStyle.getStyle().setTextSize(20).setPosX(60)
                    .setPosY(50).setWidth(160).setHeight(100));
            api.printCanvas(count, new PrintResult() {
                @Override
                public void onResult(int resultCode, String message) throws RemoteException {
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

    /**
     * 打印一张商品信息标签
     */
    private void  printLabel2(int count) {
        try {
            CanvasApi api = ((MainActivity)getActivity()).selectPrinter.canvasApi();
            api.initCanvas(BaseStyle.getStyle().setWidth(384).setHeight(220));
            api.renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(384).setHeight(219));
            api.renderText("可口可乐(2L)", TextStyle.getStyle().setTextSize(30).enableBold(true)
                    .setPosX(10).setPosY(20));
            api.renderText("2L", TextStyle.getStyle().setTextSize(20)
                    .setPosX(10).setPosY(60));
            api.renderText("200000", TextStyle.getStyle().setTextSize(20)
                    .setPosX(10).setPosY(85));
            api.renderText("瓶", TextStyle.getStyle().setTextSize(24)
                    .setPosX(10).setPosY(130));
            api.renderBarCode("12345678", BarcodeStyle.getStyle().setPosX(200).setPosY(60)
                    .setReadable(HumanReadable.POS_TWO).setDotWidth(2).setBarHeight(60).setWidth(160));
            api.renderText("￥ 7.8", TextStyle.getStyle().setTextSize(16).setTextWidthRatio(1).setTextHeightRatio(1)
                    .enableBold(true).setPosX(190).setPosY(160));
            api.printCanvas(count, new PrintResult() {
                @Override
                public void onResult(int resultCode, String message) throws RemoteException {
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

    /**
     * 打印一张品牌说明标签
     */
    private void printLabel3(int count) {

        try {
            CanvasApi api = ((MainActivity)getActivity()).selectPrinter.canvasApi();
            api.initCanvas(BaseStyle.getStyle().setWidth(420).setHeight(220));
            api.renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(420).setHeight(219));
            api.renderQrCode("www.sunmi.com", QrStyle.getStyle().setDot(3).setPosX(20).setPosY(20).setWidth(120).setHeight(120));
            api.renderText("SUNMITECH", TextStyle.getStyle().setPosX(140).setPosY(20).setRotate(Rotate.ROTATE_90));
            api.renderBarCode("4006666509", BarcodeStyle.getStyle().setPosX(180).setPosY(50)
                    .setReadable(HumanReadable.POS_TWO).setWidth(220).setHeight(90));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunmi, options);
            api.renderBitmap(bitmap, BitmapStyle.getStyle().setAlgorithm(ImageAlgorithm.DITHERING)
                    .setPosX(20).setPosY(150).setWidth(100).setHeight(60));
            api.printCanvas(count, new PrintResult() {
                @Override
                public void onResult(int resultCode, String message) throws RemoteException {
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
