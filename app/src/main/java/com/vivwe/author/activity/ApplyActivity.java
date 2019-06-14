package com.vivwe.author.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.vivwe.author.api.AuthorApi;
import com.vivwe.author.entity.ApplyEntity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Loading;
import com.vivwe.faceunity.api.WebMainApi;
import com.vivwe.faceunity.entity.UploadToken;
import com.vivwe.main.R;

import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 申请为创作者
 */
public class ApplyActivity extends BaseActivity {


    @BindView(R.id.iv_upload_id_card)
    ImageView ivUploadIdCard;
    @BindView(R.id.iv_upload_id_card_back)
    ImageView ivUploadIdCardBack;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_mail)
    EditText edtMail;
    @BindView(R.id.edt_id_card)
    EditText edtIdCard;
    @BindView(R.id.edt_bankcard)
    EditText edtBankCard;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String[] idCardPaths = new String[2];//本地身份图片路径
    private String[] idCardServicePaths = new String[2];//服务器身份图片路径
    private int number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_apply);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.iv_upload_id_card, R.id.iv_upload_id_card_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_upload_id_card:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_upload_id_card_back:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 2);
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (requestCode == 1) {
                Glide.with(this).load(data.getData()).into(ivUploadIdCard);
                idCardPaths[0] = getFilePathFromContentUri(data.getData(), getContentResolver());
            }
            if (requestCode == 2) {
                Glide.with(this).load(data.getData()).into(ivUploadIdCardBack);
                idCardPaths[1] = getFilePathFromContentUri(data.getData(), getContentResolver());
            }
            Log.e("ououou", data.getData().toString() + " " + getFilePathFromContentUri(data.getData(), getContentResolver()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void submit() {
        if (check("^[\u4e00-\u9fa5]{2,4}$", edtName.getText().toString())) {
            Toast.makeText(this, "请输入正确的姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (check("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", edtPhone.getText().toString())) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (check("^\\d{15}|\\d{18}$", edtIdCard.getText().toString())) {
            Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (check("^\\d{19}$", edtBankCard.getText().toString())) {
            Toast.makeText(this, "请输入正确的银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (check("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", edtMail.getText().toString())) {
            Toast.makeText(this, "请输入正确的邮箱账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (idCardPaths[0] == null || idCardPaths[1] == null) {
            Toast.makeText(this, "请选择身份证照片", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadImage();
    }


    private void uploadImage() {
        Loading.start(this, "正在提交，请稍后...", false);
        btnSubmit.setClickable(false);
        for (int i = 0; i < 2; i++) {
            final int tag = i;
            HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi.class).getToken(), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        //获取上传七牛云的token
                        UploadToken token = webMsg.getData(UploadToken.class);
                        idCardServicePaths[tag] = token.getKey();
                        //使用token上图片到七牛云
                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(new File(idCardPaths[tag]), token.getKey(), token.getToken(), new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        if (info.isOK()) {
                                            number++;
                                            if (number == 2) {//两个文件都上传成功了
                                                apply();
                                            }
                                        } else {
                                            Loading.stop();
                                            btnSubmit.setClickable(true);
                                            Toast.makeText(ApplyActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, null
                        );
                    } else if (webMsg.netIsSuccessed()) {
                        Loading.stop();
                        btnSubmit.setClickable(true);
                        Toast.makeText(ApplyActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void apply() {
        ApplyEntity applyEntity=new ApplyEntity();
        applyEntity.setBankCardNumber(edtBankCard.getText().toString());
        applyEntity.setEmail(edtMail.getText().toString());
        applyEntity.setIdCardBack(idCardServicePaths[1]);
        applyEntity.setIdCardFront(idCardServicePaths[0]);
        applyEntity.setName(edtName.getText().toString());
        applyEntity.setPhoneNumber(edtPhone.getText().toString());
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).applyCreator(applyEntity), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    Loading.stop();
                    btnSubmit.setClickable(true);
                    Toast.makeText(ApplyActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                    finish();
                } else if (webMsg.netIsSuccessed()) {
                    Loading.stop();
                    btnSubmit.setClickable(true);
                    Toast.makeText(ApplyActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public static String getFilePathFromContentUri(Uri selectedVideoUri, ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        //public final Cursor query (Uri uri, String[] projection,String selection,String[] selectionArgs, StringsortOrder)
        //第二个参数：查询的列；第三四个参数；查询条件；第五个参数：排序
        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }


    public boolean check(String redex, String content) {
        Pattern p1 = Pattern.compile(redex);
        Matcher m1 = p1.matcher(content);
        return !m1.matches();
    }
}
