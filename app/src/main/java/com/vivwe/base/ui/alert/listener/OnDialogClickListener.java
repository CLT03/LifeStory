package com.vivwe.base.ui.alert.listener;

import android.view.View;

import com.vivwe.base.ui.alert.constant.AlertDialogTypeEnum;

/**
 * 弹出窗的按钮点击监听
 */
public interface OnDialogClickListener {

   /**
    * 点击事件
    * @param type 点击类型
    * @param view 内容view(自定义才有用）
    */
   public void click(AlertDialogTypeEnum type, View view);
}