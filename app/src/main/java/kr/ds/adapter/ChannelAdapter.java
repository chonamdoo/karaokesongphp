package kr.ds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import kr.ds.handler.ChannelHandler;
import kr.ds.karaokesongphp.R;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-31.
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ChannelHandler> mData;
    private LayoutInflater mInflater;

    public ChannelAdapter(Context context, ArrayList<ChannelHandler> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_list1_item2,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.circularImageView);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            holder.textView_count = (TextView) convertView.findViewById(R.id.textView_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!DsObjectUtils.isEmpty(mData.get(position).getImage())){


            Glide.with(mContext)
                    .load(mData.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }else{
            holder.imageView.setVisibility(View.GONE);
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getTitle())){
            holder.textView1.setVisibility(View.VISIBLE);
            holder.textView1.setText(mData.get(position).getTitle());
        }else {
            holder.textView1.setText("");
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getTotal())){
            holder.textView2.setVisibility(View.VISIBLE);
            holder.textView2.setText(mContext.getString(R.string.video)+mData.get(position).getTotal()+mContext.getString(R.string.total));
        }else {
            holder.textView2.setText("");
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getDate())){
            holder.textView3.setVisibility(View.VISIBLE);
            holder.textView3.setText(mData.get(position).getDate());
        }else {
            holder.textView3.setText("");
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getTotal())){
            holder.textView_count.setVisibility(View.VISIBLE);
            holder.textView_count.setText(mData.get(position).getTotal());
        }else {
            holder.textView_count.setText("");
        }

        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        TextView textView1, textView2, textView3, textView_count;
    }

}