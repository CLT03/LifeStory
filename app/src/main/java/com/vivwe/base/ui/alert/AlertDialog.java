package com.vivwe.base.ui.alert;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vivwe.base.ui.alert.constant.AlertDialogEnum;
import com.vivwe.base.ui.alert.constant.AlertDialogTypeEnum;
import com.vivwe.base.ui.alert.listener.OnDialogClickListener;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 一个自定义的弹出框
 * 1、支持按钮选择性展示（比如修改按钮展示名称，只展示部分按钮）
 * 2、支持修改标题，修改展示内容
 * 3、支持设置弹出框样式（提示模式，编辑模式，自定义模式）
 */
public class AlertDialog extends Dialog implements View.OnClickListener {

	private Context context;

	@BindView(R.id.cl_content)
    ConstraintLayout contentCl;

	@BindView(R.id.v_content)
    View contentV;

	@BindView(R.id.tv_ok)
	public TextView okBtn;
	@BindView(R.id.tv_cancel)
	public TextView cancelBtn;
	@BindView(R.id.tv_title)
	TextView titleTv;
	@BindView(R.id.tv_message)
	TextView messageTv;

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
    public static AlertDialog createTip(Context context, AlertDialogEnum type, String title, String message){

        AlertDialog alertDialog = new AlertDialog(context);

        alertDialog.setContentView(R.layout.zl_dialog_alert);
        ButterKnife.bind(alertDialog);

        alertDialog.setType(type);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        return alertDialog;
    }

    /**
     * 创建一个自定义布局
     * @param context 上下文
     * @return
     */
    public static AlertDialog createCustom(Context context, int layoutId){
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setContentView(layoutId);
        alertDialog.setType(AlertDialogEnum.CUSTOM);
        return alertDialog;
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
        return this;
    }

    public void setType(AlertDialogEnum type) {
        this.type = type;
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
		this.setCanceledOnTouchOutside(false);
	}


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
			messageTv.setText(message);
			break;
		case CUSTOM:
			break;
		}
		
		messageTv.setText(message);
	}


    @OnClick({R.id.tv_ok, R.id.tv_cancel})
	@Override
	public void onClick(View view) {

        dismiss();

        int i = view.getId();
        if (i == R.id.tv_ok) {
            listener.click(AlertDialogTypeEnum.OK, view);
        } else if (i == R.id.tv_cancel) {
            listener.click(AlertDialogTypeEnum.CANCEL, view);
        }
	}
}
