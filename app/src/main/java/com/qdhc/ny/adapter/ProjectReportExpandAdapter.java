package com.qdhc.ny.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdhc.ny.R;
import com.qdhc.ny.entity.DailyReport;
import com.qdhc.ny.entity.ProjectWithReport;

import java.util.List;

public class ProjectReportExpandAdapter extends BaseExpandableListAdapter {

    Context context;
    List<ProjectWithReport> projectWithReports;

    public ProjectReportExpandAdapter(Context context, List<ProjectWithReport> projectWithReports) {
        this.context = context;
        this.projectWithReports = projectWithReports;
    }

    @Override
    public int getGroupCount() {
        return projectWithReports == null ? 0 : projectWithReports.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (projectWithReports.size() > groupPosition) {
            List<DailyReport> reports = projectWithReports.get(groupPosition).getReports();
            return reports == null ? 0 : reports.size();
        } else {
            return 0;
        }
    }

    @Override
    public ProjectWithReport getGroup(int groupPosition) {
        return projectWithReports.get(groupPosition);
    }

    @Override
    public DailyReport getChild(int groupPosition, int childPosition) {
        return projectWithReports.get(groupPosition).getReports().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_report_group, null);
        }
        TextView titleTv = convertView.findViewById(R.id.id_child_tv);
        ProjectWithReport withReport = getGroup(groupPosition);
        titleTv.setText(withReport.getName());
        ImageView arrowIv = convertView.findViewById(R.id.arrow_iv);
        if (isExpanded) {
            arrowIv.setImageResource(R.drawable.ic_down_circle);
        } else {
            arrowIv.setImageResource(R.drawable.ic_right_circle);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_report_child, null);
        }
        ImageView iv = convertView.findViewById(R.id.iv_icon);
        TextView titleTv = convertView.findViewById(R.id.id_child_tv);
        TextView timeTv = convertView.findViewById(R.id.name_child_tv);
        DailyReport report = getChild(groupPosition, childPosition);
        titleTv.setText(report.getTitle());
        try {
            timeTv.setText(report.getCreateTime().substring(0, 10));
        } catch (Exception e) {
        }
        switch (report.getCheck()) {
            case 0:
                iv.setImageResource(R.drawable.ic_report_black);
                timeTv.setTextColor(context.getResources().getColor(R.color.light_black));
                titleTv.setTextColor(context.getResources().getColor(R.color.light_black));
                break;
            case 1:
                iv.setImageResource(R.drawable.ic_report_orange);
                timeTv.setTextColor(context.getResources().getColor(R.color.text_color_orange));
                titleTv.setTextColor(context.getResources().getColor(R.color.text_color_orange));
                break;
            case 2:
                iv.setImageResource(R.drawable.ic_report_red);
                timeTv.setTextColor(context.getResources().getColor(R.color.text_color_red));
                titleTv.setTextColor(context.getResources().getColor(R.color.text_color_red));
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
