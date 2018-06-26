package com.donghyeokseo.flow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.model.Out;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dawncrow
 */
public final class OutCheckRecyclerAdapter extends RecyclerView.Adapter {

    private List<Out> outList;
    private Context context;

    public OutCheckRecyclerAdapter(List<Out> outList, Context context) {
        this.outList = outList;
        this.context = context;
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
            default:
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

    class OutViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_item_out_reason)
        TextView reasonTv;
        @BindView(R.id.textview_item_out_start)
        TextView startTimeTv;
        @BindView(R.id.textview_item_out_end)
        TextView endTimeTv;

        OutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in4));
        }

        void bind(Out out) {
            reasonTv.setText(out.getReason());
            startTimeTv.setText(out.getStartTime());
            endTimeTv.setText(out.getEndTime());
        }
    }
}
