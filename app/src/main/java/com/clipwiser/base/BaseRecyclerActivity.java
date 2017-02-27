package com.clipwiser.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


import com.clipwiser.R;
import com.clipwiser.activity.SearchActivity;
import com.clipwiser.views.custom_progressbar.SmoothProgressBar;
import com.clipwiser.views.dot_progressbar.DotProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sibaprasad on 25/10/16.
 */

public class BaseRecyclerActivity extends AppCompatActivity {

	private static final String TAG = "BaseRecyclerActivity";

	private   FrameLayout       mLayoutContainer;
	protected Toolbar           mToolBar;
	private   ProgressBar       progressbarLoadingRecycler;
	private SmoothProgressBar smoothProgressbarRecycler;
	private DotProgressBar dotProgressbarLoadMore;

	@BindView( R.id.mImageViewSearchToolbar )
	protected AppCompatImageView mImageViewSearchToolbar;

	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_recyclerbase);

		mLayoutContainer = (FrameLayout) findViewById( R.id.layout_container);
		mToolBar = (Toolbar ) findViewById( R.id.toolbar);
		progressbarLoadingRecycler = ( ProgressBar ) findViewById( R.id.progressbarLoadingRecycler );
		smoothProgressbarRecycler = ( SmoothProgressBar ) findViewById( R.id.smoothProgressbarRecycler );
		dotProgressbarLoadMore = ( DotProgressBar ) findViewById( R.id.dotProgressbarLoadMore );
		dotProgressbarLoadMore.changeDotAmount( 5 );
		dotProgressbarLoadMore.changeStartColor( ContextCompat.getColor( this, R.color.color300) );
		dotProgressbarLoadMore.changeEndColor( ContextCompat.getColor( this, R.color.colorPrimary) );
		dotProgressbarLoadMore.setVisibility( View.GONE );
	}

	@OnClick(R.id.mImageViewSearchToolbar)
	protected void onSearchClicked(){
		Intent intentSearch = new Intent( BaseRecyclerActivity.this, SearchActivity.class);
		startActivity( intentSearch );
	}

	protected void setLayout(int layoutId) {
		getLayoutInflater().inflate(layoutId, mLayoutContainer);
	}

	public void showLoadMore(){
		 if(dotProgressbarLoadMore!=null)
			 dotProgressbarLoadMore.setVisibility( View.VISIBLE );

		/*if(dotProgressbarLoadMore!=null)
			dotProgressbarLoadMore.setVisibility( View.VISIBLE );*/
	}

	public void showMainLoading(){
		if(progressbarLoadingRecycler!=null)
			progressbarLoadingRecycler.setVisibility( View.VISIBLE );
		if(smoothProgressbarRecycler!=null)
			smoothProgressbarRecycler.setVisibility( View.VISIBLE );
	}

	public void hideLoadMore(){
		/*if(progressbarLoadMore!=null)
			progressbarLoadMore.setVisibility( View.GONE );*/
		 if(dotProgressbarLoadMore!=null)
			dotProgressbarLoadMore.setVisibility( View.GONE );
	}

	public void hideMainLoading(){
		if(progressbarLoadingRecycler!=null)
			progressbarLoadingRecycler.setVisibility( View.GONE );
		if(smoothProgressbarRecycler!=null)
			smoothProgressbarRecycler.setVisibility( View.INVISIBLE );
	}

}
