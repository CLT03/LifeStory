package com.vivwe.base.ui.alert;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vivwe.base.ui.alert.adapter.NormalSpinerAdapter;
import com.vivwe.main.R;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {

//	private Context mContext;

	@BindView(R.id.lv_content)
	ListView mListView;
	private NormalSpinerAdapter mAdapter;
	private NormalSpinerAdapter.IOnItemSelectListener mItemSelectListener;
	
	private View contentView;

	public SpinerPopWindow(Context context) {
		super(context);
		contentView = LayoutInflater.from(context).inflate(R.layout.zl_dialog_spiner, null);
		ButterKnife.bind(this, contentView);
		init(context);
	}

	public void setItemListener(NormalSpinerAdapter.IOnItemSelectListener listener) {
		mItemSelectListener = listener;
	}

	private void init(Context context) {

		setContentView(contentView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);

		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		mAdapter = new NormalSpinerAdapter(context);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}
	
	public SpinerPopWindow setDatas(List<String> list, List<Integer> icons) {
		mAdapter.setDatas(list, icons);
		return this;
	}

	public SpinerPopWindow setDatas(String[] strs, Integer[] icons) {

		List<String> strLists = new ArrayList<>();
		for (String str: strs){
			strLists.add(str);
		}

		List<Integer> iconLists = new ArrayList<>();
		for (Integer icon: icons){
			iconLists.add(icon);
		}

		mAdapter.setDatas(strLists, iconLists);
		return this;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null) {
			mItemSelectListener.onItemClick(pos);
		}
	}

}
