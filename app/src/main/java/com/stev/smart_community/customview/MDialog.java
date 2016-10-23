package com.stev.smart_community.customview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stev.smart_community.R;

public class MDialog extends Dialog {
	private static final String TAG = "MDialog";
	private Context context;

	public MDialog(Context context) {
		super(context);
		this.context=context;
	}


}
