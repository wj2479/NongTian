package com.qdhc.ny.utils;

import android.util.Log;

import com.qdhc.ny.bean.UserNode;
import com.qdhc.ny.bean.UserTreeNode;

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
    public static int getChildCount(UserTreeNode treeNode) {
        int count = 0;
        if (treeNode.getLevel() == 2) {
            for (UserTreeNode child : treeNode.getChilds()) {   // 区县
                count += child.getUserInfos().size();
                for (UserTreeNode childChild : child.getChilds()) {   // 乡镇
                    int n = childChild.getUserInfos().size();
                    count += n;
                    if (childChild.getChilds().size() > 0) {
                        int num = childChild.getChilds().get(0).getUserInfos().size();
                        count += num;
                    }
                }
            }

        } else if (treeNode.getLevel() == 3) {
            count += treeNode.getUserInfos().size();
            for (UserTreeNode child : treeNode.getChilds()) {   // 乡镇
                int n = child.getUserInfos().size();
                count += n;
                if (child.getChilds().size() > 0) {
                    int num = child.getChilds().get(0).getUserInfos().size();
                    count += num;
                }
            }
        } else if (treeNode.getLevel() == 4) {
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
    public static List<UserNode> getSelectedNodes(UserTreeNode treeNode) {
        List<UserNode> selectedNodes = new ArrayList<>();
        if (treeNode == null) {
            return selectedNodes;
        }

        // 首先将当前节点的子用户全部选择上
        for (UserNode userInfo : treeNode.getUserInfos()) {
            Log.e("select-----》", treeNode.getName() + "  ====  " + userInfo.getUserInfo().getNickName());
            if (userInfo.isSelected())
                selectedNodes.add(userInfo);
        }

        if (!treeNode.hasChild()) {
            // 如果没有节点就代表是最后一层  将所有的监理取出来
            Log.e("no child-----》", treeNode.getName() + "  ====  ");
            for (UserNode userInfo : treeNode.getChilds().get(0).getUserInfos()) {
                if (userInfo.isSelected())
                    selectedNodes.add(userInfo);
            }
            return selectedNodes;
        }

        for (UserTreeNode child : treeNode.getChilds()) {
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
    public static List<UserNode> selectNodeAndChild(UserTreeNode treeNode, boolean select) {
        List<UserNode> expandChildren = new ArrayList<>();
        if (treeNode == null) {
            return expandChildren;
        }

        treeNode.setSelected(select);

        // 首先将当前节点的子用户全部选择上
        for (UserNode userInfo : treeNode.getUserInfos()) {
            userInfo.setSelected(select);
            expandChildren.add(userInfo);
        }

        if (!treeNode.hasChild()) {
            // 如果没有节点就代表是最后一层  将所有的监理取出来
            if (treeNode.getChilds().size() > 0) {
                for (UserNode userInfo : treeNode.getChilds().get(0).getUserInfos()) {
                    userInfo.setSelected(select);
                    expandChildren.add(userInfo);
                }
            }
            return expandChildren;
        }

        for (UserTreeNode child : treeNode.getChilds()) {
            Log.e("TAG", "节点-->" + treeNode.getName());
            expandChildren.addAll(selectNodeAndChild(child, select));
            selectNodeInner(child, select);
        }

        return expandChildren;
    }

    private static void selectNodeInner(UserTreeNode treeNode, boolean select) {
        if (treeNode == null) {
            return;
        }
        treeNode.setSelected(select);
        for (UserNode child : treeNode.getUserInfos()) {
            Log.e("Inner", "监理-->" + child.getUserInfo().getNickName());
            child.setSelected(select);
        }
    }
}
