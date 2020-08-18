package com.qdhc.ny.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.activity.ProjectInfoActivity;
import com.qdhc.ny.activity.ReportAllListActivity;
import com.qdhc.ny.entity.Project;

import java.util.List;

/**
 * 信息列表
 */
public class ProjectWithReportAndScheduleAdapter extends BaseQuickAdapter<Project, BaseViewHolder> {
    Activity mContext;


    public ProjectWithReportAndScheduleAdapter(Activity mContext, @Nullable List<Project> data) {
        super(R.layout.item_project_report_schedule, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Project item) {
        helper.setText(R.id.tv_title, item.getName());

        helper.setText(R.id.tv_introduce, item.getInfo());
//        helper.setText(R.id.tv_village, item.getVillage());
//        helper.setText(R.id.tv_district, item.getDistrict());

//        CircleProgressBar circleBar = helper.getView(R.id.circleProgressBar);
//        circleBar.update(item.getProcess(), item.getProcess() + "%");

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
        helper.setText(R.id.tv_tags, TextUtils.isEmpty(item.getTags()) ? "无" : item.getTags());
//        helper.setText(R.id.tv_from, "创建时间: " + item.e().substring(0, 10));

        helper.setOnClickListener(R.id.tv_schedule, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProjectInfoActivity.class);
                intent.putExtra("info", item);

                mContext.startActivity(intent);
            }
        });
        helper.setOnClickListener(R.id.tv_report, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReportAllListActivity.class);
//                intent.putExtra("user", userInfo);
                intent.putExtra("project", item);
                mContext.startActivity(intent);
            }
        });


    }

}
