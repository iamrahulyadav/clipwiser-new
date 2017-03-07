package com.clipwiser.account;

/**
 * Created by sibaprasad on 20/10/16.
 */

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.clipwiser.R;
import com.clipwiser.utils.CommonUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sibaprasad on 4/2/2016.
 */
public class SignupDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "SignupDialogFragment";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 9999;
    private Context context;
    // widgets Initialization
    private AppCompatImageView imageViewBackToolbar;
    private AppCompatTextView textViewTitleToolbar;
    private AppCompatEditText edittextFnameRegistration;
    private AppCompatEditText edittextLnameRegistration;
    private AppCompatEditText edittextEmailRegistration;
    private AppCompatEditText edittextPwdRegistration;
    private AppCompatEditText edittextConfPwdRegistration;
    private AppCompatEditText edittextMobileRegistration;
    private AppCompatEditText edittextDOBRegistration;
    DatePickerDialog.OnDateSetListener dob = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            List<String> monthNames = Arrays.asList(getResources().getStringArray(R.array.months));
            String mDay = "", mMonth = "", mYear = "";
            int day = dayOfMonth;
            if (dayOfMonth < 10) {
                mDay = "0" + day;
            } else {
                mDay = "" + day;
            }
            int month = monthOfYear + 1;
            if (month < 10) {
                mMonth = "0" + month;
            } else {
                mMonth = "" + month;
            }
            mYear = "" + year;
            edittextDOBRegistration.setText(monthNames.get(monthOfYear) + " " + mDay + "," + year);
            //  tvDob.setText(monthNames.get(monthOfYear) + " " + mDay + "," + year);
            //  mDob = mYear + "-" + mMonth + "-" + mDay;
            //   Toast.makeText(RegistrationActivity.this, mDob, Toast.LENGTH_SHORT).show();
        }
    };
    private RadioGroup radioGroupGender;
    private RadioButton radioMale;
    private RadioButton radioFemale;

    public static SignupDialogFragment newInstance() {

        /*Bundle args = new Bundle();
        args.putInt(Constants.BundleKeys.FROM_WHICH_SCREEN, fromWhichScreen);
        args.putBoolean(Constants.BundleKeys.LOGIN_SCREEN, isLoginScreen);
        SignupDialogFragment fragmentLogIn = new SignupDialogFragment();
        fragmentLogIn.setArguments(args);
        Log.i(TAG, "newInstance: " + fromWhichScreen);*/
        return new SignupDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.SplashScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_registration, container, false);

        init(rootview);

        //  askRuntimePermission();
        return rootview;
    }

    private void init(View rootview) {
        imageViewBackToolbar = (AppCompatImageView) rootview.findViewById(R.id.imageViewBackToolbar);
        textViewTitleToolbar = (AppCompatTextView) rootview.findViewById(R.id.textViewTitleToolbar);
        edittextFnameRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextFnameRegistration);
        edittextLnameRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextLnameRegistration);
        edittextEmailRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextEmailRegistration);
        edittextPwdRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextPwdRegistration);
        edittextConfPwdRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextConfPwdRegistration);
        edittextMobileRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextMobileRegistration);
        edittextDOBRegistration = (AppCompatEditText) rootview.findViewById(R.id.edittextDOBRegistration);
        radioGroupGender = (RadioGroup) rootview.findViewById(R.id.radioGroupGender);
        radioMale = (RadioButton) rootview.findViewById(R.id.radioMale);
        radioFemale = (RadioButton) rootview.findViewById(R.id.radioFemale);

        edittextDOBRegistration.setOnClickListener(this);
    }

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

    //===============================READ PERMISSION FOR GET ACCOUNTS==============================

    private void setupSigninSignUpScreen(boolean isLoginScreen) {

    }

    private void setPrimaryEmailId() {
        String primaryEmail_id = CommonUtils.getPrimaryEmailId(getActivity());
        Log.i(TAG, "initView: " + primaryEmail_id);
        if (!TextUtils.isEmpty(primaryEmail_id)) {
            /*editTextEmailSignup.setText(primaryEmail_id);
            editTextEmailSignup.setSelection(editTextEmailSignup.getText().length());*/
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
            case R.id.edittextDOBRegistration:
                getDob();
                break;
        }
    }

    //=============================== END READ PERMISSION FOR GET ACCOUNTS==============================
    private void getDob() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dob, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
