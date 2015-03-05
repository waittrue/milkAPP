package com.example.details;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;












import com.example.aaaa.R;
import com.example.details.PinnedHeaderListView.HeadViewManager;
import com.example.details.details_fragment.ItemEntity;

import android.R.integer;
import android.content.Context;
import android.graphics.Picture;
import android.media.MediaMetadata;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class myAdapter extends  BaseAdapter implements HeadViewManager{
	
	List<ItemEntity> mData ;
	Context mContext;
	LayoutInflater inflater;
	//这里注意这里是应用，，所以如果我们在外面修改了mdata 这里也会改变，这个真是我们想要的哈。
	public myAdapter(Context context , List<ItemEntity> data) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mData = data;
	    inflater  =  LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mData == null)
			return 0;
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(mData == null)
			return null;
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listdetails1, null);
			viewHolder  = new ViewHolder();
			
			
			viewHolder.title = (TextView)convertView.findViewById(R.id.date);
			viewHolder.titleLayout = (View)convertView.findViewById(R.id.title);
			viewHolder.amount = (TextView)convertView.findViewById(R.id.num);
			viewHolder.time  = (TextView)convertView.findViewById(R.id.time);
			viewHolder.picture = (ImageView)convertView.findViewById(R.id.one);
			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		//选择标题
		if(needtitle(position)){
			viewHolder.title.setText(  mData.get(position).getTitle());
			viewHolder.titleLayout.setVisibility(View.VISIBLE);
		}
		else 
			viewHolder.titleLayout.setVisibility(View.GONE);
		
		
		//设置数据
		String amount = mData.get(position).getAmount();
		viewHolder.amount.setText(  amount + "ml");
		viewHolder.time.setText( mData.get(position).getTime());
		if( Integer.parseInt(	amount ) < 100)
			viewHolder.picture.setImageResource(R.drawable.detail_amount_warning);
		else 
			viewHolder.picture.setImageResource(R.drawable.detail_amount_normal);
		
		
		return convertView;
	}
	
	
	private boolean needtitle(int position){
		if(position == 0 )
			return true;
		
		ItemEntity currentItemEntity = (ItemEntity)mData.get(position);
		ItemEntity previousItemEntity = (ItemEntity)mData.get(position - 1);
		
		if(currentItemEntity == null || previousItemEntity == null)
			return false;
		
		String currentTitle = currentItemEntity.getTitle();
		String previousTitle = previousItemEntity.getTitle();
		if(  currentTitle ==null || previousTitle == null  )
			return false;
		
		if(currentTitle.equals( previousTitle ))
			return false;
		return true;	
		
	}
	
	private boolean isMove(int position){
		if(position == 0)
			return false;
		ItemEntity currentItemEntity = mData.get(position);
		ItemEntity nextItemEntity = mData.get(position + 1);
		if( currentItemEntity == null || nextItemEntity == null){
			return false;
		}
		String currentTitle = currentItemEntity.getTitle();
		String nextTitle = nextItemEntity.getTitle();
		if(currentTitle == null || nextTitle == null)
			return false;
		if(currentTitle.equals(nextTitle))
			return false;
		return true;
		
	}
	@Override
	public int getHeadViewState(int position) {
		// TODO Auto-generated method stub
		if (mData.size() <=0)
			return HeadViewManager.HEAD_VIEW_GONE;
		
		if ( isMove(position))
			return HeadViewManager.HEAD_VIEW_MOVE;
		
		return HeadViewManager.HEAD_VIEW_VISIBLE;
	}

	@Override
	public void configureHeadView(View view,int position) {
		// TODO Auto-generated method stub
		ItemEntity itemEntity = mData.get(position);
		String title = itemEntity.getTitle();
		if (	!title.equals("") ) {
			TextView textView = (TextView)view.findViewById(R.id.date);
			textView.setText(title);
		}
	}
	
	
	
	
	private class ViewHolder {
        TextView title;
        ImageView picture;
        TextView amount;
        TextView time;
        View  titleLayout;
        
    }
	
	
}

