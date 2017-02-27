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
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.clipwiser.R;
import com.clipwiser.WelcomeActivity;
import com.clipwiser.interfaces.SignInSIgnupCLickListener;
import com.clipwiser.utils.CommonUtils;
import com.clipwiser.utils.Constants;
import com.clipwiser.views.custom_progressbar.SmoothProgressBar;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Sibaprasad on 4/2/2016.
 */
public class LoginDialogFragment extends DialogFragment implements GoogleSignInManager.OnGoogleSignInListener, FacebookManager.OnFacebookLoginSuccessListener {

	private static final int    REQUEST_CODE_ASK_PERMISSIONS = 9999;
	public static String TAG = "Signin";

	BottomSheetDialog mBottomSheetDialog;

	@BindView( R.id.relativeRootSignin )
	RelativeLayout relativeRootSignin;

	@BindView( R.id.editTextEmailSignIn )
	AppCompatEditText editTextEmailSignIn;

	@BindView( R.id.viewLineEmailSignIn )
	View              viewLineEmailSignIn;

	@BindView( R.id.editTextPwdSignIn )
	AppCompatEditText editTextPwdSignIn;

	@BindView( R.id.viewLinePwdSignIn )
	View  viewLinePwdSignIn;

	@BindView( R.id.smoothProgressbar )
	SmoothProgressBar smoothProgressbar;

	@BindView( R.id.textViewSignUp )
	AppCompatTextView textViewSignUp;

	@BindView( R.id.textViewForgotPwdLogin )
	AppCompatTextView textViewForgotPwdLogin;

	@BindView( R.id.buttonGoogle )
	AppCompatButton buttonGoogle;

	@BindView( R.id.buttonFacebook )
	AppCompatButton buttonFacebook;

	@BindView( R.id.buttonSignInMain )
	AppCompatButton buttonSignInMain;

	@BindView( R.id.buttonSignUp )
	AppCompatButton buttonSignUp;

	@BindView( R.id.relativelayoutLoginSignupCOntainer )
	RelativeLayout    relativelayoutLoginSignupCOntainer;

	@BindView( R.id.imageViewBackToolbar )
	AppCompatImageView imageViewBackToolbar;

	@BindView( R.id.textViewTitleToolbarWithBack )
	AppCompatTextView textViewTitleToolbarWithBack;

	private Context context;
	//=========OTHER CONSTANTS============
	private int     fromWhichScreen = 0;
	private boolean isLoginScreen   = false;
	private String       strEmail;
	private String       strPwd;
	//FIREBASE
	private FirebaseAuth auth;

	private Animation animation;

	SignInSIgnupCLickListener signInSIgnupCLickListener;

	AppCompatEditText edittextEmailForgotPwd;
	TextInputLayout   textinputlayoutForgotPwd;
	ProgressBar       progressBarForgotPwd;
	AppCompatButton   buttonResetForgotPwd;

	public static LoginDialogFragment newInstance( int fromWhichScreen, boolean isLoginScreen ) {

		Bundle args = new Bundle();
		args.putInt( Constants.BundleKeys.FROM_WHICH_SCREEN, fromWhichScreen );
		args.putBoolean( Constants.BundleKeys.LOGIN_SCREEN, isLoginScreen );
		LoginDialogFragment fragmentLogIn = new LoginDialogFragment();
		fragmentLogIn.setArguments( args );
		Log.i( TAG, "newInstance: " + fromWhichScreen );
		return fragmentLogIn;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setStyle( STYLE_NO_FRAME, R.style.SplashScreenDialogTheme );
		animation = AnimationUtils.loadAnimation( getActivity(), R.anim.et_anim);
	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View rootview = inflater.inflate( R.layout.fragment_login, container, false );
		ButterKnife.bind( this, rootview );

		relativelayoutLoginSignupCOntainer.setVisibility( View.GONE );
		//Get Firebase auth instance
//		auth = FirebaseAuth.getInstance();

		init( rootview );

		askRuntimePermission();

		// get bundle values
		if ( getArguments() != null ) {
			if ( getArguments().containsKey( Constants.BundleKeys.FROM_WHICH_SCREEN ) ) {
				fromWhichScreen = getArguments().getInt( Constants.BundleKeys.FROM_WHICH_SCREEN );
			}
			if ( getArguments().containsKey( Constants.BundleKeys.LOGIN_SCREEN ) ) {
				isLoginScreen = getArguments().getBoolean( Constants.BundleKeys.LOGIN_SCREEN );
			}
		}

		//set ur sign in or sign up screen for user
//		setupSigninSignUpScreen(isLoginScreen);

		//Get Firebase auth instance
		//	auth = FirebaseAuth.getInstance();

		return rootview;
	}

	@OnClick(R.id.buttonFacebook)
	protected void onFacebookLoginClick(){
		FacebookManager.getInstance(getActivity()).login(this, this);
	}

	@OnClick(R.id.buttonGoogle)
	protected void onGoogleLoginClick(){
		GoogleSignInManager.getInstance( getActivity() ).signIn(this);
	}

	@OnClick(R.id.textViewForgotPwdLogin)
	protected void showForgotPasswordBottomshet(){
		showBottomSheetForgotPwd("");
	}
	@OnClick(R.id.textViewSignUp)
	protected void showSignup(){
		dismiss();
		signInSIgnupCLickListener.onSignInOrSignupClick( false );
	}


	@OnClick(R.id.buttonSignInMain)
	protected void onLoginClick(){

		if(TextUtils.isEmpty(  editTextEmailSignIn.getText().toString().trim())){
			viewLineEmailSignIn.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.red ) );
			CommonUtils.showSnackBar( relativeRootSignin, "Email can't be empty", Snackbar.LENGTH_SHORT );
		}
		else if(!CommonUtils.isValidEmail(  editTextEmailSignIn.getText().toString().trim())){
			viewLineEmailSignIn.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.red ) );
			CommonUtils.showSnackBar( relativeRootSignin, "Invalid Email id", Snackbar.LENGTH_SHORT );
		}
		else if(TextUtils.isEmpty(  editTextPwdSignIn.getText().toString().trim())){
			CommonUtils.showSnackBar( relativeRootSignin, "Password can't be empty", Snackbar.LENGTH_SHORT );
		}
		else {
			smoothProgressbar.setVisibility( View.VISIBLE );
			new Handler().postDelayed( new Runnable() {
				@Override
				public void run() {
					getActivity().startActivity( new Intent( getActivity(),WelcomeActivity.class ) );
					getActivity().finish();
				}
			},2000 );
		}

	}

	@OnClick(R.id.imageViewBackToolbar)
	protected void onBackImageClick(){
		dismiss();
	}

	private void init( View rootview ) {
		textViewTitleToolbarWithBack.setText( getString(R.string.signin ));
		editTextEmailSignIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					viewLineEmailSignIn.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.colorPrimaryDark));
					viewLineEmailSignIn.setVisibility(View.VISIBLE);
					viewLineEmailSignIn.startAnimation(animation);
					viewLinePwdSignIn.setBackgroundColor(Color.parseColor("#d3d3d3"));
				} else {
					viewLineEmailSignIn.setBackgroundColor(Color.parseColor("#d3d3d3"));
				}
			}
		});

		editTextPwdSignIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					viewLinePwdSignIn.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.colorPrimaryDark));
					viewLinePwdSignIn.setVisibility(View.VISIBLE);
					viewLinePwdSignIn.startAnimation(animation);
					viewLineEmailSignIn.setBackgroundColor(Color.parseColor("#d3d3d3"));
				} else {
					viewLinePwdSignIn.setBackgroundColor(Color.parseColor("#d3d3d3"));
				}
			}
		});
	}
	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if ( dialog != null ) {
			dialog.getWindow().setWindowAnimations(
					R.style.styleDialogFragment );
			dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
			dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
		}
	}

	@Override
	public void onAttach( Context activity ) {
		super.onAttach( activity );
		context = activity;
		signInSIgnupCLickListener = (SignInSIgnupCLickListener)context;
	}

	@Override
	public void onDestroy() {
		if ( getDialog() != null && getRetainInstance() ) {
			getDialog().setOnDismissListener( null );
		}
		super.onDestroy();
	}

	@Override
	public void onActivityCreated( Bundle savedInstanceState ) {
		super.onActivityCreated( savedInstanceState );
		Log.i( TAG, "onActivityCreated: " );
		getDialog().getWindow().getAttributes().windowAnimations = R.style.animationDialog;
	}

	private void setupSigninSignUpScreen( boolean isLoginScreen ) {

	}



	/*@Override
	public void onClick( View view ) {
		switch ( view.getId() ){
			case R.id.buttonSignIn :
				strEmail = editTextEmailSignIn.getText().toString().trim();
				strPwd = editTextEmailSignIn.getText().toString().trim();
				smoothProgressbar.setVisibility( View.VISIBLE );
				if(isLoginScreen){
					//authenticate user
					auth.signInWithEmailAndPassword(strEmail, strPwd)
							.addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
								@Override
								public void onComplete(@NonNull Task<AuthResult> task) {
									// If sign in fails, display a message to the user. If sign in succeeds
									// the auth state listener will be notified and logic to handle the
									// signed in user can be handled in the listener.
									smoothProgressbar.setVisibility(View.GONE);
									if (!task.isSuccessful()) {
										// there was an error
										if (strEmail.length() < 6) {

										} else {
											Toast.makeText( getActivity(), getString( R.string.auth_failed), Toast.LENGTH_LONG).show();
										}
									} else {
										Intent intentHome = new Intent( getActivity(), HomeActivity.class);
										startActivity( intentHome );
										getActivity().finish();
									}
								}
							});
				}
				else {
					//create user
					auth.createUserWithEmailAndPassword(strEmail, strPwd)
							.addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
								@Override
								public void onComplete(@NonNull Task<AuthResult> task) {
									Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
									smoothProgressbar.setVisibility(View.GONE);
									// If sign in fails, display a message to the user. If sign in succeeds
									// the auth state listener will be notified and logic to handle the
									// signed in user can be handled in the listener.
									if (!task.isSuccessful()) {
										Log.i( TAG, "onComplete: "+task.getException() );
										Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
										               Toast.LENGTH_SHORT).show();
									} else {
										Log.i( TAG, "onComplete: 123" );
										Intent intentHome = new Intent(getActivity(),HomeActivity.class);
										startActivity( intentHome );
										getActivity().finish();
									}
								}
							});
				}

				break;
			case R.id.textViewForgotPwdLogin :
				InputMethodManager inputMethodManager =
						(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInputFromWindow(
						textViewForgotPwdLogin.getApplicationWindowToken(),
						InputMethodManager.SHOW_FORCED, 0);
				showBottomSheetForgotPwd();
				break;


		}
	}*/

	private void showBottomSheetForgotPwd() {

		if ( mBottomSheetDialog == null ) {
			mBottomSheetDialog = new BottomSheetDialog( getActivity() );
		}

		//similar
		mBottomSheetDialog.setContentView( R.layout.bottomsheet_forgotpwd );

		mBottomSheetDialog.show();
	}

	//===============================READ PERMISSION FOR GET ACCOUNTS==============================

	private void setPrimaryEmailId() {
		String primaryEmail_id = CommonUtils.getPrimaryEmailId( getActivity() );
		Log.i( TAG, "initView: " + primaryEmail_id );
		/*if (!TextUtils.isEmpty( primaryEmail_id)) {
			editTextEmailSignIn.setText(primaryEmail_id);
			editTextEmailSignIn.setSelection(editTextEmailSignIn.getText().length());
		}*/
	}

	private void askRuntimePermission() {
		int hasWriteContactsPermission = 0;
		if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ) {
			hasWriteContactsPermission = getActivity().checkSelfPermission( Manifest.permission.GET_ACCOUNTS );
			if ( hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED ) {
				requestPermissions( new String[]{ Manifest.permission.GET_ACCOUNTS },
				                    REQUEST_CODE_ASK_PERMISSIONS );
				return;
			}
			setPrimaryEmailId();
		}
		else {
			// do the task here
			setPrimaryEmailId();
		}

	}

	@Override
	public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults ) {
		switch ( requestCode ) {
			case REQUEST_CODE_ASK_PERMISSIONS:
				if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
					// Permission Granted and do ur task here
					setPrimaryEmailId();
				}
				else {
					// Permission Denied
					// CommonUtils.showSnackBar(mLinearLayoutOnBoardingRoot,"Read Account Permission Denied", Snackbar.LENGTH_SHORT);
				}
				break;
			default:
				super.onRequestPermissionsResult( requestCode, permissions, grantResults );
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.i(TAG, "onActivityResult: IN ACTIVITY RESULT");

		if (requestCode == Constants.ActivityRequestCodes.RC_SIGN_IN) {
			Log.i(TAG, "onActivityResult: google ");
			GoogleSignInManager.getInstance(getContext()).onActivityResult(requestCode, resultCode, data, this);
		} else {
			Log.i(TAG, "onActivityResult: facebook ");
			FacebookManager.getInstance(getActivity()).onActivityResult(requestCode, resultCode, data);
		}


	}

	@Override
	public void onFacebookLoginSuccess( String accessToken ) {
		Log.i( TAG, "onFacebookLoginSuccess: "+accessToken );
	}

	@Override
	public void onFacebookFailed() {

	}

	@Override
	public void onGoogleTokenReceived( String token,String name,String email ) {
		Log.i( TAG, "onGoogleTokenReceived: "+token );
	}

	@Override
	public void onGoogleFailed() {

	}
//=============================== END READ PERMISSION FOR GET ACCOUNTS==============================
// hit the social api when GOOGLE access token recieved

	private void showBottomSheetForgotPwd(final String email) {


		if ( mBottomSheetDialog == null ) {
			mBottomSheetDialog = new BottomSheetDialog( getActivity() );
		}
		//similar
		mBottomSheetDialog.setContentView( R.layout.bottomsheet_forgotpwd );
		textinputlayoutForgotPwd = ( TextInputLayout ) mBottomSheetDialog.findViewById( R.id.textinputlayoutForgotPwd );
		edittextEmailForgotPwd = ( AppCompatEditText ) mBottomSheetDialog.findViewById( R.id.edittextEmailForgotPwd );
		buttonResetForgotPwd = ( AppCompatButton ) mBottomSheetDialog.findViewById( R.id.buttonResetForgotPwd );
		progressBarForgotPwd = ( ProgressBar ) mBottomSheetDialog.findViewById( R.id.progressBarForgotPwd );

		edittextEmailForgotPwd.setText( email );

		buttonResetForgotPwd.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				if(CommonUtils.isValidEmail( edittextEmailForgotPwd.getText().toString().trim())){
					textinputlayoutForgotPwd.setError( "Invalid Email" );
				}
				else{
					buttonResetForgotPwd.setVisibility( View.INVISIBLE );
					progressBarForgotPwd.setVisibility( View.VISIBLE );
					new Handler().postDelayed( new Runnable() {
						@Override
						public void run() {
							buttonResetForgotPwd.setVisibility( View.VISIBLE );
							progressBarForgotPwd.setVisibility( View.INVISIBLE );
							mBottomSheetDialog.dismiss();
						}
					},1000 );
				}
			}
		} );

		mBottomSheetDialog.show();
	}
}
