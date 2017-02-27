package com.clipwiser.adapter;

/**
 * Created by sibaprasad on 08/10/16.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.clipwiser.R;
import com.clipwiser.interfaces.SlidingImageClickListener;

import java.util.List;


/**
 * Created by SIBAPRASAD on 8/10/16.
 */
public class ImageSlidingAdapter extends PagerAdapter {
	private static final String TAG = ImageSlidingAdapter.class.getSimpleName();
	private final Context mContext;
	SlidingImageClickListener imageClickListener;
	boolean isImageClickable = false;
	AppCompatImageView imageViewSlidingImage;
	AppCompatTextView  textViewBannerItem;
	FrameLayout        frameLayoutBannerTextItem;
	String[]           bannerData;
	private List< String > listImageUrls;

	public ImageSlidingAdapter( Context context, List< String > data, SlidingImageClickListener mImageClickListener, boolean isImageClickable ) {
		this.listImageUrls = data;
		mContext = context;
		this.imageClickListener = mImageClickListener;
		Log.i( TAG, " list size : " + data.size() );
		this.isImageClickable = isImageClickable;
		bannerData = context.getResources().getStringArray( R.array.bannerDataWelcome );
	}

	@Override
	public int getCount() {
		return listImageUrls.size();
	}

	public int getRealCount() {
		return listImageUrls.size();
	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {
		return ( view == object );
	}

	@Override
	public Object instantiateItem( ViewGroup container, final int position ) {
		LayoutInflater inflater = LayoutInflater.from( mContext );
		ViewGroup      layout   = ( ViewGroup ) inflater.inflate( R.layout.itemview_imageslider, container, false );

		imageViewSlidingImage = ( AppCompatImageView ) layout.findViewById( R.id.imageViewSlidingImage );
		textViewBannerItem = ( AppCompatTextView ) layout.findViewById( R.id.textViewBannerItem );
		frameLayoutBannerTextItem = ( FrameLayout ) layout.findViewById( R.id.frameLayoutBannerTextItem );

		Log.i( TAG, "instantiateItem: IMAGE " + listImageUrls.get( position ) );


		if ( !TextUtils.isEmpty( listImageUrls.get( position ) ) ) {
			/*ImageViewAware      imageAware    = new ImageViewAware( mImageViewPdpSlider, false );
			ImageLoader         imageLoader   = SpCartApplication.getImageLoader();
			final AtomicBoolean playAnimation = new AtomicBoolean( true);*/

			Glide.with( mContext )
					.load( listImageUrls.get( position ) )
					.error( R.drawable.ic_launcher )         // optional
					.into( imageViewSlidingImage );

			/*Picasso.with( mContext)
					.load(listImageUrls.get( position ))
					.error(R.drawable.ic_launcher)         // optional
					.into(imageViewSlidingImage);*/

			if ( isImageClickable && imageClickListener != null ) {
				frameLayoutBannerTextItem.setVisibility( View.GONE );
				imageViewSlidingImage.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick( View v ) {
						imageClickListener.onImageClick( listImageUrls.get( position ) );
					}
				} );
			}
			else {
				frameLayoutBannerTextItem.setVisibility( View.VISIBLE );
				textViewBannerItem.setText( bannerData[position] );
				imageViewSlidingImage.setScaleType( ImageView.ScaleType.FIT_XY );
			}
		}
		container.addView( layout );
		return layout;
	}

	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {
		( ( ViewPager ) container ).removeView( ( View ) object );
	}
}

