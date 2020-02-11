package com.qdhc.ny.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.qdhc.ny.R;
import com.vondear.rxui.view.dialog.RxDialog;


/**
 * 选择之前的时间
 * Created by 申健 on 2019/3/23.
 */

public class RxDialogWheelBank extends RxDialog {


    public RxDialogWheelBank(Context mContext) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
    }
    private void build() {
        final View dialogView1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_wheel_bank, null);

    //Wheelview wheelView = dialogView1.findViewById(R.id.wheelview);
//        wheelView.setCyclic(false);
//
//        final List<String> mOptionsItems = new ArrayList<>();
//        mOptionsItems.add("item0");
//        mOptionsItems.add("item1");
//        mOptionsItems.add("item2");
//
//        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
//        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                Toast.makeText(MainActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}