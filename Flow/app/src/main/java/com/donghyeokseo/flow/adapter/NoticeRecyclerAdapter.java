package com.donghyeokseo.flow.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.activity.DetailNoticeActivity;
import com.donghyeokseo.flow.model.Notice;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author dawncrow
 */
public final class NoticeRecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<Notice> mList;
    private Context context;

    public NoticeRecyclerAdapter(ArrayList<Notice> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    final class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_notice_title)
        TextView title;
        @BindView(R.id.textview_notice_idx)
        TextView idx;
        @BindView(R.id.textview_notice_date)
        TextView date;
        @BindView(R.id.textview_notice_author)
        TextView author;

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Notice list) {
            title.setText(list.getContent());
            idx.setText(String.valueOf((int) (long) list.getIdx()));
            date.setText(list.getModifyDate());
            author.setText(list.getWriter());
        }

        @OnClick(R.id.item_notice_root_view)
        public void onRootViewClicked(View view) {
            Intent i = new Intent(context, DetailNoticeActivity.class);
            i.putExtra("idx", Integer.parseInt(String.valueOf(idx.getText())));
            context.startActivity(i);
        }
    }
}
