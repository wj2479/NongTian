package com.sj.core.ui;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sj.core.R;


/**
 * 自定义dialog
 * 去掉黑边
 * 填充屏幕
 * @author liminmin
 *
 */
public abstract class BottomDialog extends Dialog {
	private View view;
	protected Activity context;
	private int gravity= Gravity.BOTTOM;//默认底部

	public BottomDialog(Activity context) {
		super(context, R.style.bottom_dialog);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	public BottomDialog(Activity context, int gravity) {
		super(context, R.style.bottom_dialog);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.gravity=gravity;
		init();
		
	}
	public BottomDialog(FragmentActivity context, int gravity) {
		super(context,R.style.bottom_dialog);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.gravity=gravity;
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		Window window = this.getWindow();
	    window.setGravity(gravity);  //此处可以设置dialog显示的位置  
	    window.setWindowAnimations(R.style.AnimUp);  //添加动画  
        
        setContentView(getView());
        /*
         * 设置填满屏幕
         */
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        this.getWindow().setAttributes(lp);    
	      
	}
	
	public abstract View getView();
	
	
	
	
}
