package kr.ds.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

import kr.ds.data.BaseResultListener;
import kr.ds.db.RecordDB;
import kr.ds.handler.RecordHandler;
import kr.ds.karaokesongphp.EffectActivity;
import kr.ds.karaokesongphp.R;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-31.
 */
public class RecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RecordHandler> mData;
    private LayoutInflater mInflater;
    private RecordDB mRecordDB;


    public RecordAdapter(Context context, ArrayList<RecordHandler> data) {
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
            convertView = mInflater.inflate(R.layout.fragment_list1_item3,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.circularImageView);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.imageViewShare =  (ImageView) convertView.findViewById(R.id.imageView_share);
            holder.imageViewDelete =  (ImageView) convertView.findViewById(R.id.imageView_delete);
            holder.imageViewEffect =  (ImageView) convertView.findViewById(R.id.imageView_effect);

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

        if(!DsObjectUtils.isEmpty(mData.get(position).getRegdate())){
            holder.textView2.setVisibility(View.VISIBLE);
            holder.textView2.setText(mData.get(position).getRegdate());
        }else {
            holder.textView2.setText("");
        }
        holder.imageViewShare.setTag(position);
        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File audioFile = new File(mData.get(position).getUrl_file());
                if (audioFile.exists() == false){ return; }
                int position = (int) v.getTag();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(audioFile));
                intent.setType("audio/*");
                mContext.startActivity(Intent.createChooser(intent, "녹음 파일 공유하기"));

            }
        });

        holder.imageViewEffect.setTag(position);
        holder.imageViewEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Intent intent = new Intent(mContext, EffectActivity.class);
                intent.putExtra("data", mData.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.imageViewDelete.setTag(position);
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                try {
                    mRecordDB = new RecordDB(mContext);
                    mRecordDB.open();
                    mRecordDB.deleteNote(mData.get(position).getContents_id());
                    new File(mData.get(position).getUrl_file()).delete();
                    mData.remove(position);
                    notifyDataSetChanged();
                    mRecordDB.close();
                    Toast.makeText(mContext, "내노래 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, "오류가 발생 되었습니다. 계속 문제가 발생시 관리자에게 문의 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView imageView, imageViewShare, imageViewDelete, imageViewEffect;
        TextView textView1, textView2;
    }

}