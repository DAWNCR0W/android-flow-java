package com.donghyeokseo.flow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.OutCheckRecyclerAdapter;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.model.Out;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dawncrow
 */
public final class OutCheckActivity extends Activity {

    private List<Out> outList;
    private DatabaseHelper databaseHelper;
    private OutCheckRecyclerAdapter outCheckRecyclerAdapter;

    @BindView(R.id.out_check_recyclerview)
    RecyclerView mOutCheckRecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_out_check);
        ButterKnife.bind(this);

        databaseHelper = new DatabaseHelper(this);

        initList();

        outCheckRecyclerAdapter = new OutCheckRecyclerAdapter(outList, OutCheckActivity.this);
        mOutCheckRecyclerview.setAdapter(outCheckRecyclerAdapter);
        mOutCheckRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initList() {

        outList = databaseHelper.getOuts();

        try {

            if (!outList.isEmpty()) {

                outCheckRecyclerAdapter.notifyDataSetChanged();
                mOutCheckRecyclerview.notifyAll();
            }
        } catch (Exception ignored) {
        }
    }
}
