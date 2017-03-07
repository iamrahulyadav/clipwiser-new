package com.clipwiser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.clipwiser.account.FacebookManager;
import com.clipwiser.account.LoginDialogFragment;
import com.clipwiser.account.SignupDialogFragment;
import com.clipwiser.base.BaseActivity;
import com.clipwiser.interfaces.SignInSIgnupCLickListener;
import com.clipwiser.utils.Constants;


public class WelcomeActivity extends BaseActivity implements BaseActivity.OnNavigationClick, FacebookManager.OnFacebookLoginSuccessListener, SignInSIgnupCLickListener {

    private static final String TAG = "WelcomeActivity";

    // widgets Declaration
    private AppCompatButton buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();

        //  showSplashDialog();

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

    void init() {
        buttonRegister = (AppCompatButton) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
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
            case R.id.buttonRegister:
                SignupDialogFragment.newInstance().show(getSupportFragmentManager(), SignupDialogFragment.TAG);
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

            } else {
                FacebookManager.getInstance(WelcomeActivity.this).onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFacebookLoginSuccess(String accessToken) {

    }

    @Override
    public void onFacebookFailed() {

    }

    @Override
    public void onSignInOrSignupClick(boolean isSignin) {
        if (isSignin) {
            showLoginDialog();
        } else {
            showSignUpDialog();
        }
    }

    private void showLoginDialog() {
        Log.i(TAG, "showSignUpDialog: ");
        fragment = new SignupDialogFragment().newInstance( );
        try {
            fragment.show(getSupportFragmentManager(), Constants.Dialogs.SPLASH);
        } catch (IllegalStateException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast
                    .LENGTH_SHORT).show();
        }
    }

    private void showSignUpDialog() {
        Log.i(TAG, "showLoginDialog: ");
        fragment = new LoginDialogFragment().newInstance(0, true);
        try {
            fragment.show(getSupportFragmentManager(), Constants.Dialogs.SPLASH);
        } catch (IllegalStateException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast
                    .LENGTH_SHORT).show();
        }
    }
}
