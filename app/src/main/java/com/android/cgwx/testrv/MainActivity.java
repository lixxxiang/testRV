package com.android.cgwx.testrv;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ivGoodsType;

    private RecyclerView recyclerView;

    private GoodsListRecyclerViewAdapter adapter;

    private ImageView ivStick;//置顶

    private int goodsType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ivGoodsType= (ImageView) findViewById(R.id.iv_goods_type);
        ivGoodsType.setOnClickListener(this);
        ivStick= (ImageView) findViewById(R.id.iv_stick);
        ivStick.setOnClickListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new GoodsListRecyclerViewAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见位置
                    int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                    //当滑动到第十个以上时显示置顶图标
                    if (firstVisibleItemPosition>10) {
                        ivStick.setVisibility(View.VISIBLE);
                    }else {
                        ivStick.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_goods_type://切换布局
                if (goodsType==0){
                    ivGoodsType.setImageResource(R.mipmap.good_type_grid);
                    //1：设置布局类型
                    adapter.setType(1);
                    //2：设置对应的布局管理器
                    recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                    //3：刷新adapter
                    adapter.notifyDataSetChanged();
                    goodsType=1;
                }else {
                    ivGoodsType.setImageResource(R.mipmap.good_type_linear);
                    adapter.setType(0);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    adapter.notifyDataSetChanged();
                    goodsType=0;
                }
                break;
            case R.id.iv_stick://置顶
                recyclerView.scrollToPosition(0);
                break;
        }
    }
}

