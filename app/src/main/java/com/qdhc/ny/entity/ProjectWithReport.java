package com.qdhc.ny.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 带有日报列表的项目对象
 */
public class ProjectWithReport extends Project implements Serializable {

    private List<DailyReport> reports = null;

    public List<DailyReport> getReports() {
        return reports;
    }

    public void setReports(List<DailyReport> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        return "ProjectWithReport{" +
                "reports=" + reports +
                '}';
    }
}
