package com.clipwiser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipwiser.R;
import com.clipwiser.adapter.ClipAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.clipwiser.R.id.recyclerview;
import static com.clipwiser.R.id.view;


/**
 * Created by sibaprasad on 22/12/16.
 */

public class ClipTabbedFragment extends Fragment implements View.OnClickListener {


	public static final String TAG = "HomeTabbedFragment";
	private RecyclerView recyclerViewClips;
	private View rootView;
	List<String> listProduct = new ArrayList<>();
	GridLayoutManager gridLayoutManager;
	ClipAdapter clipAdapter;
	AppCompatTextView tvSwitch,tvSort;
	boolean isGrid;
	private int type = 0;
	public static ClipTabbedFragment newInstance(int type) {

		Bundle args = new Bundle();
		args.putInt("type",type);
		ClipTabbedFragment fragment = new ClipTabbedFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getInt("type");
	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		rootView = inflater.inflate(
				R.layout.fragment_tabbed, container, false );
		recyclerViewClips = (RecyclerView) rootView.findViewById(R.id.recyclerViewClips);
		tvSwitch = (AppCompatTextView) rootView.findViewById(R.id.tvSwitch);
		tvSort = (AppCompatTextView) rootView.findViewById(R.id.tvSort);

		tvSwitch.setOnClickListener(this);

		setupRecyclerView( recyclerViewClips );
		return rootView;
	}

	private void setupRecyclerView( RecyclerView recyclerView ) {
		recyclerView.setLayoutManager( new LinearLayoutManager( recyclerView.getContext() ) );
		for(int i = 0;i<20;i++)
			listProduct.add("Product "+i);

		clipAdapter = new ClipAdapter(getActivity(),listProduct);
		if(type == 1) {
			gridLayoutManager = new GridLayoutManager(getActivity(), 1);
		}
		else if(type == 2){
			gridLayoutManager = new GridLayoutManager(getActivity(), 2);
		}
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.setAdapter( clipAdapter );
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tvSwitch :
				if(isGrid){
					gridLayoutManager.setSpanCount( 1 );
					isGrid = false;
				}
				else{
					gridLayoutManager.setSpanCount( 2 );
					isGrid = true;
				}

				break ;
			case R.id.tvSort :

				break;
		}
	}
}