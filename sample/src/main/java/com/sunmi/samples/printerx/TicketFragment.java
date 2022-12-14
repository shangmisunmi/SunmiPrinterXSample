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
import com.sunmi.printerx.api.LineApi;
import com.sunmi.printerx.api.PrintResult;
import com.sunmi.printerx.enums.Align;
import com.sunmi.printerx.enums.DividingLine;
import com.sunmi.printerx.enums.ErrorLevel;
import com.sunmi.printerx.enums.HumanReadable;
import com.sunmi.printerx.enums.ImageAlgorithm;
import com.sunmi.printerx.style.BarcodeStyle;
import com.sunmi.printerx.style.BaseStyle;
import com.sunmi.printerx.style.BitmapStyle;
import com.sunmi.printerx.style.QrStyle;
import com.sunmi.printerx.style.TextStyle;
import com.sunmi.samples.printerx.databinding.FragmentTicketBinding;

public class TicketFragment extends Fragment {

    private FragmentTicketBinding binding;

    public TicketFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ticketText.setOnClickListener(view1 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                api.initLine(BaseStyle.getStyle());
                api.printText("这行内容将直接打印出", TextStyle.getStyle());
                api.addText("不同风格的内容:", TextStyle.getStyle());
                api.addText("加粗", TextStyle.getStyle().enableBold(true));
                api.addText("下划线", TextStyle.getStyle().enableUnderline(true));
                api.addText("删除线", TextStyle.getStyle().enableStrikethrough(true));
                api.addText("倾斜", TextStyle.getStyle().enableItalics(true));
                api.addText("\n", TextStyle.getStyle());
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketTexts.setOnClickListener(view12 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                TextStyle textStyle = TextStyle.getStyle().setAlign(Align.CENTER);
                api.printTexts(new String[]{"第一列","第二列","第三列"},
                        new int[]{1, 1, 1},
                        new TextStyle[]{textStyle, textStyle, textStyle});
                api.printTexts(new String[]{"第一列","第二列","第三列"},
                        new int[]{1, 1, 1},
                        new TextStyle[]{TextStyle.getStyle().setAlign(Align.LEFT),
                                TextStyle.getStyle().setAlign(Align.CENTER),
                                        TextStyle.getStyle().setAlign(Align.RIGHT)});
                textStyle = TextStyle.getStyle().setAlign(Align.LEFT);
                api.printTexts(new String[]{"第一列","第二列","第三列"},
                        new int[]{1, 1, 1},
                        new TextStyle[]{textStyle, textStyle, textStyle});
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketBar.setOnClickListener(view13 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                BarcodeStyle barcodeStyle = BarcodeStyle.getStyle()
                        .setAlign(Align.CENTER)
                        .setDotWidth(2)
                        .setBarHeight(100)
                        .setReadable(HumanReadable.POS_TWO);
                api.printBarCode("0123456789", barcodeStyle);
                api.printBarCode("0123456789ABCDEFG", barcodeStyle);
                barcodeStyle.setWidth(384);
                api.printBarCode("0123456789ABCDEFG", barcodeStyle);
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketQr.setOnClickListener(view14 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                api.printQrCode("http://www.sunmi.com", QrStyle.getStyle().setAlign(Align.CENTER)
                        .setDot(9).setErrorLevel(ErrorLevel.L));
                api.initLine(BaseStyle.getStyle().setAlign(Align.CENTER));
                api.printText("http://www.sunmi.com", TextStyle.getStyle().enableBold(true));
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketLogo.setOnClickListener(view15 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sunmi, options);
                api.printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.BINARIZATION).setValue(120).setWidth(384).setHeight(150));
                api.printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.DITHERING).setWidth(384).setHeight(150));
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketLine.setOnClickListener(view16 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                api.printDividingLine(DividingLine.EMPTY, 20);
                api.printDividingLine(DividingLine.DOTTED, 5);
                api.printDividingLine(DividingLine.EMPTY, 20);
                api.printDividingLine(DividingLine.SOLID, 10);
                api.autoOut();
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });

        binding.ticketAll.setOnClickListener(view17 -> testTicketSample());

        binding.ticketAllRet.setOnClickListener(view18 -> {
            try {
                LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
                //需要监听打印结果时可以开启事务模式,只需要调用一次
                api.enableTransMode(true);
                testTicketSampleResult();
                api.enableTransMode(false);
            } catch (SdkException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 一个标准的小票内容
     */
    private void testTicketSample() {
        try {
            LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
            api.initLine(BaseStyle.getStyle().setAlign(Align.CENTER));
            api.addText("******", TextStyle.getStyle());
            api.addText("商米之家", TextStyle.getStyle().enableBold(true).setTextWidthRatio(1).setTextHeightRatio(1));
            api.printText( "******", TextStyle.getStyle());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sunmi, options);
            api.printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.BINARIZATION).setValue(120).setWidth(384).setHeight(150));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.printDividingLine(DividingLine.EMPTY, 30);
            TextStyle textStyle = TextStyle.getStyle().setAlign(Align.LEFT);
            TextStyle[] textStyles = new TextStyle[]{textStyle, textStyle, textStyle};
            api.printTexts(new String[]{"商品1","商品12","商品13"}, new int[]{1, 1, 1}, textStyles);
            api.printTexts(new String[]{"商品21","商品22","商品23"}, new int[]{1, 1, 1}, textStyles);
            api.printTexts(new String[]{"商品31","商品32","商品33"}, new int[]{1, 1, 1}, textStyles);
            api.printText("商品信息条码信息:", TextStyle.getStyle());
            api.printBarCode("1234567890", BarcodeStyle.getStyle().setAlign(Align.CENTER).setReadable(HumanReadable.POS_TWO));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.initLine(BaseStyle.getStyle().setAlign(Align.CENTER));
            api.printText("--已支付--", TextStyle.getStyle().setTextSize(48));
            api.printText( "预计19：00送达", TextStyle.getStyle().setTextSize(48));
            api.initLine(BaseStyle.getStyle().setAlign(Align.LEFT));
            api.printText( "【下单时间】2021-8-1 12:00", TextStyle.getStyle());
            api.addText("【备注】", TextStyle.getStyle());
            api.printText("尽快送达", TextStyle.getStyle().setTextHeightRatio(2));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.printText("【发票抬头】", TextStyle.getStyle().setTextSize(16));
            api.printQrCode("1234567890", QrStyle.getStyle().setDot(9).setAlign(Align.CENTER));
            api.autoOut();
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印内容并获取打印的结果
     * 需要开启打印事务模式
     */
    private void testTicketSampleResult() {
        try {
            LineApi api = ((MainActivity)getActivity()).selectPrinter.lineApi();
            api.initLine(BaseStyle.getStyle().setAlign(Align.CENTER));
            api.addText("******", TextStyle.getStyle());
            api.addText("商米之家", TextStyle.getStyle().enableBold(true).setTextWidthRatio(1).setTextHeightRatio(1));
            api.printText( "******", TextStyle.getStyle());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sunmi, options);
            api.printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.BINARIZATION).setValue(120).setWidth(384).setHeight(150));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.printDividingLine(DividingLine.EMPTY, 30);
            TextStyle textStyle = TextStyle.getStyle().setAlign(Align.LEFT);
            TextStyle[] textStyles = new TextStyle[]{textStyle, textStyle, textStyle};
            api.printTexts(new String[]{"商品1","商品12","商品13"}, new int[]{1, 1, 1}, textStyles);
            api.printTexts(new String[]{"商品21","商品22","商品23"}, new int[]{1, 1, 1}, textStyles);
            api.printTexts(new String[]{"商品31","商品32","商品33"}, new int[]{1, 1, 1}, textStyles);
            api.printText("商品信息条码信息:", TextStyle.getStyle());
            api.printBarCode("1234567890", BarcodeStyle.getStyle().setAlign(Align.CENTER).setReadable(HumanReadable.POS_TWO));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.initLine(BaseStyle.getStyle().setAlign(Align.CENTER));
            api.printText("--已支付--", TextStyle.getStyle().setTextSize(48));
            api.printText( "预计19：00送达", TextStyle.getStyle().setTextSize(48));
            api.initLine(BaseStyle.getStyle().setAlign(Align.LEFT));
            api.printText( "【下单时间】2021-8-1 12:00", TextStyle.getStyle());
            api.addText("【备注】", TextStyle.getStyle());
            api.printText("尽快送达", TextStyle.getStyle().setTextHeightRatio(2));
            api.printDividingLine(DividingLine.EMPTY, 30);
            api.printDividingLine(DividingLine.DOTTED, 2);
            api.printText("【发票抬头】", TextStyle.getStyle().setTextSize(16));
            api.printQrCode("1234567890", QrStyle.getStyle().setDot(9).setAlign(Align.CENTER));
            api.autoOut();
            api.printTrans(new PrintResult() {
                @Override
                public void onResult(int resultCode, String message) {
                    if(resultCode == 0) {
                        //打印成功
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
