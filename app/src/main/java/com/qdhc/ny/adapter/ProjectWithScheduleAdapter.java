package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.Project;
import com.qdhc.ny.view.CircleProgressBar;

import java.util.List;

/**
 * 信息列表
 */
public class ProjectWithScheduleAdapter extends BaseQuickAdapter<Project, BaseViewHolder> {
    Activity mContext;

    public ProjectWithScheduleAdapter(Activity mContext, @Nullable List<Project> data) {
        super(R.layout.item_project_with_schedule, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Project item) {
        helper.setText(R.id.tv_title, item.getName());

        CircleProgressBar circleBar = helper.getView(R.id.circleProgressBar);
//        circleBar.update(item.getProcess(), item.getProcess() + "%");
        helper.setText(R.id.tv_introduce, item.getInfo());
//        helper.setText(R.id.tv_village, item.getVillage());
//        helper.setText(R.id.tv_district, item.getDistrict());

        helper.setText(R.id.tv_tags, TextUtils.isEmpty(item.getTags()) ? "无" : item.getTags());

//        if (!userInfo.getObjectId().equals(item.getManager())) {
//            helper.setVisible(R.id.tv_person, true);
//            UserInfoUtils.getInfoByObjectId(item.getManager(), new UserInfoUtils.IResult() {
//                @Override
//                public void onReslt(UserInfo userInfo) {
//                    if (userInfo != null)
//                        helper.setText(R.id.tv_person, "负责人:" + userInfo.getNickName());
//                }
//            });
//        } else {
//            helper.setVisible(R.id.tv_person, false);
//        }

        helper.setText(R.id.tv_from, "创建时间: " + item.getCreateTime());

    }

}
