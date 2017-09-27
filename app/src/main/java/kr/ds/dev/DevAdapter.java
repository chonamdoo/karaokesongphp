package kr.ds.dev;

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

import kr.ds.karaokesongphp.R;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-31.
 */
public class DevAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DevHandler> mData;
    private LayoutInflater mInflater;


    public DevAdapter(Context context, ArrayList<DevHandler> data) {
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
            convertView = mInflater.inflate(R.layout.dev_view_item,null);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textView);
            holder.textViewSubName = (TextView) convertView.findViewById(R.id.textView_sub);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
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

        if(!DsObjectUtils.isEmpty(mData.get(position).getName())){
            holder.textViewName.setVisibility(View.VISIBLE);
            holder.textViewName.setText(mData.get(position).getName());
        }else {
            holder.textViewName.setVisibility(View.GONE);
            holder.textViewName.setText("");
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getSub_name())){
            holder.textViewSubName.setVisibility(View.VISIBLE);
            holder.textViewSubName.setText(mData.get(position).getSub_name());
        }else {
            holder.textViewSubName.setVisibility(View.GONE);
            holder.textViewSubName.setText("");
        }

       return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        TextView  textViewName, textViewSubName;

    }

}