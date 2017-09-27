package kr.ds.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import kr.ds.handler.ListHandler;
import kr.ds.karaokesongphp.R;
import kr.ds.karaokesongphp.SubActivity;

/**
 * Created by Administrator on 2017-07-21.
 */

public class MainItem1Adapter extends RecyclerView.Adapter<MainItem1Adapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ListHandler> mData;
    private LayoutInflater mInflater;

    public MainItem1Adapter(Context context, ArrayList<ListHandler> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //레이아웃전개
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main_item1, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//바인더
        holder.texView.setText(mData.get(position).getTitle());
        Glide.with(mContext)
                .load(mData.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, SubActivity.class);
                intent.putExtra("data", mData.get(position));
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {//view 홀더
        public TextView texView;
        public ImageView imageView;
        private ItemClickListener clickListener;
        public ViewHolder(View v) {
            super(v);
            texView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onClick(v, getLayoutPosition());
            }
        }
    }
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
