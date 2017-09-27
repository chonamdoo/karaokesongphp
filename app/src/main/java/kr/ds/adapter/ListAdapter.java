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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import kr.ds.karaokesongphp.R;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-31.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ListHandler> mData;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, ArrayList<ListHandler> data) {
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
            convertView = mInflater.inflate(R.layout.fragment_list1_item1,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.circularImageView);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);

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

        if(!DsObjectUtils.isEmpty(mData.get(position).getHit())){
            holder.textView2.setVisibility(View.VISIBLE);
            holder.textView2.setText("조회수 "+mData.get(position).getHit());
        }else {
            holder.textView2.setText("");
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getDate())){
            holder.textView3.setVisibility(View.VISIBLE);
            holder.textView3.setText(mData.get(position).getDate());
        }else {
            holder.textView3.setText("");
        }

        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        TextView textView1, textView2, textView3;
    }

}