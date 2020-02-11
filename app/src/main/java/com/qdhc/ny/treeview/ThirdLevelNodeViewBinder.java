package com.qdhc.ny.treeview;

import android.view.View;
import android.widget.TextView;

import com.qdhc.ny.R;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * @Author wj
 * @Date 2019/11/21
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ThirdLevelNodeViewBinder extends CheckableNodeViewBinder {
    TextView textView;

    public ThirdLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_third_level;
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
    }
}
