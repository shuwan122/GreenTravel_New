package com.example.zero.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.view.SimpleTextView;

import java.io.File;

/**
 * Created by jojo on 2017/9/27.
 */

//TODO 用户数据修改写回

public class UserActivity extends Activity implements View.OnClickListener {
    private ImageView backArrow;
    private LinearLayout user_img;
    private ImageView mImage;
    private View changeName;
    private View changePhone;
    private View changePw;
    private Bitmap mBitmap;
    private TextView user_name;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        innitView();
        /**
         *  监听器
         */
        backArrow.setOnClickListener(this);
        user_img.setOnClickListener(this);
        changeName.setOnClickListener(this);
        changePhone.setOnClickListener(this);
        changePw.setOnClickListener(this);
    }

    public void innitView() {
        user_name = (TextView) findViewById(R.id.user_name_gray);
        backArrow = (ImageView) findViewById(R.id.user_back_arrow);
        user_img = (LinearLayout) findViewById(R.id.user_img_change);
        mImage = (ImageView) findViewById(R.id.user_img_show);
        changeName = (View) findViewById(R.id.user_name_change);
        changePhone = (View) findViewById(R.id.user_phone_change);
        changePw = (View) findViewById(R.id.user_pw_change);
    }

    /**
     * 显示修改用户名的对话框
     */
    protected void showChangeNameDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_change_name, null);
        final SimpleTextView simpleTextView = view.findViewById(R.id.dialog_name);
        simpleTextView.setHintText("新用户名");
        new AlertDialog.Builder(this)
                .setTitle("修改用户名")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String userMsg = simpleTextView.getText().toString();
                        user_name.setText(userMsg);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /**
     * 显示修改密码的对话框
     */
    protected void showChangePwDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_change_pw, null);
        final SimpleTextView simpleTextView1 = view.findViewById(R.id.dialog_pw_pre);
        final SimpleTextView simpleTextView2 = view.findViewById(R.id.dialog_pw_new);
        final SimpleTextView simpleTextView3 = view.findViewById(R.id.dialog_pw_confirm);
        simpleTextView1.setHintText("原密码");
        simpleTextView2.setHintText("新密码");
        simpleTextView3.setHintText("重复新密码");
        simpleTextView1.setLeftImage(R.drawable.lock_fill);
        simpleTextView2.setLeftImage(R.drawable.lock_fill);
        simpleTextView3.setLeftImage(R.drawable.lock_fill);
        new AlertDialog.Builder(this)
                .setTitle("修改密码")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /**
     * 显示修改手机的对话框
     */
    protected void showChangePhoneDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_change_phone, null);
        SimpleTextView simpleTextView1 = view.findViewById(R.id.dialog_phone_pre);
        SimpleTextView simpleTextView2 = view.findViewById(R.id.dialog_phone_new);
        SimpleTextView simpleTextView3 = view.findViewById(R.id.dialog_phone_new_confirm);
        simpleTextView1.setHintText("原手机收到的验证码");
        simpleTextView2.setHintText("新手机");
        simpleTextView3.setHintText("新手机收到的验证码");
        simpleTextView1.setLeftImage(R.drawable.identify);
        simpleTextView2.setLeftImage(R.drawable.user_fill);
        simpleTextView3.setLeftImage(R.drawable.identify);
        new AlertDialog.Builder(this)
                .setTitle("修改绑定手机")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent();
                        openAlbumIntent.setAction(Intent.ACTION_PICK);
                        //Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    Toast.makeText(UserActivity.this, "pic", Toast.LENGTH_SHORT).show();
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上,并上传服务器（还未写）
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形
            mImage.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_img_change:
                showChoosePicDialog();
                break;
            case R.id.user_back_arrow:
                finish();
                break;
            case R.id.user_name_change:
                showChangeNameDialog();
                break;
            case R.id.user_phone_change:
                showChangePhoneDialog();
                break;
            case R.id.user_pw_change:
                showChangePwDialog();
                break;
        }
    }
}
