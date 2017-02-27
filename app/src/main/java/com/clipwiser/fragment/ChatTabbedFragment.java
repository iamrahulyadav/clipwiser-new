package com.clipwiser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spinfotech.spchatting.R;
import spinfotech.spchatting.base.BaseFragment;

/**
 * Created by sibaprasad on 23/12/16.
 */

public class ChatTabbedFragment extends BaseFragment {

	public static final String TAG = "ChatTabbedFragment";

	View rootView;

	// widget initialization
	private RecyclerView recyclerview;

	public static ChatTabbedFragment newInstance(  ) {

		Bundle args = new Bundle();

		ChatTabbedFragment fragment = new ChatTabbedFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( @Nullable Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

		rootView = inflater.inflate( R.layout.fragment_hometabbed ,container,false);

		initView( rootView );

		return rootView;
	}

	@Override
	public void initView( View rootView ) {
		recyclerview = ( RecyclerView ) rootView.findViewById( R.id.recyclerview );
	}

	@Override
	public void setClickListener() {

	}

	@Override
	public void setUpdata() {

	}
}
