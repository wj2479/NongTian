package com.qdhc.ny.bean;

import com.qdhc.ny.bmob.Area_Region;
import com.qdhc.ny.bmob.Project;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wj
 * @Date 2020/1/6
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class RegionProject implements Serializable {

    private Area_Region region;

    private List<Project> projectList;

    private int nowProcess = 0;

    public Area_Region getRegion() {
        return region;
    }

    public void setRegion(Area_Region region) {
        this.region = region;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public int getNowProcess() {
        return nowProcess;
    }

    public void setNowProcess(int nowProcess) {
        this.nowProcess = nowProcess;
    }
}
