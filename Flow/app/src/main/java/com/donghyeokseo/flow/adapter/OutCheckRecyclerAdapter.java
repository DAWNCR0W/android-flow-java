package com.donghyeokseo.flow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.model.Out;

import java.util.List;

public final class OutCheckRecyclerAdapter extends RecyclerView.Adapter {

    private List<Out> outList;


    public OutCheckRecyclerAdapter(List<Out> outList) {
        this.outList = outList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case Util.OUTGO_APPROVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outgo_approved, parent, false);
                break;
            case Util.OUTGO_DECLINED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outgo_declined, parent, false);
                break;
            case Util.OUTGO_REQUESTED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outgo_requested, parent, false);
                break;
            case Util.OUTSLEEP_APPROVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outsleep_approved, parent, false);
                break;
            case Util.OUTSLEEP_DECLINED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outsleep_declined, parent, false);
                break;
            case Util.OUTSLEEP_REQUESTED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outsleep_requested, parent, false);
                break;
        }

        return new OutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OutViewHolder) holder).bind(outList.get(position));
    }

    @Override
    public int getItemCount() {
        return outList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return outList.get(position).getStatusCode();
    }

    private class OutViewHolder extends RecyclerView.ViewHolder {
        TextView reasonTv, startTimeTv, endTimeTv;

        OutViewHolder(View itemView) {
            super(itemView);
            reasonTv = itemView.findViewById(R.id.textview_item_out_reason);
            startTimeTv = itemView.findViewById(R.id.textview_item_out_start);
            endTimeTv = itemView.findViewById(R.id.textview_item_out_end);
        }

        void bind(Out out) {
            reasonTv.setText(out.getReason());
            startTimeTv.setText(out.getStartTime());
            endTimeTv.setText(out.getEndTime());
        }
    }
}
