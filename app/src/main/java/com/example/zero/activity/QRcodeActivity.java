package com.example.zero.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zero.greentravel_new.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kazu_0122 on 2017/11/20.
 */

public class QRcodeActivity extends Activity {

    private Button btn;
    private ImageView iv, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        iv = (ImageView) findViewById(R.id.qrcode_iv);
        back = (ImageView) findViewById(R.id.qrcode_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        generate(iv);
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generate(View view) {
        Bitmap qrBitmap = generateBitmap("http://www.csdn.net", 400, 400);
        iv.setImageBitmap(qrBitmap);
    }
}
