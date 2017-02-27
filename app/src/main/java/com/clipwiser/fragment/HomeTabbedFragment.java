package com.clipwiser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spinfotech.spchatting.R;
import spinfotech.spchatting.dto.ModelHome;
import spinfotech.spchatting.ui.adapter.ContactAndCallAdapter;

/**
 * Created by sibaprasad on 22/12/16.
 */

public class HomeTabbedFragment extends Fragment {
	public static final String TAG = "HomeTabbedFragment";
	private RecyclerView rootRecycler;

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		rootRecycler = ( RecyclerView ) inflater.inflate(
				R.layout.fragment_hometabbed, container, false );
		setupRecyclerView( rootRecycler );
		return rootRecycler;
	}

	private void setupRecyclerView( RecyclerView recyclerView ) {
		recyclerView.setLayoutManager( new LinearLayoutManager( recyclerView.getContext() ) );
		recyclerView.setAdapter( new ContactAndCallAdapter( getActivity(),
		                                                    getRandomSublist( ModelHome.sCheeseStrings, 30 ) ) );
	}

	private List< String > getRandomSublist( String[] array, int amount ) {
		ArrayList< String > list   = new ArrayList<>( amount );
		Random              random = new Random();
		while ( list.size() < amount ) {
			list.add( array[random.nextInt( array.length )] );
		}
		return list;
	}

}