package com.clipwiser.activity;

/**
 * Created by sibaprasad on 22/12/16.
 */

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.clipwiser.R;
import com.clipwiser.adapter.HomePagerAdapter;
import com.clipwiser.fragment.ClipTabbedFragment;
import com.clipwiser.fragment.UserTabbedFragment;


/**
 * TODO
 */
public class HomeActivity extends AppCompatActivity {

	public static final String TAG = "HomeActivity";
	private DrawerLayout mDrawerLayout;
	TabLayout        tabLayout;
	HomePagerAdapter adapter;

	// widget declaration
	FloatingActionButton fabHome;
	private Toolbar        toolbar;
	private NavigationView navigationView;
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_home );

		toolbar = ( Toolbar ) findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );

		final ActionBar ab = getSupportActionBar();
		ab.setHomeAsUpIndicator( R.drawable.ic_menu );
		ab.setDisplayHomeAsUpEnabled( true );

		mDrawerLayout = ( DrawerLayout ) findViewById( R.id.drawer_layout );

		navigationView = ( NavigationView ) findViewById( R.id.nav_view );
		if ( navigationView != null ) {
			setupDrawerContent( navigationView );
		}

		ViewPager viewPager = ( ViewPager ) findViewById( R.id.viewpager );
		if ( viewPager != null ) {
			setupViewPager( viewPager );
		}

		fabHome = ( FloatingActionButton ) findViewById( R.id.fabHome );
		fabHome.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				Snackbar.make( view, "Here's a Snackbar", Snackbar.LENGTH_LONG )
						.setAction( "Action", null ).show();
			}
		} );

		tabLayout = ( TabLayout ) findViewById( R.id.tabs );
		tabLayout.setupWithViewPager( viewPager );

		viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {
				setFabIcon(position);
			}

			@Override
			public void onPageSelected( int position ) {
				setFabIcon(position);
			}

			@Override
			public void onPageScrollStateChanged( int state ) {
			}
		} );

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.menu_home, menu );
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu( Menu menu ) {
		/*switch ( AppCompatDelegate.getDefaultNightMode() ) {
			case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
				menu.findItem( R.id.menu_night_mode_system ).setChecked( true );
				break;
			case AppCompatDelegate.MODE_NIGHT_AUTO:
				menu.findItem( R.id.menu_night_mode_auto ).setChecked( true );
				break;
			case AppCompatDelegate.MODE_NIGHT_YES:
				menu.findItem( R.id.menu_night_mode_night ).setChecked( true );
				break;
			case AppCompatDelegate.MODE_NIGHT_NO:
				menu.findItem( R.id.menu_night_mode_day ).setChecked( true );
				break;
		}*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
			case android.R.id.home:
				mDrawerLayout.openDrawer( GravityCompat.START );
				return true;
			case R.id.menu_night_mode_system:
				setNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM );
				break;
			case R.id.menu_night_mode_day:
				setNightMode( AppCompatDelegate.MODE_NIGHT_NO );
				break;
			case R.id.menu_night_mode_night:
				setNightMode( AppCompatDelegate.MODE_NIGHT_YES );
				break;
			case R.id.menu_night_mode_auto:
				setNightMode( AppCompatDelegate.MODE_NIGHT_AUTO );
				break;
		}
		return super.onOptionsItemSelected( item );
	}

	private void setNightMode( @AppCompatDelegate.NightMode int nightMode ) {
		AppCompatDelegate.setDefaultNightMode( nightMode );

		if ( Build.VERSION.SDK_INT >= 11 ) {
			recreate();
		}
	}

	private void setupViewPager( ViewPager viewPager ) {
		adapter = new HomePagerAdapter( getSupportFragmentManager() );
		adapter.addFragment(  ClipTabbedFragment.newInstance(1), "Clips" );
		adapter.addFragment( ClipTabbedFragment.newInstance(2), "Social" );
		adapter.addFragment( ClipTabbedFragment.newInstance(2), "Wishlist" );
		adapter.addFragment( new UserTabbedFragment(), "TimeLine" );
		viewPager.setAdapter( adapter );
	}

	private void setupDrawerContent( NavigationView navigationView ) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected( MenuItem menuItem ) {
						menuItem.setChecked( true );
						mDrawerLayout.closeDrawers();
						return true;
					}
				} );
	}


	private void setupTabLayout( ViewPager viewPager, HomePagerAdapter adapter) {
		tabLayout.setupWithViewPager(viewPager);

		int length = tabLayout.getTabCount();
		for (int i = 0; i < length; i++) {
//			tabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabView(i));
		}
	}

	private void setFabIcon(int position){
		switch ( position ) {
			case 0:
				fabHome.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.alert_icon ) );
				break;
			case 1:
				fabHome.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.share_icon ) );
				break;
			case 2:
				fabHome.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.user_icon ) );
				break;
		}
	}
}