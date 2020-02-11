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
import com.qdhc.ny.bean.UserNode;
import com.qdhc.ny.bean.UserTreeNode;
import com.qdhc.ny.utils.TreeNodeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构
 */
public class OrgInfoAdapter extends RecyclerView.Adapter<OrgInfoAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private UserTreeNode treeNode;
    private Context context;

    public OrgInfoAdapter(Context context, UserTreeNode treeNode) {
        this.context = context;
        this.treeNode = treeNode;
        mInflater = LayoutInflater.from(context);
    }

    public void setTreeNode(UserTreeNode treeNode) {
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
            if (treeNode.getLevel() == 4) {
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
        if (treeNode.getLevel() != 4) {
            if (position < treeNode.getChilds().size()) {
                final UserTreeNode childNode = treeNode.getChilds().get(position);
                holder.checkBox.setChecked(childNode.isSelected());

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("CheckBox", "节点-->" + childNode.getName());
                        boolean checked = holder.checkBox.isChecked();
                        selectNode(checked, childNode);
                    }
                });
                holder.nameTv.setText(childNode.getName());
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
                final UserNode userNode = treeNode.getUserInfos().get(position - treeNode.getChilds().size());
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<UserNode> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getUserInfo().getNickName());
                holder.countTv.setVisibility(View.INVISIBLE);
                holder.levelView.setVisibility(View.INVISIBLE);
                holder.photoIv.setVisibility(View.VISIBLE);
            }
        } else {

            if (position < treeNode.getUserInfos().size()) {
                final UserNode userNode = treeNode.getUserInfos().get(position);
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<UserNode> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getUserInfo().getNickName());
            } else {
                final UserNode userNode = treeNode.getChilds().get(0).getUserInfos().get(position - treeNode.getUserInfos().size());
                holder.checkBox.setChecked(userNode.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = holder.checkBox.isChecked();
                        userNode.setSelected(checked);
                        if (mItemClickListener != null) {
                            List<UserNode> children = new ArrayList<>();
                            children.add(userNode);
                            mItemClickListener.onUserInfoSelectedChanged(children, checked);
                        }
                    }
                });
                holder.nameTv.setText(userNode.getUserInfo().getNickName());
            }

            holder.countTv.setVisibility(View.INVISIBLE);
            holder.levelView.setVisibility(View.INVISIBLE);
            holder.photoIv.setVisibility(View.VISIBLE);
        }
    }

    public void selectNode(boolean checked, UserTreeNode treeNode) {
        treeNode.setSelected(checked);

        selectChildren(treeNode, checked);
        selectParentIfNeed(treeNode, checked);
    }

    private void selectChildren(UserTreeNode treeNode, boolean checked) {
        List<UserNode> impactedChildren = TreeNodeHelper.selectNodeAndChild(treeNode, checked);
        Log.e("selectChildren", "节点-->" + impactedChildren.size());
        if (impactedChildren.size() > 0) {
            if (mItemClickListener != null) {
                mItemClickListener.onUserInfoSelectedChanged(impactedChildren, checked);
            }
            notifyDataSetChanged();
        }
    }

    private void selectParentIfNeed(UserTreeNode treeNode, boolean checked) {

    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);

        void onNodeClick(UserTreeNode treeNode);

        void onUserInfoSelectedChanged(List<UserNode> userNodes, boolean isSelected);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
