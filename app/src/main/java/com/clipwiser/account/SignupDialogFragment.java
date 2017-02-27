package com.clipwiser.account;

/**
 * Created by sibaprasad on 20/10/16.
 */

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.clipwiser.R;
import com.clipwiser.WelcomeActivity;
import com.clipwiser.utils.CommonUtils;
import com.clipwiser.utils.Constants;
import com.clipwiser.views.custom_progressbar.SmoothProgressBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sibaprasad on 4/2/2016.
 */
public class SignupDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 9999;
    public static String TAG = "Signup";
    LinearLayout linearLayoutRootSignup;
    AppCompatEditText editTextEmailSignup;
    View viewLineEmailSignup;
    AppCompatEditText editTextPwdSignup;
    int viewLinePwdSignup;

    //FIREBASE

    //Widgets Initializaion using Buffer knife
    AppCompatEditText editTextFullNameSignup;
    View viewLineNameSignup;
    AppCompatEditText editTextMobileSignup;
    View viewLineMobileSignup;
    RadioGroup radioGroupGender;
    RadioButton radiobuttonFemale;
    RadioButton radiobuttonMale;
    AppCompatImageView imageViewBackToolbar;
    AppCompatTextView textViewTitleToolbarWithBack;
    SmoothProgressBar smoothProgressbarSignup;
    AppCompatButton buttonSignUp;
    private Context context;
    //=========OTHER CONSTANTS============
    private int fromWhichScreen = 0;
    private boolean isLoginScreen = false;
    private String strEmail;
    private String strPwd;
    private Animation animation;

    public static SignupDialogFragment newInstance(int fromWhichScreen, boolean isLoginScreen) {

        Bundle args = new Bundle();
        args.putInt(Constants.BundleKeys.FROM_WHICH_SCREEN, fromWhichScreen);
        args.putBoolean(Constants.BundleKeys.LOGIN_SCREEN, isLoginScreen);
        SignupDialogFragment fragmentLogIn = new SignupDialogFragment();
        fragmentLogIn.setArguments(args);
        Log.i(TAG, "newInstance: " + fromWhichScreen);
        return fragmentLogIn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.SplashScreenDialogTheme);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.et_anim);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootview);

//		Utility.setStatusBarColorIfPossible( getActivity(), R.color.colorPrimary);
        //Get Firebase auth instance
//		auth = FirebaseAuth.getInstance();

        init(rootview);

        askRuntimePermission();

        // get bundle values
        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.BundleKeys.FROM_WHICH_SCREEN))
                fromWhichScreen = getArguments().getInt(Constants.BundleKeys.FROM_WHICH_SCREEN);
            if (getArguments().containsKey(Constants.BundleKeys.LOGIN_SCREEN))
                isLoginScreen = getArguments().getBoolean(Constants.BundleKeys.LOGIN_SCREEN);
        }

        //set ur sign in or sign up screen for user
//		setupSigninSignUpScreen(isLoginScreen);

        //Get Firebase auth instance
        //	auth = FirebaseAuth.getInstance();

        return rootview;
    }

    @OnClick(R.id.buttonSignUp)
    protected void onLoginClick() {

        if (TextUtils.isEmpty(editTextEmailSignup.getText().toString().trim())) {
            viewLineEmailSignup.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            CommonUtils.showSnackBar(linearLayoutRootSignup, "Email can't be empty", Snackbar.LENGTH_SHORT);
        } else if (!CommonUtils.isValidEmail(editTextEmailSignup.getText().toString().trim())) {
            viewLineEmailSignup.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            CommonUtils.showSnackBar(linearLayoutRootSignup, "Invalid Email id", Snackbar.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(editTextPwdSignup.getText().toString().trim())) {
            CommonUtils.showSnackBar(linearLayoutRootSignup, "Password can't be empty", Snackbar.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(editTextFullNameSignup.getText().toString().trim())) {
            viewLineNameSignup.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            CommonUtils.showSnackBar(linearLayoutRootSignup, "Name Can't be Empty", Snackbar.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(editTextMobileSignup.getText().toString().trim())) {
            viewLineMobileSignup.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            CommonUtils.showSnackBar(linearLayoutRootSignup, "Password can't be empty", Snackbar.LENGTH_SHORT);
        } else {
            smoothProgressbarSignup.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
                    getActivity().finish();
                }
            }, 2000);
        }

    }

    @OnClick(R.id.imageViewBackToolbar)
    protected void onBackImageClick() {
        dismiss();
    }

    private void init(View rootview) {

        linearLayoutRootSignup = (LinearLayout) rootview.findViewById(R.id.linearLayoutRootSignup);

        editTextEmailSignup = (AppCompatEditText) rootview.findViewById(R.id.editTextEmailSignup);

        viewLineEmailSignup = rootview.findViewById(R.id.viewLineEmailSignup);

        editTextPwdSignup = (AppCompatEditText) rootview.findViewById(R.id.editTextPwdSignup);

        viewLinePwdSignup = (R.id.viewLinePwdSignup);

        editTextFullNameSignup = (AppCompatEditText) rootview.findViewById(R.id.editTextFullNameSignup);

        viewLineNameSignup = rootview.findViewById(R.id.viewLineNameSignup);

        editTextMobileSignup = (AppCompatEditText) rootview.findViewById(R.id.editTextMobileSignup);

        viewLineMobileSignup = rootview.findViewById(R.id.viewLineMobileSignup);
        radioGroupGender = (RadioGroup) rootview.findViewById(R.id.radioGroupGender);


        radiobuttonFemale = (RadioButton) rootview.findViewById(R.id.radiobuttonFemale);

        radiobuttonMale = (RadioButton) rootview.findViewById(R.id.radiobuttonMale);

        imageViewBackToolbar = (AppCompatImageView) rootview.findViewById(R.id.imageViewBackToolbar);

        textViewTitleToolbarWithBack = (AppCompatTextView) rootview.findViewById(R.id.textViewTitleToolbarWithBack);

        smoothProgressbarSignup = (SmoothProgressBar) rootview.findViewById(R.id.smoothProgressbarSignup);

        buttonSignUp = (AppCompatButton) rootview.findViewById(R.id.buttonSignUp);

        //
    }

/*	@NonNull
    @Override
	public Dialog onCreateDialog( Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity(), getTheme()) {
			@Override
			public void onBackPressed() {
				super.onBackPressed();
			}
		};
		dialog.getWindow().requestFeature( Window.FEATURE_NO_TITLE);

		return dialog;
	}*/

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setWindowAnimations(
                    R.style.styleDialogFragment);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onDestroy() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
        getDialog().getWindow().getAttributes().windowAnimations = R.style.animationDialog;
    }

    private void setupSigninSignUpScreen(boolean isLoginScreen) {

    }

    //===============================READ PERMISSION FOR GET ACCOUNTS==============================

    private void setPrimaryEmailId() {
        String primaryEmail_id = CommonUtils.getPrimaryEmailId(getActivity());
        Log.i(TAG, "initView: " + primaryEmail_id);
        if (!TextUtils.isEmpty(primaryEmail_id)) {
            editTextEmailSignup.setText(primaryEmail_id);
            editTextEmailSignup.setSelection(editTextEmailSignup.getText().length());
        }
    }

    private void askRuntimePermission() {
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasWriteContactsPermission = getActivity().checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
            setPrimaryEmailId();
        } else {
            // do the task here
            setPrimaryEmailId();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted and do ur task here
                    setPrimaryEmailId();
                } else {
                    // Permission Denied
                    // CommonUtils.showSnackBar(mLinearLayoutOnBoardingRoot,"Read Account Permission Denied", Snackbar.LENGTH_SHORT);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBackToolbar:
                Toast.makeText(getActivity(), "Onclick Back", Toast.LENGTH_SHORT).show();
                break;
        }
    }
//=============================== END READ PERMISSION FOR GET ACCOUNTS==============================

}
