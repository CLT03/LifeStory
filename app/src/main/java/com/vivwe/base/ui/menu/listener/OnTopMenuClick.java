package com.vivwe.base.ui.menu.listener;

import android.view.View;

import com.vivwe.base.ui.menu.constant.TopMenuTypeEnum;

/**
 * ahtor: super_link
 * date: 2019/4/10 18:22
 * remark:
 */
public interface OnTopMenuClick {
    /** 菜单按钮点击事件 */
    public void onClick(TopMenuTypeEnum type, View view);
}
