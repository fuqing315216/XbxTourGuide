package com.xbx.tourguide.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

import java.io.File;
import java.util.Date;

/**
 * Created by shuzhen on 2016/3/30.
 */
public class CameraDialogActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout dialogRlyt;
    private TextView cameraTv, photoTv, cancelTv;
    private boolean isPic;//是否选择上传图片
    private boolean isCrop;//是否需要剪切

    /**
     * 调用系统拍照、图片浏览、图片裁剪状态
     */
    private static final int TAKE_CAMERA = 1;
    private static final int FETCH_PHOTO = 2;
    private static final int CROP_PHOTO = 3;
    /**
     * 图片默认保存路径
     */
    private File mFile = null;
    /**
     * 返回图片的URI
     */
    private Uri mUri = null;

    private Uri getPictureUri() {
        return mUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_dialog);
        isPic = getIntent().getBooleanExtra("isPic", true);
        isCrop=getIntent().getBooleanExtra("isCrop",false);
        Date date = new Date();
        mFile = getExternalFilesDir("");
        String name = date.getTime() + "";
        if (null != mFile && mFile.exists()) {
            mFile = new File(mFile, name + "xbx.jpg");
        }

        initView();
    }

    private void initView() {
        dialogRlyt = (RelativeLayout) findViewById(R.id.rlyt_dialog);
        cameraTv = (TextView) findViewById(R.id.tv_camera);
        photoTv = (TextView) findViewById(R.id.tv_photo);
        cancelTv = (TextView) findViewById(R.id.tv_cancel);

        if (!isPic) {
            cameraTv.setText(getResources().getString(R.string.guide));
            photoTv.setText(getResources().getString(R.string.leader));
        }

        cameraTv.setOnClickListener(this);
        photoTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        dialogRlyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:

                if (!isPic) {
                    Intent intent = new Intent();
                    intent.putExtra("type", cameraTv.getText().toString());
                    setResult(101, intent);
                    finish();
                } else {
                    takePicture();
                }
                break;

            case R.id.tv_photo:
                if (!isPic) {
                    Intent intent = new Intent();
                    intent.putExtra("type", photoTv.getText().toString());
                    setResult(101, intent);
                    finish();
                } else {

                    getPicture();
                }
                break;

            default:
                finish();
                break;
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /***
         * 以下程序是采用相机拍照，拍照后的图片存放在相册中，采用ContentValues方式保存的是拍照后的原图， 其它方式会不太清晰
         */
        ContentValues values = new ContentValues();
        try {
            mUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (isCrop) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                Intent intentPic = new Intent();
                intentPic.putExtra("url", mFile);
                setResult(100, intentPic);
//                mCallBack.handleResult(mFile);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri.fromFile(mFile));
            }
            startActivityForResult(intent, TAKE_CAMERA);
        } catch (Exception ex) {
            // CommonUtils.showToast(mContext, "启动相机异常");
        }
    }

    /**
     * 从相册中选择图片
     */
    private File sdcardTempFile;

    private void getPicture() {
        Date date = new Date();
        sdcardTempFile = getExternalFilesDir("");
        String name = date.getTime() + "";
        if (null != sdcardTempFile && sdcardTempFile.exists()) {
            sdcardTempFile = new File(sdcardTempFile, name + ".jpg");
        }


        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        startActivityForResult(intent, FETCH_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            Uri photoUri = null;
            switch (requestCode) {
                case TAKE_CAMERA:
                    if (isCrop) {
                        photoUri = getPictureUri();
                        cropImageUri(photoUri, mFile);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("url", mFile.getAbsolutePath());
                        setResult(100, intent);
                        finish();
//                        mCallBack.handleResult(mFile);
                    }
                    break;
                case FETCH_PHOTO:
                    if (data != null && data.getData() != null) {
                        if (isCrop) {
                            cropImageUri(data.getData(), sdcardTempFile);
                        } else {
                            sendPicByUri(data.getData());
                        }
                    }

                    break;
                case CROP_PHOTO:
                    if (sdcardTempFile != null) {
                        Intent intent = new Intent();
                        intent.putExtra("url", sdcardTempFile.getAbsolutePath());
                        setResult(100, intent);
                        finish();
//                        mCallBack.handleResult(sdcardTempFile);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("url", mFile.getAbsolutePath());
                        setResult(100, intent);
                        finish();
//                        mCallBack.handleResult(mFile);
                    }

                    break;
            }
        }
    }

    /**
     * 不剪切图片
     *
     * @param selectedImage
     */
    private void sendPicByUri(Uri selectedImage) {
        Cursor cursor = getContentResolver().query(selectedImage,
                null, null, null, null);
        String st8 = "找不到图片";
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sdcardTempFile = new File(picturePath);
            Intent intent = new Intent();
            intent.putExtra("url", sdcardTempFile.getAbsolutePath());
            setResult(100, intent);
            finish();

//            mCallBack.handleResult(sdcardTempFile);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            Intent intent = new Intent();
            intent.putExtra("url", file.getAbsolutePath());
            setResult(100, intent);
            finish();
//            mCallBack.handleResult(file);
        }

    }

    /**
     * 传递裁剪参数
     *
     * @param uri 图片URI
     */
    public void cropImageUri(Uri uri, File file) {
        if (null == file) {
            // CommonUtils.showToast(mContext, "启动相机异常");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");
        // 裁切信号
        intent.putExtra("crop", "true");

        // 裁切框 的宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

         // 裁切后图片的尺寸
         intent.putExtra("outputX", 192);
         intent.putExtra("outputY", 192);

        // 自动缩放
        intent.putExtra("scale", true);
        // 裁切后图片的输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用面部识别
        intent.putExtra("noFaceDetection", true);

        // 若"return-data"为false,则须要设置输出目录，裁切好的图片会存储在指定的目录中而不会从Intent返回
        // 若"return-data"为true,则不必设置输出目录，图片会在Intent中返回，通过(Bitmap)
        // intent.getExtras().get("data")获取
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(intent, CROP_PHOTO);
    }
}
