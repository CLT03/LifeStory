package com.vivwe.base.ui.alert;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lifestory.lifestory.R;
import com.vivwe.base.ui.alert.constant.AlertDialogEnum;
import com.vivwe.base.ui.alert.constant.AlertDialogTypeEnum;
import com.vivwe.base.ui.alert.listener.OnDialogClickListener;

/**
 * 一个自定义的弹出框
 * 1、支持按钮选择性展示（比如修改按钮展示名称，只展示部分按钮）
 * 2、支持修改标题，修改展示内容
 * 3、支持设置弹出框样式（提示模式，编辑模式，自定义模式）
 */
public class AlertDialog extends Dialog implements View.OnClickListener {

	private Context context;
	private Button okBtn, cancelBtn, otherBtn, opt2Btn;
	private TextView titleTv, messageTv;
	/** 内容 */
    private RelativeLayout contentRl;
	private EditText messageZedt;
	/** 弹窗类型，默认为提示 */
    private AlertDialogEnum type = AlertDialogEnum.TIP;
	private OnDialogClickListener listener;

    private AlertDialog(Context context) {
        super(context, R.style.ViewsDialog);
        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        initUI(context);
    }

    /**
     * 创建一个弹出框实例
     * @param context 上下文
     * @param type 类型
     * @param title 标题
     * @param message 显示内容
     * @return 弹出框实例
     */
    public static AlertDialog create(Context context, AlertDialogEnum type, String title, String message){

        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(type);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        return alertDialog;
    }

    /**
     * 创建一个弹出框实例
     * @param context 上下文
     * @param type 类型
     * @param message 显示内容
     * @return 弹出框实例
     */
    public static AlertDialog create(Context context, AlertDialogEnum type, String message){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(type);
        alertDialog.setTitle("系统提示");
        alertDialog.setMessage(message);
        return alertDialog;
    }

    /**
     * 创建一个提示框
     * @param context 上下文
     * @param title 标题
     * @param message 消息
     * @return
     */
    public static AlertDialog createTip(Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(AlertDialogEnum.TIP);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        return alertDialog;
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static AlertDialog createInput(Context context, String title, String message, Integer inputType){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(AlertDialogEnum.INPUT);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.messageZedt.setInputType(inputType);
        return alertDialog;
    }

    public static AlertDialog createInput(Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(AlertDialogEnum.INPUT);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        return alertDialog;
    }

    /**
     * 创建一个自定义布局
     * @param context 上下文
     * @param title 标题
     * @return
     */
    public static AlertDialog createCustom(Context context, String title, View customView){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setType(AlertDialogEnum.CUSTOM);
        alertDialog.setTitle(title);
        alertDialog.setCustomViw(customView);
        return alertDialog;
    }

    public AlertDialog setButton(String submitStr, String otherStr, String opt2Str, String cancelStr) {
        okBtn.setText(submitStr);
        cancelBtn.setText(cancelStr);
        otherBtn.setText(otherStr);
        opt2Btn.setText(opt2Str);
        okBtn.setVisibility(View.VISIBLE);
        cancelBtn.setVisibility(View.VISIBLE);
        otherBtn.setVisibility(View.VISIBLE);
        opt2Btn.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置按钮
     * @param submitStr 提交按钮名称
     * @param cancelStr 取消按钮名称
     * @param otherStr 其他按钮名称
     * @return 弹出框实例
     */
    public AlertDialog setButton(String submitStr, String otherStr, String cancelStr) {
        okBtn.setText(submitStr);
        cancelBtn.setText(cancelStr);
        otherBtn.setText(otherStr);
        okBtn.setVisibility(View.VISIBLE);
        cancelBtn.setVisibility(View.VISIBLE);
        otherBtn.setVisibility(View.VISIBLE);
        opt2Btn.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置按钮
     * @param submitStr 提交按钮名称
     * @param cancelStr 取消按钮名称
     * @return 弹出框实例
     */
    public AlertDialog setButton(String submitStr, String cancelStr) {
        okBtn.setText(submitStr);
        cancelBtn.setText(cancelStr);
        okBtn.setVisibility(View.VISIBLE);
        cancelBtn.setVisibility(View.VISIBLE);
        otherBtn.setVisibility(View.GONE);
        opt2Btn.setVisibility(View.GONE);
        return this;
    }

    public void setType(AlertDialogEnum type) {
        this.type = type;
    }

    /**
     * 设置自定义view
     * @param view
     * @return
     */
    public AlertDialog setCustomViw(View view){
        if(type == AlertDialogEnum.CUSTOM){
            contentRl = this.findViewById(R.id.rl_content);
            contentRl.removeAllViews();
            contentRl.addView(view);
        }
        return this;
    }

    /**
     * 设置按钮监听
     * @param listener 监听类
     * @return 本实例对象
     */
    public AlertDialog setOnClickListener(OnDialogClickListener listener) {
        this.listener = listener;
        return this;
    }

	/***
	 * @detail init UI
	 */
	private void initUI(Context context) {
		this.context = context;
		setContentView(R.layout.zl_dialog_alert);

		// find view
		okBtn = (Button) findViewById(R.id.btn_ok);
		cancelBtn = (Button) findViewById(R.id.btn_cancel);
		otherBtn = (Button) findViewById(R.id.btn_other);
		opt2Btn = (Button) findViewById(R.id.btn_opt2);

		titleTv = (TextView) findViewById(R.id.dialog_title_tv);
		messageTv = (TextView) findViewById(R.id.dialog_message_tv);
		messageZedt = (EditText) findViewById(R.id.dialog_message_zedt);

		// listener
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		otherBtn.setOnClickListener(this);
        opt2Btn.setOnClickListener(this);
		this.setCanceledOnTouchOutside(false);
	}

    /**
     * 获取编辑的内容
     * @return
     */
    public String getSimpleInput(){
        return messageZedt.getText().toString();
    }

    @Override
    public void show() {
        super.show();
    }

	//--------------------------------------------------------------
    /**
     * 设置弹出框标题
     * @param title 标题字符
     */
	private void setTitle(String title) {
		titleTv.setText(title);
	}

    /**
     * 设置弹出框类型
     * @param message 显示内容
     */
	private void setMessage(String message) {
		switch(type){
		case TIP:
			messageTv.setVisibility(View.VISIBLE);
			messageZedt.setVisibility(View.GONE);
			messageTv.setText(message);
			break;
		case INPUT:
			messageZedt.setVisibility(View.VISIBLE);
			messageTv.setVisibility(View.GONE);
			messageZedt.setText(message);
			break;
		case CUSTOM:
			break;
		}
		
		messageTv.setText(message);
	}

	@Override
	public void onClick(View view) {

        dismiss();

        View v = view;
        switch (type){
            case INPUT:
                v = messageZedt;
                break;
            case CUSTOM:
                v = contentRl;
                break;
        }

        int i = view.getId();
        if (i == R.id.btn_ok) {
            listener.click(AlertDialogTypeEnum.OK, v);
        } else if (i == R.id.btn_cancel) {
            listener.click(AlertDialogTypeEnum.CANCEL, v);
        } else if (i == R.id.btn_other) {
            listener.click(AlertDialogTypeEnum.OTHER, v);
        } else if(i == R.id.btn_opt2){
            listener.click(AlertDialogTypeEnum.OPT2, v);
        }


	}
}
