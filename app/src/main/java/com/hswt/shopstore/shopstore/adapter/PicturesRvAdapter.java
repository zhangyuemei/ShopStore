package com.hswt.shopstore.shopstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.bean.PicturesDataBean;

import java.util.List;

/**
 * Created by zhangyuemei on 2019/2/26.
 * Description:
 */
public class PicturesRvAdapter extends RecyclerView.Adapter<PicturesRvAdapter.ViewHolder> {

    private Context context;
    private List<PicturesDataBean> data;

    public PicturesRvAdapter(Context context, List<PicturesDataBean> list) {
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public PicturesRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.picture_rv_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesRvAdapter.ViewHolder viewHolder, int i) {

         //viewHolder.iv
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img_rv_item);
        }
    }

}
