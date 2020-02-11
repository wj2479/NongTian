package com.qdhc.ny.common;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.qdhc.ny.bean.UserNode;
import com.qdhc.ny.bean.UserTreeNode;
import com.qdhc.ny.bmob.Area_Region;
import com.qdhc.ny.bmob.Notice;
import com.qdhc.ny.bmob.Project;
import com.qdhc.ny.bmob.UserInfo;
import com.qdhc.ny.bmob.Villages;
import com.qdhc.ny.entity.User;
import com.qdhc.ny.eventbus.RegionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @Author wj
 * @Date 2019/11/13
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ProjectData {

    /**
     * 当前用户
     */
    private User userInfo = null;

    /**
     * 当前工程的列表
     */
    private List<Project> projects = new ArrayList<>();

    /**
     * 当前定位的位置
     */
    private AMapLocation location = null;

    /**
     * 公告列表
     */
    private List<Notice> notices = new ArrayList<>();

    /**
     * 监理列表
     */
    private List<UserInfo> userInfos = new ArrayList<>();

    /**
     * 所有乡镇的列表
     */
    private List<Villages> villages = new ArrayList<>();

    /**
     * 乡镇ID 与乡镇对应表
     */
    private Map<String, Villages> villageMap = new HashMap<>();
    /**
     * 所有乡镇与用户的对应
     */
    private Map<String, List<UserInfo>> villageUserMap = new HashMap<>();

    /**
     * 地区，人员总节点
     */
    private UserTreeNode rootNode = new UserTreeNode();

    private static ProjectData mPorjectData = null;

    /**
     * 当前区域
     */
    private Area_Region myRegion = null;

    /**
     * 当前区域的下级区域
     */
    private List<Area_Region> subRegion = new ArrayList<>();

    private ProjectData() {
    }

    public static ProjectData getInstance() {
        if (mPorjectData == null) {
            synchronized (ProjectData.class) {
                if (mPorjectData == null)
                    mPorjectData = new ProjectData();
            }
        }
        return mPorjectData;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public AMapLocation getLocation() {
        return location;
    }

    public void setLocation(AMapLocation location) {
        this.location = location;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public List<Villages> getVillages() {
        return villages;
    }

    public void setVillages(List<Villages> villages) {
        this.villages = villages;
    }

    public Map<String, Villages> getVillageMap() {
        return villageMap;
    }

    public void setVillageMap(Map<String, Villages> villageMap) {
        this.villageMap = villageMap;
    }

    public Map<String, List<UserInfo>> getVillageUserMap() {
        return villageUserMap;
    }

    public void setVillageUserMap(Map<String, List<UserInfo>> villageUserMap) {
        this.villageUserMap = villageUserMap;
    }

    public UserTreeNode getRootNode() {
        return rootNode;
    }

    public Area_Region getMyRegion() {
        return myRegion;
    }

    public List<Area_Region> getSubRegion() {
        return subRegion;
    }

    /**
     * 初始化地区跟用户的对照表
     * 程序启动时只需要更新一次
     */
//    public void initUserData() {
//        BmobQuery<UserInfo> categoryBmobQuery = new BmobQuery();
//        categoryBmobQuery.addWhereEqualTo("city", userInfo.getCity());
//        switch (userInfo.getRole()) {
//            case 3:      // 市级领导
//                categoryBmobQuery.addWhereNotEqualTo("role", 3);
//                break;
//            case 2:     // 区县领导
//                categoryBmobQuery.addWhereEqualTo("county", userInfo.getCounty());
//                categoryBmobQuery.addWhereNotEqualTo("role", 3);
//                categoryBmobQuery.addWhereNotEqualTo("role", 2);
//                break;
//            case 4:       // 乡镇领导
//                categoryBmobQuery.addWhereEqualTo("county", userInfo.getCounty());
//                categoryBmobQuery.addWhereEqualTo("village", userInfo.getVillage());
//                categoryBmobQuery.addWhereEqualTo("role", 1);
//                break;
//        }
//        categoryBmobQuery.setLimit(500);
//        categoryBmobQuery.findObjects(new FindListener<UserInfo>() {
//            @Override
//            public void done(List<UserInfo> list, BmobException e) {
//                if (e == null) {
//                    if (list != null) {
//                        userInfos.addAll(list);
//                        Log.e("监理人员数量-----》", list.size() + "");
//                        getAreaData();
//                    }
//                } else {
//                }
//            }
//        });
//    }

    /**
     * 获取当前地区列表
     */
//    private void getAreaData() {
//        BmobQuery<Area_Region> categoryBmobQuery = new BmobQuery();
//        String objectId = "";
//
//        switch (userInfo.getRole()) {
//            case 3:      // 市级领导
//                objectId = userInfo.getCity();
//                break;
//            case 2:     // 区县领导
//                objectId = userInfo.getCounty();
//                break;
//            case 4:       // 乡镇领导
//                objectId = userInfo.getVillage();
//                break;
//        }
//
//        categoryBmobQuery.getObject(objectId, new QueryListener<Area_Region>() {
//            @Override
//            public void done(Area_Region region, BmobException e) {
//                if (region != null) {
//                    myRegion = region;
//                    rootNode.setObjectId(region.getObjectId());
//                    rootNode.setName(region.getName());
//                    rootNode.setLevel(region.getRegion_level());
//                }
//            }
//        });
//        getChildArea(rootNode, objectId);
//    }

    int MAX_COUNT = 0;
    int count = 0;

    boolean isInitSubRegion = false;

    /**
     * 获取所包含的区域
     */
    private void getChildArea(final UserTreeNode rootNode, String objectId) {
        BmobQuery<Area_Region> bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("parent_id", objectId);
        bmobQuery.order("region_code");
        bmobQuery.findObjects(new FindListener<Area_Region>() {
            @Override
            public void done(List<Area_Region> list, BmobException e) {
                if (e == null && list != null) {
//                    Log.e("下级列表结果-----》", rootNode.getName() + "-->" + list.toString());
                    if (!isInitSubRegion) {
                        subRegion.addAll(list);
                        EventBus.getDefault().post(new RegionEvent());
                        isInitSubRegion = true;
                    }
                    if (rootNode.getLevel() <= 3) {     // 市级 区县级别
                        if (rootNode.getLevel() == 3) {// 说明到了区县级别
                            MAX_COUNT += list.size();
                        }

                        for (Area_Region region : list) {
                            UserTreeNode childNode = new UserTreeNode();
                            childNode.setObjectId(region.getObjectId());
                            childNode.setName(region.getName());
                            childNode.setLevel(region.getRegion_level());
                            childNode.setParent(rootNode);
                            rootNode.addChild(childNode);
                            getChildArea(childNode, region.getObjectId());
                        }

                    } else if (rootNode.getLevel() == 4) {      // 乡镇级别
                        // 添加乡镇下级 子节点
                        UserTreeNode childNode = new UserTreeNode();
                        childNode.setParent(rootNode);
                        rootNode.addChild(childNode);
//                        Log.e("下级列表结果-----》", "乡镇--》" + rootNode);
                        count++;
                        if (MAX_COUNT == count) {
//                            Log.e("下级列表结果-----》", "结束");
                            initUser();
                        }
                    }
                } else {
                    Log.e("下级异常-----》", e.toString());
                }
            }
        });
    }

    int cut = 0;

    /**
     * 初始化用户
     */
    private void initUser() {
        for (UserInfo uInfo : userInfos) {
            switch (uInfo.getRole()) {
                case 1: {  // 监理账号
                    if (userInfo.getRole() == 3) {
                        for (UserTreeNode countNode : rootNode.getChilds()) { // 区县
                            if (countNode.getObjectId().equals(uInfo.getCounty())) {
                                for (UserTreeNode villageNode : countNode.getChilds()) {  // 乡镇
                                    if (villageNode.getObjectId().equals(uInfo.getVillage())) {
                                        UserNode userNode = new UserNode();
                                        userNode.setUserInfo(uInfo);
                                        villageNode.getChilds().get(0).addUser(userNode);
                                        cut += 1;
                                        Log.e("监理账号-----》", cut + "  " + countNode.getName() + "-->" + villageNode.getName() + "--> -->" + uInfo.toString());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else if (userInfo.getRole() == 2) {
                        for (UserTreeNode villageNode : rootNode.getChilds()) {  // 乡镇
                            if (villageNode.getObjectId().equals(uInfo.getVillage())) {
                                UserNode userNode = new UserNode();
                                userNode.setUserInfo(uInfo);
                                villageNode.getChilds().get(0).addUser(userNode);

                                Log.e("监理账号-----》", villageNode.getName() + "--> -->" + uInfo.toString());
                                break;
                            }
                        }
                    } else if (userInfo.getRole() == 4) {
                        UserNode userNode = new UserNode();
                        userNode.setUserInfo(uInfo);
                        rootNode.getChilds().get(0).addUser(userNode);
                    }
                }
                break;

                case 2: {    // 区县用户
                    if (userInfo.getRole() == 3) {    // 如果当前是市级用户
                        for (UserTreeNode countNode : rootNode.getChilds()) {
                            if (countNode.getObjectId().equals(uInfo.getCounty())) {
                                UserNode userNode = new UserNode();
                                userNode.setUserInfo(uInfo);
                                countNode.addUser(userNode);
                                cut += 1;
                                Log.e("区县账号-----》", cut + "  " + countNode.getName() + "-->" + uInfo.toString());
                                break;
                            }
                        }
                    } else if (userInfo.getRole() == 2) {
                        UserNode userNode = new UserNode();
                        userNode.setUserInfo(uInfo);
//                        rootNode.addUser(userNode);
                    }
                }
                break;
                case 3: {   // 市级用户  直接加到根节点
//                    if (userInfo.getRole() == 3) {    // 如果当前是市级用户
//                        UserNode userNode = new UserNode();
//                        userNode.setUserInfo(uInfo);
////                        rootNode.addUser(userNode);
////                        Log.e("市级账号-----》", uInfo.toString());
//                    }
                }
                break;

                case 4: {   // 乡镇用户
                    if (userInfo.getRole() == 3) {
                        for (UserTreeNode countNode : rootNode.getChilds()) { // 区县
                            if (countNode.getObjectId().equals(uInfo.getCounty())) {
                                for (UserTreeNode villageNode : countNode.getChilds()) {  // 乡镇
                                    if (villageNode.getObjectId().equals(uInfo.getVillage())) {
                                        UserNode userNode = new UserNode();
                                        userNode.setUserInfo(uInfo);
                                        villageNode.addUser(userNode);
                                        cut += 1;
                                        Log.e("乡镇账号-----》", cut + "  " + countNode.getName() + "-->" + villageNode.getName() + "-->" + uInfo.toString());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else if (userInfo.getRole() == 2) {
                        for (UserTreeNode villageNode : rootNode.getChilds()) {  // 乡镇
                            if (villageNode.getObjectId().equals(uInfo.getVillage())) {
                                UserNode userNode = new UserNode();
                                userNode.setUserInfo(uInfo);
                                villageNode.addUser(userNode);
                                Log.e("监理账号-----》", villageNode.getName() + "--> -->" + uInfo.toString());
                                break;
                            }
                        }
                    } else if (userInfo.getRole() == 4) {
                        UserNode userNode = new UserNode();
                        userNode.setUserInfo(uInfo);
                        rootNode.addUser(userNode);
                    }
                }
                break;
            }
        }
    }

    /**
     * 释放内存资源
     */
    public void release() {
        cut = 0;
        isInitSubRegion = false;
        userInfo = null;
        projects.clear();
        notices.clear();
        userInfos.clear();
        villages.clear();
        villageMap.clear();
        villageUserMap.clear();
        myRegion = null;
        subRegion.clear();
        rootNode.reset();
    }

}
