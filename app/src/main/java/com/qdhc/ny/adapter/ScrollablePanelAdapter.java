package com.qdhc.ny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;
import com.qdhc.ny.R;
import com.qdhc.ny.bean.ATableTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelin on 16-11-18.
 */

public class ScrollablePanelAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int ROOM_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int ORDER_TYPE = 2;
    Context mContext;
    private List<String> roomInfoList = new ArrayList<>();
    private List<ATableTitle> dateInfoList = new ArrayList<>();
    private ArrayList<ArrayList<ATableTitle>> ordersList = new ArrayList<>();
    private SubClickListener subClickListener;

    @Override
    public int getRowCount() {
        return roomInfoList.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return dateInfoList.size() + 1;
    }

    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return ROOM_TYPE;
        }
        if (row == 0) {
            return DATE_TYPE;
        }
        return ORDER_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case DATE_TYPE:
                setDateView(column, (DateViewHolder) holder);
                break;
            case ROOM_TYPE:
                setRoomView(row, (RoomViewHolder) holder);
                break;
            case ORDER_TYPE:
                setOrderView(row, column, (OrderViewHolder) holder);
                break;
            case TITLE_TYPE:
                break;
            default:
                setOrderView(row, column, (OrderViewHolder) holder);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case DATE_TYPE:
                return new DateViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_date_info, parent, false));
            case ROOM_TYPE:
                return new RoomViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_room_info, parent, false));
            case ORDER_TYPE:
                return new OrderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_order_info, parent, false));
            case TITLE_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_title, parent, false));
            default:
                break;
        }
        return new OrderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_order_info, parent, false));
    }

    private void setDateView(final int pos, DateViewHolder viewHolder) {
        ATableTitle dateInfo = dateInfoList.get(pos - 1);
        if (dateInfo != null && pos > 0) {
            viewHolder.dateTextView.setText(dateInfo.getTitle());
            viewHolder.dateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (subClickListener != null) {
                        subClickListener.OntopicClickListener(view, pos);
                    }
                }
            });
            final ViewGroup.LayoutParams lp = viewHolder.date_fr.getLayoutParams();
            if (pos == 0) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (pos == 1) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (pos == 2) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (pos == 3) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 150);
            } else if (pos == 4) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 150);
            } else if (pos == 9) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 80);
            }
            viewHolder.date_fr.setLayoutParams(lp);

        }
    }

    private void setRoomView(int pos, RoomViewHolder viewHolder) {
        String roomInfo = roomInfoList.get(pos - 1);
        if (roomInfo != null && pos > 0) {

            viewHolder.roomNameTextView.setText(roomInfo);

        }
    }

    private void setOrderView(final int row, final int column, OrderViewHolder viewHolder) {
        final ATableTitle orderInfo = ordersList.get(row - 1).get(column - 1);

        if (orderInfo != null) {
            viewHolder.nameTextView.setText(orderInfo.getTitle());
            if (orderInfo.getState() == 0) {
                viewHolder.nameTextView.setTextColor(android.graphics.Color.parseColor("#333333"));
            } else if (orderInfo.getState() > 0) {
                viewHolder.nameTextView.setTextColor(android.graphics.Color.parseColor("#039145"));
            } else {
                viewHolder.nameTextView.setTextColor(android.graphics.Color.parseColor("#ff0000"));
            }
            final ViewGroup.LayoutParams lp = viewHolder.guest_layout.getLayoutParams();
            if (column == 0) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (column == 1) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (column == 2) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else if (column == 3) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 150);
            } else if (column == 4) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 150);
            } else if (column == 9) {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 70);
            } else {
                lp.width = com.qdhc.ny.utils.BaseUtil.dip2px(mContext, 80);
            }
            viewHolder.guest_layout.setLayoutParams(lp);
        }
    }

    public void setRoomInfoList(List<String> roomInfoList) {
        this.roomInfoList = roomInfoList;
    }

    public void setDateInfoList(List<ATableTitle> dateInfoList) {
        this.dateInfoList = dateInfoList;
    }

    public void setOrdersList(ArrayList<ArrayList<ATableTitle>> ordersList) {
        this.ordersList = ordersList;
    }

    public void setsubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }

    public interface SubClickListener {
        void OntopicClickListener(View v, int position);
    }

    private static class DateViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public LinearLayout date_fr;
        public ImageView iv;
        public ImageView iv_top;

        public DateViewHolder(View itemView) {
            super(itemView);
            this.dateTextView = (TextView) itemView.findViewById(R.id.date);
            this.date_fr = (LinearLayout) itemView.findViewById(R.id.date_fr);
            this.iv = (ImageView) itemView.findViewById(R.id.iv_title);
            this.iv_top = (ImageView) itemView.findViewById(R.id.iv_title_top);
        }

    }

    private static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomNameTextView;

        public RoomViewHolder(View view) {
            super(view);
            this.roomNameTextView = (TextView) view.findViewById(R.id.room_name);
        }
    }

    private static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public LinearLayout guest_layout;
        public View view;

        public OrderViewHolder(View view) {
            super(view);
            this.view = view;
            this.nameTextView = (TextView) view.findViewById(R.id.guest_name);
            this.guest_layout = (LinearLayout) view.findViewById(R.id.guest_layout);
        }
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.title);
        }
    }

}
