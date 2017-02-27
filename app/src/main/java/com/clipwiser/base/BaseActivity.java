/*
 * Copyright © 2016, Craftsvilla.com
 * Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package com.clipwiser.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.clipwiser.R;
import com.clipwiser.utils.Constants;
import com.clipwiser.views.custom_progressbar.SmoothProgressBar;

import static com.clipwiser.R.styleable.SmoothProgressBar;


public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.LeftMenuClickListener,
	BaseFragment.RightMenuClickListener,View.OnClickListener,
    BaseFragment.ToolbarListener,
    BaseFragment.LoginSuccessListener {

	private static final String TAG = "BaseActivity";
	
    private   FrameLayout           mLayoutContainer;
    protected Toolbar               mToolBar;
    protected DrawerLayout          mDrawerLayout;
    private   ActionBarDrawerToggle mDrawerToggle;
    private   View                  mNavigationHeaderView;
    protected DialogFragment        fragment;
	NavigationView                                           navigationViewLeft;
	NavigationView                                           navigationViewRight;
	com.clipwiser.views.custom_progressbar.SmoothProgressBar smoothProgressbarHome;
	private AppCompatImageView mImageViewUser;

	public  OnNavigationClick onNavigationClick;
	private AppCompatTextView mTextViewTitleToolbar;
	private boolean isLeftDrawerEnable = true;
	private boolean isRightDrawerEnable = true;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_base);


	    onNavigationClick = (OnNavigationClick )this;

        mLayoutContainer = (FrameLayout) findViewById( R.id.layout_container);
	    navigationViewLeft = ( NavigationView ) findViewById( R.id.nav_view );
	    navigationViewRight = ( NavigationView ) findViewById( R.id.nav_right_view );
	    smoothProgressbarHome = ( SmoothProgressBar ) findViewById( R.id.smoothProgressbarHome );
        mToolBar = (Toolbar ) findViewById( R.id.toolbar);
        mDrawerLayout = (DrawerLayout ) findViewById( R.id.drawer_layout);
	    mTextViewTitleToolbar = ( AppCompatTextView ) findViewById( R.id.mTextViewTitleToolbar );
        setSupportActionBar(mToolBar);

	    mToolBar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
		    @Override
		    public boolean onMenuItemClick( MenuItem item ) {
			    if ( item.getItemId() == R.id.mImageViewUser ) {
				    mDrawerLayout.openDrawer( Gravity.RIGHT ); // where mDrawerLayout is your android.support.v4.widget.DrawerLayout in your activity's xml layout.
			    }
			    return false;
		    }
	    } );

	    final ActionBar ab = getSupportActionBar();
	    ab.setHomeAsUpIndicator( R.drawable.ic_menu_24dp );
	    ab.setDisplayHomeAsUpEnabled( true );
	    mImageViewUser = ( AppCompatImageView ) findViewById( R.id.mImageViewUser );

	    mImageViewUser.setOnClickListener( new View.OnClickListener() {
		    @Override
		    public void onClick( View view ) {
			    mDrawerLayout.openDrawer( GravityCompat.END );
		    }
	    } );

	    if ( navigationViewLeft != null ) {
		    setupDrawerContentLeft( navigationViewLeft );
	    }

	    if ( navigationViewRight != null ) {
		    setupDrawerContentRight( navigationViewRight );
	    }


    }

    protected void setHomeUpIndicator() {
        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mToolBar.setTitleTextColor( ContextCompat.getColor( this, android.R.color.white));
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setHomeUpIndicator(int colorId) {
        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mToolBar.setTitleTextColor( ContextCompat.getColor( this, colorId));
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setHomeUpButtonColor(int colorId) {
        final Drawable upArrow = ContextCompat.getDrawable( this, R.drawable.ic_menu);

    }

    protected void setLayout(int layoutId) {
        getLayoutInflater().inflate(layoutId, mLayoutContainer);
    }

    protected void initDrawer() {
       
        /*getSupportActionBar().setTitle("");*/


        // mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        initDrawerToggle();
    }

    protected void lockDrawer() {
        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout, mToolBar, R.string.app_name,
                                                   R.string.abc_action_bar_home_description) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);


            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mDrawerToggle.syncState();

    }

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {

			case android.R.id.home:
				if ( mDrawerLayout.isDrawerOpen( Gravity.RIGHT ) ) {
					mDrawerLayout.closeDrawer( Gravity.RIGHT );
				}
				else {
					if(isRightDrawerEnable)
						mDrawerLayout.openDrawer( Gravity.RIGHT );
				}
				return true;


			case R.id.ic_action_user: {
				if(isRightDrawerEnable)
					mDrawerLayout.openDrawer( GravityCompat.END ); /*Opens the Right Drawer*/
				return true;
			}

		}
		return super.onOptionsItemSelected( item );
	}



    protected void showMenuButton() {
        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_UNLOCKED);
        initDrawerToggle();
    }

    protected void showBackButton() {
        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        final Drawable upArrow = ContextCompat.getDrawable( this, R.drawable.back_icon);

        setSupportActionBar(mToolBar);
    }


    protected void hideToolBar() {
        mToolBar.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarTitle(String title) {
	    if(mTextViewTitleToolbar!=null)
		    mTextViewTitleToolbar.setText( title );
       /* if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);*/
    }

    @Override
    public void setToolbarVisibility(int value) {
        mToolBar.setVisibility(value);
    }

    @Override
    public void setToolbar() {
        setHomeUpButtonColor(android.R.color.black);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onLoginSuccess() {
        //Dummy implementation
    }

    @Override
    public void onLogoutSuccess() {
        //Dummy Implementation.
    }
	@Override
	public void onClick( View view ) {
		switch ( view.getId() ){
			case R.id.mImageViewUser :
			case android.R.id.home:

				break;
		}
	}
	// for left navigation drawer
	private void setupDrawerContentLeft( NavigationView navigationView ) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected( MenuItem menuItem ) {

						switch ( menuItem.getItemId() ) {
							//MEN
							case R.id.menuleft_men:
								Log.i( TAG, "onNavigationItemSelected: " );
								onNavigationClick.onNavigationClickListener( Constants.Navigation.MEN );
								break;
							//WOMEN
							case R.id.menuleft_women:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.WOMEN );
								break;
							//KIDS
							case R.id.menuleft_kids:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.KID );
								break;
							// Electronics
							case R.id.menuleft_electronics:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.ELECTRONICS );
								break;
							// photogallery
							case R.id.menuleft_acessories:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.ACCESSORIES );
								break;
							// settings
							case R.id.menuleft_appliences:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.APPLIENCES );
								break;
							// contacts
							case R.id.menuleft_other:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.OTHER );
								break;
						}

						/*if(fragmentManager!=null){
							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
							fragmentTransaction.replace(R.id.container_body, fragment,TAG_FRAGMENT);
							getSupportActionBar().setTitle(TAG_FRAGMENT);

							fragmentTransaction.commit();
						}
						else{
							getFramentMGR();
							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
							fragmentTransaction.replace(R.id.container_body, fragment,TAG_FRAGMENT);
							getSupportActionBar().setTitle(TAG_FRAGMENT);
							fragmentTransaction.commit();
						}*/

						menuItem.setChecked( true );
						mDrawerLayout.closeDrawers();

						return true;
					}
				} );
	}
	// for RIGHT navigation VIEW
	private void setupDrawerContentRight( NavigationView navigationView ) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected( MenuItem menuItem ) {

						switch ( menuItem.getItemId() ) {
							//ACCOUNT
							case R.id.right_nav_acc:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.MY_ACCOUNT );
								break;
							//ORDER
							case R.id.right_nav_order:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.MY_ORDER );
								break;
							//WISHLIST
							case R.id.right_nav_wishlist:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.MY_WISHLIST );
								break;
							// CART
							case R.id.right_nav_cart:
								onNavigationClick.onNavigationClickListener( Constants.Navigation.MY_CART );
								break;
							// COLLLECTION
							case R.id.right_nav_collection:

								break;

							// SHARE
							case R.id.right_nav_share:

								break;

							// SHARE
							case R.id.right_nav_rate:

								break;

						}

						/*if(fragmentManager!=null){
							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
							fragmentTransaction.replace(R.id.container_body, fragment,TAG_FRAGMENT);
							getSupportActionBar().setTitle(TAG_FRAGMENT);

							fragmentTransaction.commit();
						}
						else{
							getFramentMGR();
							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
							fragmentTransaction.replace(R.id.container_body, fragment,TAG_FRAGMENT);
							getSupportActionBar().setTitle(TAG_FRAGMENT);
							fragmentTransaction.commit();
						}*/

						menuItem.setChecked( true );
						mDrawerLayout.closeDrawers();

						return true;
					}
				} );
	}

	public interface OnNavigationClick{
		void onNavigationClickListener( int type );
	}

	public void setLeftDrawerEnable(boolean isLeftDrawerEnable){
		this.isLeftDrawerEnable = isLeftDrawerEnable;
	}

	public void setRightDrawerEnable(boolean isRightDrawerEnable){
		this.isRightDrawerEnable = isRightDrawerEnable;
	}

}
