package com.clipwiser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.clipwiser.R;

import java.util.List;

/**
 * Created by sibaprasad on 23/12/16.
 */

public  class ContactAndCallAdapter
		extends RecyclerView.Adapter< ContactAndCallAdapter.ViewHolderMain > {

	private final TypedValue mTypedValue = new TypedValue();
	private int            mBackground;
	private List< String > mValues;
	private Context context;
	private String boundString;

	public ContactAndCallAdapter( Context context, List< String > items ) {
		this.context = context;
		context.getTheme().resolveAttribute( R.attr.selectableItemBackground, mTypedValue, true );
		mBackground = mTypedValue.resourceId;
		mValues = items;
	}

	public String getValueAt( int position ) {
		return mValues.get( position );
	}

	@Override
	public ViewHolderMain onCreateViewHolder( ViewGroup parent, int viewType ) {
		View view = LayoutInflater.from( parent.getContext() )
				.inflate( R.layout.itemview_home_list, parent, false );
		view.setBackgroundResource( mBackground );
		return new ViewHolderMain( view );
	}

	@Override
	public void onBindViewHolder( final ViewHolderMain holder, int position ) {
		boundString = mValues.get( position );
		holder.textViewNameChatList.setText( mValues.get( position ) );
		holder.textViewLastChat.setText( mValues.get( position ) );


		if(position %2== 0)
		Glide.with( context )
				.load( R.mipmap.ic_launcher )
				.into( holder.imageviewItemChatListMain );
		else
			Glide.with( context )
					.load( R.mipmap.ic_launcher )
					.into( holder.imageviewItemChatListMain );
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	public static class ViewHolderMain extends RecyclerView.ViewHolder {
		RelativeLayout    relativeLayoutRootMainChatlist;
		AppCompatImageView   imageviewItemChatListMain;
		AppCompatTextView textViewNameChatList;
		AppCompatTextView textViewLastChat;
		AppCompatTextView textViewTimeChatList;
		public ViewHolderMain( View itemView ) {
			super( itemView );
			relativeLayoutRootMainChatlist = ( RelativeLayout ) itemView.findViewById( R.id.relativeLayoutRootMainChatlist );
			imageviewItemChatListMain = ( AppCompatImageView ) itemView.findViewById( R.id.imageviewItemChatListMain );
			textViewNameChatList = ( AppCompatTextView ) itemView.findViewById( R.id.textViewNameChatList );
			textViewLastChat = ( AppCompatTextView ) itemView.findViewById( R.id.textViewLastChat );
			textViewTimeChatList = ( AppCompatTextView ) itemView.findViewById( R.id.textViewTimeChatList );
		}
	}
}