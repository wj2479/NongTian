package com.qdhc.ny.utils;

import android.util.Log;

import com.qdhc.ny.entity.SelectUser;
import com.qdhc.ny.entity.UserAreaTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wj
 * @Date 2020/1/5
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class TreeNodeHelper {

    /**
     * 获取子节点的数量
     *
     * @param treeNode
     * @return
     */
    public static int getChildCount(UserAreaTreeNode treeNode) {
        int count = 0;
        if (treeNode.getArea().getRegionLevel() == 2) {
            for (UserAreaTreeNode child : treeNode.getChilds()) {   // 区县
                count += child.getUserInfos().size();
                for (UserAreaTreeNode childChild : child.getChilds()) {   // 乡镇
                    int n = childChild.getUserInfos().size();
                    count += n;
                    if (childChild.getChilds().size() > 0) {
                        int num = childChild.getChilds().get(0).getUserInfos().size();
                        count += num;
                    }
                }
            }

        } else if (treeNode.getArea().getRegionLevel() == 3) {
            count += treeNode.getUserInfos().size();
            for (UserAreaTreeNode child : treeNode.getChilds()) {   // 乡镇
                int n = child.getUserInfos().size();
                count += n;
                if (child.getChilds().size() > 0) {
                    int num = child.getChilds().get(0).getUserInfos().size();
                    count += num;
                }
            }
        } else if (treeNode.getArea().getRegionLevel() == 4) {
            count += treeNode.getUserInfos().size();
            if (treeNode.getChilds().size() > 0) {
                int num = treeNode.getChilds().get(0).getUserInfos().size();
                count += num;
            }
        }
        return count;
    }

    /**
     * 获取当前节点下所选择的子节点列表
     *
     * @param treeNode
     * @return
     */
    public static List<SelectUser> getSelectedNodes(UserAreaTreeNode treeNode) {
        List<SelectUser> selectedNodes = new ArrayList<>();
        if (treeNode == null) {
            return selectedNodes;
        }

        // 首先将当前节点的子用户全部选择上
        for (SelectUser userInfo : treeNode.getUserInfos()) {
            if (userInfo.isSelected())
                selectedNodes.add(userInfo);
        }

        if (treeNode.getArea().getRegionLevel() == 4) {
            // 如果没有节点就代表是最后一层  将所有的监理取出来
            if (treeNode.getChilds() != null && treeNode.getChilds().size() > 0) {
                for (SelectUser userInfo : treeNode.getChilds().get(0).getUserInfos()) {
                    if (userInfo.isSelected())
                        selectedNodes.add(userInfo);
                }
            }
            return selectedNodes;
        }

        for (UserAreaTreeNode child : treeNode.getChilds()) {
            selectedNodes.addAll(getSelectedNodes(child));
        }
        return selectedNodes;
    }

    /**
     * 选择节点和子节点
     *
     * @param treeNode
     * @param select
     * @return
     */
    public static List<SelectUser> selectNodeAndChild(UserAreaTreeNode treeNode, boolean select) {
        List<SelectUser> expandChildren = new ArrayList<>();
        if (treeNode == null) {
            return expandChildren;
        }

        treeNode.setSelected(select);

        // 首先将当前节点的子用户全部选择上
        for (SelectUser userInfo : treeNode.getUserInfos()) {
            userInfo.setSelected(select);
            expandChildren.add(userInfo);
        }

        if (treeNode.getArea().getRegionLevel() == 4) {
            // 如果没有节点就代表是最后一层  将所有的监理取出来
            if (treeNode.getChilds() != null && treeNode.getChilds().size() > 0) {
                for (SelectUser userInfo : treeNode.getChilds().get(0).getUserInfos()) {
                    userInfo.setSelected(select);
                    expandChildren.add(userInfo);
                }
            }
            return expandChildren;
        }

        for (UserAreaTreeNode child : treeNode.getChilds()) {
            Log.e("TAG", "节点-->" + treeNode.getArea().getName());
            expandChildren.addAll(selectNodeAndChild(child, select));
            selectNodeInner(child, select);
        }

        return expandChildren;
    }

    private static void selectNodeInner(UserAreaTreeNode treeNode, boolean select) {
        if (treeNode == null) {
            return;
        }
        treeNode.setSelected(select);
        for (SelectUser child : treeNode.getUserInfos()) {
            Log.e("Inner", "监理-->" + child.getNickName());
            child.setSelected(select);
        }
    }
}
