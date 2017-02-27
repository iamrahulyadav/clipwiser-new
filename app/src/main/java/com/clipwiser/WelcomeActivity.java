package com.clipwiser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clipwiser.account.FacebookManager;
import com.clipwiser.account.GoogleSignInManager;
import com.clipwiser.account.LoginDialogFragment;
import com.clipwiser.account.SignupDialogFragment;
import com.clipwiser.activity.HomeActivity;
import com.clipwiser.adapter.ImageSlidingAdapter;
import com.clipwiser.base.BaseActivity;
import com.clipwiser.interfaces.SignInSIgnupCLickListener;
import com.clipwiser.utils.CommonUtils;
import com.clipwiser.utils.Constants;
import com.clipwiser.views.viewpager_transformation.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WelcomeActivity extends BaseActivity implements BaseActivity.OnNavigationClick, GoogleSignInManager.OnGoogleSignInListener, FacebookManager.OnFacebookLoginSuccessListener, SignInSIgnupCLickListener {

    private static final String TAG = "WelcomeActivity";

    protected DialogFragment fragment;
//	private DotProgressBar dotProgressbarLoadMore;


    AppCompatButton buttonGoogle;


    AppCompatButton buttonFacebook;


    AppCompatButton buttonSignIn;


    AppCompatButton buttonSignUp;


    AppCompatTextView textViewSkipWelcome;


    ViewPager viewpagerImageSlidingWelcome;


    LinearLayout activity_welcome;

    ImageSlidingAdapter imageSlidingAdapter;

    List<String> listImage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        activity_welcome = (LinearLayout) findViewById(R.id.activity_welcome);
        viewpagerImageSlidingWelcome = (ViewPager) findViewById(R.id.viewpagerImageSlidingWelcome);
        textViewSkipWelcome = (AppCompatTextView) findViewById(R.id.textViewSkipWelcome);
        buttonGoogle = (AppCompatButton) findViewById(R.id.buttonGoogle);
        buttonFacebook = (AppCompatButton) findViewById(R.id.buttonFacebook);
        buttonSignIn = (AppCompatButton) findViewById(R.id.buttonSignIn);
        buttonSignUp = (AppCompatButton) findViewById(R.id.buttonSignUp);


        listImage.add("http://loremflickr.com/g/320/240/paris,girl/all");
        listImage.add("http://loremflickr.com/320/240/dog");
        listImage.add("http://loremflickr.com/320/240/brazil,rio");

        imageSlidingAdapter = new ImageSlidingAdapter(this, listImage, null, false);
        viewpagerImageSlidingWelcome.setAdapter(imageSlidingAdapter);
        viewpagerImageSlidingWelcome.setPageTransformer(true, new AccordionTransformer());


        textViewSkipWelcome.setOnClickListener(this);

        showSplashDialog();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideSplashDialog();
                /*if(! PreferenceManager.getInstance( WelcomeActivity.this ).isUserLoggedIn()) {
					hideSplashDialog();
					showLoginDialog();
				}
				else {
					Intent intentHome = new Intent( WelcomeActivity.this,HomeActivity.class );
					startActivity( intentHome );
					finish();
				}*/
            }
        }, 2000);

    }

    @OnClick(R.id.buttonFacebook)
    protected void onFacebookLoginClick() {
        FacebookManager.getInstance(WelcomeActivity.this).login(this, this);
    }

    @OnClick(R.id.buttonGoogle)
    protected void onGoogleLoginClick() {
        GoogleSignInManager.getInstance(WelcomeActivity.this).signIn(this);
    }


    @OnClick(R.id.buttonSignIn)
    protected void showLoginDialog() {
        Log.i(TAG, "showLoginDialog: ");
        fragment = new LoginDialogFragment().newInstance(0, true);
        try {
            fragment.show(getSupportFragmentManager(), Constants.Dialogs.SPLASH);
        } catch (IllegalStateException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast
                    .LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.buttonSignUp)
    protected void showSignUpDialog() {
        Log.i(TAG, "showSignUpDialog: ");
        fragment = new SignupDialogFragment().newInstance(0, true);
        try {
            fragment.show(getSupportFragmentManager(), Constants.Dialogs.SPLASH);
        } catch (IllegalStateException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast
                    .LENGTH_SHORT).show();
        }
    }



    protected void showSplashDialog() {
        fragment = new SplashDialodFragment();
        try {
            fragment.show(getSupportFragmentManager(), Constants.Dialogs.SPLASH);
        } catch (IllegalStateException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast
                    .LENGTH_SHORT).show();
        }
    }


    public void hideSplashDialog() {
        // DialogFragment fragment = (SplashScreenFragment) getSupportFragmentManager().findFragmentByTag(Constants.Dialogs.SPLASH);
        if (fragment != null)
            fragment.dismissAllowingStateLoss();
    }

    @Override
    public void onSearchClick(String fragmentName) {

    }

    @Override
    public void onMenSelect() {

    }

    @Override
    public void onWomenSelect() {

    }

    @Override
    public void onKidSelect() {

    }

    @Override
    public void onElectronicsSelect() {

    }

    @Override
    public void onAcessoriesSelect() {

    }

    @Override
    public void onAppliencesSelect() {

    }

    @Override
    public void onOthersSelect() {

    }

    @Override
    public void onSettingsSelect() {

    }

    @Override
    public void onMyAccountSelect() {

    }

    @Override
    public void onMyOrdersSelect() {

    }

    @Override
    public void onMyWishlistSelect() {

    }

    @Override
    public void onMyCartSelect() {

    }

    @Override
    public void onMyCollectionSelect() {

    }

    @Override
    public void onShareSelect() {

    }

    @Override
    public void onRateSelect() {

    }

    @Override
    public void onCartSelect() {

    }

    @Override
    public void onNotificationSelect() {

    }

    @Override
    public void onSearchSelect() {

    }

    @Override
    public void onRightNavigationSelect() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImageViewUser:
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.textViewSkipWelcome :
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
    }

    @Override
    public void onNavigationClickListener(int type) {

    }

    public void setStatusBarColorIfPossible(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constants.ActivityRequestCodes.RC_SIGN_IN) {
                GoogleSignInManager.getInstance(WelcomeActivity.this).onActivityResult(requestCode, resultCode, data, this);
            } else {
                FacebookManager.getInstance(WelcomeActivity.this).onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFacebookLoginSuccess(String accessToken) {
        CommonUtils.showSnackBar(activity_welcome, "Facebook token " + accessToken, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onFacebookFailed() {
        CommonUtils.showSnackBar(activity_welcome, "Facebook Sign In Faliled", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onGoogleTokenReceived(String token, String name, String email) {
        CommonUtils.showSnackBar(activity_welcome, "Google token " + token + " Name " + name + " email " + email, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onGoogleFailed() {
        CommonUtils.showSnackBar(activity_welcome, "Google Sign In Faliled ", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onSignInOrSignupClick(boolean isSignin) {
        if (isSignin) {
            showLoginDialog();
        } else {
            showSignUpDialog();
        }
    }
}
