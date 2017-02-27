package com.clipwiser.fragment;

/**
 * Created by sibaprasad on 01/01/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import spinfotech.spchatting.R;
import spinfotech.spchatting.data.DatabaseHelper;
import spinfotech.spchatting.data.PreferenceManager;
import spinfotech.spchatting.dto.User;
import spinfotech.spchatting.ui.adapter.UserAdapter;
import spinfotech.spchatting.util.CommonUtils;
import spinfotech.spchatting.util.Constants;


/**
 * Created by sibaprasad on 22/12/16.
 */

public class UserTabbedFragment extends Fragment {
	public static final String TAG = "UserTabbedFragment";
	List< User > listUser = new ArrayList<>();
	private View                rootView;
	private RecyclerView        recyclerViewUser;
	private LinearLayout        linearLayoutRootUserTab;
	//FIREBASE
	private FirebaseAuth        firebaseAuth;
	private DatabaseReference   databaseChatReference;
	private DatabaseReference   databaseReference;
	private FirebaseDatabase    firebaseDatabase;
	private UserAdapter         userAdapter;
	private LinearLayoutManager linearLayoutManager;
	private DatabaseHelper      databaseHelper;

	@Override
	public void onCreate( @Nullable Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		//Get Firebase auth instance
		firebaseAuth = FirebaseAuth.getInstance();
		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseHelper = new DatabaseHelper( getActivity() );
		databaseReference = firebaseDatabase.getReference( Constants.FirebaseConstants.TABLE_USER );
	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		rootView = inflater.inflate( R.layout.fragment_user_tab, container, false );

		initView( rootView );



		if( PreferenceManager.getInstance( getActivity() ).getIsUserExist()){
			listUser = databaseHelper.getUser( getActivity() );
			Log.i( TAG, "onCreateView: listUser "+listUser.size() );
			userAdapter = new UserAdapter( getActivity(), listUser );
			recyclerViewUser.setAdapter( userAdapter );
//			userAdapter.notifyDataSetChanged();
		}

		if( CommonUtils.isInternetAvailable( getActivity() ))
			getData();

		return rootView;
	}

	private void initView( View rootView ) {
		recyclerViewUser = ( RecyclerView ) rootView.findViewById( R.id.recyclerViewUser );
		linearLayoutRootUserTab = ( LinearLayout ) rootView.findViewById( R.id.linearLayoutRootUserTab );
		recyclerViewUser.setLayoutManager( new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false ) );
	}

	private List< String > getRandomSublist( String[] array, int amount ) {
		ArrayList< String > list   = new ArrayList<>( amount );
		Random              random = new Random();
		while ( list.size() < amount ) {
			list.add( array[random.nextInt( array.length )] );
		}
		return list;
	}
private void getData(){
	//	databaseChatReference = FirebaseDatabase.getInstance().getReference().child( Constants.FirebaseConstants.TABLE_CHAT );
	databaseReference.addListenerForSingleValueEvent(
			new ValueEventListener() {
				@Override
				public void onDataChange( DataSnapshot dataSnapshot ) {
					Log.i( TAG, "onDataChange: " );
					if ( dataSnapshot != null ) {

						for ( DataSnapshot innerDataSanpShot : dataSnapshot.getChildren() ) {
							//DataSnapshot of inner Childerns
							User modelChat = innerDataSanpShot.getValue( User.class );
						}


						Iterable< DataSnapshot > dataSnapshotIterable = dataSnapshot.getChildren();
						Iterator< DataSnapshot > iterator             = dataSnapshotIterable.iterator();

						while ( iterator.hasNext() ) {

							User modelChat = iterator.next().getValue( User.class );
							Log.i( TAG, "onDataChange: 2 "+modelChat.email+" name "+modelChat.username+" id "+modelChat.userId );
							listUser.add( modelChat );
							databaseHelper.insertUser( getActivity(),modelChat );
						}
						userAdapter.notifyDataSetChanged();
					}

				}

				@Override
				public void onCancelled( DatabaseError databaseError ) {
					//handle databaseError
				}
			} );
}
}