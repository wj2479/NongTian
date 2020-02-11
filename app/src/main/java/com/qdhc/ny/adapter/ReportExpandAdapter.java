package com.qdhc.ny.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Project;
import com.qdhc.ny.bmob.Report;
import com.qdhc.ny.common.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author wj
 * @Date 2019/11/12
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ReportExpandAdapter extends BaseExpandableListAdapter {

    // 子选项最大的数量
    private final int MAX_CHILD_COUNT = 7;
    Context context;

    List<Project> projects;
    int type;

    private LayoutInflater inflater;

    OnViewButtonClickListener onViewButtonClickListener;

    public ReportExpandAdapter(Context context, List<Project> projects, int type) {
        this.context = context;
        this.projects = projects;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    public void setOnViewButtonClickListener(OnViewButtonClickListener onViewButtonClickListener) {
        this.onViewButtonClickListener = onViewButtonClickListener;
    }

    @Override
    public int getGroupCount() {
        return projects == null ? 0 : projects.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        switch (type) {
            case Constant.REPORT_TYPE_DAY:
//                count = projects.get(groupPosition).getDayRreports().size();
//                count = Math.min(count, MAX_CHILD_COUNT);
                break;
            case Constant.REPORT_TYPE_WEEK:
//                count = projects.get(groupPosition).getWeekRreports().size();
                break;
            case Constant.REPORT_TYPE_MONTH:
//                count = projects.get(groupPosition).getMonthRreports().size();
                break;
            default:
        }
        return count;
    }

    @Override
    public Project getGroup(int groupPosition) {
        return projects == null ? null : projects.get(groupPosition);
    }

    @Override
    public Report getChild(int groupPosition, int childPosition) {
        Report report = null;
        switch (type) {
            case Constant.REPORT_TYPE_DAY:
//                report = projects.get(groupPosition).getDayRreports().get(childPosition);
                break;
            case Constant.REPORT_TYPE_WEEK:
//                report = projects.get(groupPosition).getWeekRreports().get(childPosition);
                break;
            case Constant.REPORT_TYPE_MONTH:
//                report = projects.get(groupPosition).getMonthRreports().get(childPosition);
                break;
            default:
        }
        return report;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //加载组item的视图，isExpanded表示是否处于展开状态
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_parent_report, parent, false);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.parentTextView = convertView.findViewById(R.id.id_parent_tv);
            parentViewHolder.imageView = convertView.findViewById(R.id.arrowIv);
            parentViewHolder.addIv = convertView.findViewById(R.id.addIv);
            parentViewHolder.moreIv = convertView.findViewById(R.id.moreIv);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        Project project = projects.get(groupPosition);
        parentViewHolder.parentTextView.setText(project.getName());
        parentViewHolder.imageView.setImageResource(R.drawable.group_indicator);
        //设置图标是否展开
        parentViewHolder.imageView.setSelected(isExpanded);
        parentViewHolder.addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewButtonClickListener != null) {
                    onViewButtonClickListener.onAddButtonClick(groupPosition);
                }
            }
        });

        parentViewHolder.moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewButtonClickListener != null) {
                    onViewButtonClickListener.onMoreButtonClick(groupPosition);
                }
            }
        });

        return convertView;
    }

    //加载子item的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child_report, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.childTextView = convertView.findViewById(R.id.id_child_tv);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        Report report = null;
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

//        switch (type) {
//            case Constant.REPORT_TYPE_DAY:
//                report = projects.get(groupPosition).getDayRreports().get(childPosition);
//                String dayTime = report.getCreatedAt().substring(0, 10);
//                childViewHolder.childTextView.setText("日报 :  " + dayTime);
//                break;
//            case Constant.REPORT_TYPE_WEEK:
//                report = projects.get(groupPosition).getWeekRreports().get(childPosition);
//                try {
//                    date = dateFormat.parse(report.getCreatedAt());
//                } catch (ParseException e) {
//                }
//                calendar.setTime(date);
//
//                childViewHolder.childTextView.setText("周报 :  " + (calendar.get(Calendar.MONTH) + 1) + "月  第" + calendar.get(Calendar.WEEK_OF_MONTH) + "周");
//                break;
//            case Constant.REPORT_TYPE_MONTH:
//                report = projects.get(groupPosition).getMonthRreports().get(childPosition);
//                try {
//                    date = dateFormat.parse(report.getCreatedAt());
//                } catch (ParseException e) {
//                }
//                calendar.setTime(date);
//
//                childViewHolder.childTextView.setText("月报 :  " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
//                break;
//            default:
//        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class ParentViewHolder {
        TextView parentTextView;
        ImageView imageView;

        ImageView addIv, moreIv;
    }

    public static class ChildViewHolder {
        TextView childTextView;
    }

    public interface OnViewButtonClickListener {
        void onAddButtonClick(int groupPosition);

        void onMoreButtonClick(int groupPosition);
    }


}
