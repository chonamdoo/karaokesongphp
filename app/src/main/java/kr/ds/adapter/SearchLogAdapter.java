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

import kr.ds.handler.SearchLogHandler;
import kr.ds.karaokesongphp.R;
import kr.ds.karaokesongphp.SubActivity;

/**
 * Created by Administrator on 2017-07-21.
 */

public class SearchLogAdapter extends RecyclerView.Adapter<SearchLogAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SearchLogHandler> mData;
    private LayoutInflater mInflater;
    private EventClickListener mEventClickListener;

    public SearchLogAdapter setCallBaack(EventClickListener eventClickListener){
        mEventClickListener = eventClickListener;
        return this;
    }
    public SearchLogAdapter(Context context, ArrayList<SearchLogHandler> data) {
        mContext = context;
        mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //레이아웃전개
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_log_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//바인더
        holder.texView.setText(mData.get(position).getMessage());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
            if (mEventClickListener != null) {
                mEventClickListener.onEvent(mData.get(position).getMessage());
            }

            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {//view 홀더
        public TextView texView;
        private ItemClickListener clickListener;
        public ViewHolder(View v) {
            super(v);
            texView = (TextView) v.findViewById(R.id.textView);
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

    public interface EventClickListener {
        void onEvent(String message);
    }
}
