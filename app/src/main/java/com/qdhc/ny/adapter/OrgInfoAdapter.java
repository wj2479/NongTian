package com.qdhc.ny.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdhc.ny.R;
import com.qdhc.ny.entity.SelectUser;
import com.qdhc.ny.entity.UserAreaTreeNode;
import com.qdhc.ny.utils.TreeNodeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构
 */
public class OrgInfoAdapter extends RecyclerView.Adapter<OrgInfoAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private UserAreaTreeNode treeNode;
    private Context context;

    public OrgInfoAdapter(Context context, UserAreaTreeNode treeNode) {
        this.context = context;
        this.treeNode = treeNode;
        mInflater = LayoutInflater.from(context);
    }

    public void setTreeNode(UserAreaTreeNode treeNode) {
        this.treeNode = treeNode;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatCheckBox checkBox;
        TextView nameTv, countTv;
        View levelView;
        ImageView photoIv;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            nameTv = view.findViewById(R.id.node_name_view);
            countTv = view.findViewById(R.id.node_count_view);
            photoIv = view.findViewById(R.id.iv_photo);
            levelView = view.findViewById(R.id.nextLevelLayout);
        }
    }

    @Override
    public int getItemCount() {
        if (treeNode == null) {
            return 0;
        } else {
            if (treeNode.getArea().getRegionLevel() == 4) {
                return treeNode.getChilds().get(0).getUserInfos().size() + treeNode.getUserInfos().size();
            } else {
                return treeNode.getChilds().size() + treeNode.getUserInfos().size();
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_org_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // 说明不是最底层结构
        if (treeNode.getArea().getRegionLevel() != 4) {

            if (position < treeNode.getChilds().size()) {
                final UserAreaTreeNode childNode = treeNode.getChilds().get(position);
                holder.checkBox.setChecked(childNode.isSelected());

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("CheckBox", "节点-->" + childNode.getArea().getName());
                        boolean checked = holder.checkBox.isChecked();
                        selectNode(checked, childNode);
                    }
                });
                holder.nameTv.setText(childNode.getArea().getName());
                if (childNode.getChilds().size() > 0) {
                    holder.levelView.setVisibility(View.VISIBLE);
                    holder.levelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mItemClickListener != null) {
                                mItemClickListener.onNodeClick(childNode);
                            }
                        }
                    });
                } else {
                    holder.levelView.setVisibility(View.INVISIBLE);
                }
                holder.photoIv.setVisibility(View.GONE);
                // 获取子节点的数量
                int count = TreeNodeHelper.getChildCount(childNode);
                holder.countTv.setVisibility(View.VISIBLE);
                holder.countTv.setText(new StringBuffer().append("(").append(count).append("人)"));

            } else {
                final SelectUser userNode = treeNode.getUserInfos().get(position - treeNode.getChilds().size());
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<SelectUser> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getNickName());
                holder.countTv.setVisibility(View.INVISIBLE);
                holder.levelView.setVisibility(View.INVISIBLE);
                holder.photoIv.setVisibility(View.VISIBLE);
            }
        } else {
            if (position < treeNode.getUserInfos().size()) {
                final SelectUser userNode = treeNode.getUserInfos().get(position);
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<SelectUser> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getNickName());
            } else {
                final SelectUser userNode = treeNode.getChilds().get(0).getUserInfos().get(position - treeNode.getUserInfos().size());
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<SelectUser> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getNickName());
            }

            holder.countTv.setVisibility(View.INVISIBLE);
            holder.levelView.setVisibility(View.INVISIBLE);
            holder.photoIv.setVisibility(View.VISIBLE);
        }
    }

    public void selectNode(boolean checked, UserAreaTreeNode treeNode) {
        treeNode.setSelected(checked);

        selectChildren(treeNode, checked);
        selectParentIfNeed(treeNode, checked);
    }

    private void selectChildren(UserAreaTreeNode treeNode, boolean checked) {
        List<SelectUser> impactedChildren = TreeNodeHelper.selectNodeAndChild(treeNode, checked);
        Log.e("selectChildren", "节点-->" + impactedChildren.size());
        if (impactedChildren.size() > 0) {
            if (mItemClickListener != null) {
                mItemClickListener.onUserInfoSelectedChanged(impactedChildren, checked);
            }
            notifyDataSetChanged();
        }
    }

    private void selectParentIfNeed(UserAreaTreeNode treeNode, boolean checked) {

    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);

        void onNodeClick(UserAreaTreeNode treeNode);

        void onUserInfoSelectedChanged(List<SelectUser> userNodes, boolean isSelected);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
