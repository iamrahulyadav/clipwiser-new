/*
 * Copyright © 2016, Craftsvilla.com
 * Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package com.clipwiser.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Mahesh Nayak on 15-01-2016.
 * All the common implementations of the fragments are implemented here.
 */
public abstract class BaseFragment extends Fragment {
    private int mLayoutId;
    protected String mRequestTag;

	protected LeftMenuClickListener leftMenuClickListener;
	protected RightMenuClickListener rightMenuClickListener;
	protected ToolbarListener toolbarListener;
	
	private ProgressBar progressbarLoading;
	private ProgressBar progressbarLoadMore;

	public interface LeftMenuClickListener {
		 //FOR LEFT MENU
		 void onSearchClick( String fragmentName );
		void onMenSelect();
		void onWomenSelect();
		void onKidSelect();
		void onElectronicsSelect();
		void onAcessoriesSelect();
		void onAppliencesSelect();
		void onOthersSelect();
		void onSettingsSelect();
	}

	public interface RightMenuClickListener{
		// FOR RIGHT MENU SELECTION
		void onMyAccountSelect();
		void onMyOrdersSelect();
		void onMyWishlistSelect();
		void onMyCartSelect();
		void onMyCollectionSelect();
		void onShareSelect();
		void onRateSelect();
	}

	public interface CategoryClickListener {
		/*void onCategoryClicked(HomeItem item);

		void onVendorClicked(HomeItem item);

		void onPageClicked(HomeItem item);

		void onFeedIdClick(String feedId);

		void onProductClicked(HomeItem item);

		void onFeedClicked(HomeItem item);*/
	}

	public interface ToolbarListener {
		void setToolbarTitle( String title );

		void setToolbarVisibility( int value );

		void setToolbar();

		void  onCartSelect();

		void onNotificationSelect();

		void onSearchSelect();

		void onRightNavigationSelect();
	}



    public interface LoginSuccessListener {
        void onLoginSuccess();

        void onLogoutSuccess();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            toolbarListener = (ToolbarListener) context;
	        rightMenuClickListener = (RightMenuClickListener ) context;
	        leftMenuClickListener = (LeftMenuClickListener ) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement MenuClickListener, ToolbarListener, LoginSuccessListener");
        }
    }


    protected void setEmptyView(TextView view) {
        view.setVisibility(View.VISIBLE);
        view.setText( R.string.data_not_available);
    }

    protected void setEmptyView(TextView view, String message) {
        view.setText(message);
    }

    protected void hideProgressBar() {
        if (progressbarLoading != null)
            progressbarLoading.setVisibility(View.GONE);
    }

    protected void showProgressBar() {
        if (progressbarLoading != null)
            progressbarLoading.setVisibility(View.VISIBLE);
    }

	protected void hideLoadMoreProgress() {
		if (progressbarLoadMore != null)
			progressbarLoadMore.setVisibility(View.GONE);
		if (progressbarLoading != null)
			progressbarLoading.setVisibility(View.GONE);
	}

	protected void showLoadMoreProgress() {
		if (progressbarLoadMore != null)
			progressbarLoadMore.setVisibility(View.VISIBLE);
	}

    protected void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        progressbarLoading = (ProgressBar) view.findViewById(R.id.progressbarLoading);
	    progressbarLoadMore = (ProgressBar) view.findViewById(R.id.progressbarLoadMore);
        FrameLayout fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragment_layout_container);
        inflater.inflate(mLayoutId, fragmentLayoutContainer);
        return view;
    }

    protected void setLayout(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        toolbarListener = null;
    }

	// ON OPTION MENU SELECTED
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.ic_action_notification:
				String fragmentName = Constants.EMPTY_TEXT;
				/*if (this instanceof ProductDetailFragment )
					fragmentName = Constants.FragmentNames.PRODUCT_DETAIL;
				else if (this instanceof ProductListFragment )
					fragmentName = Constants.FragmentNames.CATEGORY_PAGE;
				else if (this instanceof SearchResultsFragment )
					fragmentName = Constants.FragmentNames.SEARCH_PAGE;
				else if (this instanceof CartFragment )
					fragmentName = Constants.FragmentNames.CART_PAGE;
				else if (this instanceof HomeFragment )
					fragmentName = Constants.FragmentNames.HOME_SCREEN;
				else if (this instanceof OrdersListFragment )
					fragmentName = Constants.FragmentNames.ORDER_PAGE;
				leftMenuClickListener.onSearchClick(fragmentName);
				break;*/

			/*case R.id.action_cart:
				mMenuClickListener.onCartClick(getString(R.string.top_navigation));
				break;

			case R.id.action_account:
				mMenuClickListener.onAccountClick();
				break;

			case R.id.action_home:
				mMenuClickListener.onHomeClick();
				break;

			case R.id.action_order:
				mMenuClickListener.onOrdersClick();
				break;

			case R.id.action_logout:
				mMenuClickListener.onLogoutClick();
				break;

			case R.id.action_wishlist:
				mMenuClickListener.onMenuWishListClick();
				break;*/
		}
		return super.onOptionsItemSelected(item);
	}
}
