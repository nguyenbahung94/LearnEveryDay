package com.ekakashi.greenhouse.features.login_screen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.database.DatabaseHelper;
import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.database.model_server_response.LoginResponse;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.forget_password.ForgotPasswordActivity;
import com.ekakashi.greenhouse.features.register.RegisterActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginInterface.View {
    private static final String TAG = "LoginActivity";
    private final int LOGIN_EMAIL = 1;
    private final int LOGIN_FACEBOOK = 2;
    private final int LOGIN_GOOGLE = 3;
    private final int LOGIN_TWITTER = 4;
    private int loginType = 0;
    private LoginInterface.Presenter mPresenter;
    private DatabaseHelper mDatabaseHelper;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private CallbackManager callbackManager;
    private LoginButton btnLoginFacebook;
    private GoogleSignInClient mGoogleSignInClient;
    private TwitterAuthClient authClient;
    private TwitterSession twitterSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabaseHelper = App.getDatabaseHelper(getApplicationContext());
        initPresenter();
        initView();
        setUpSocialNetwork();

        if (!Prefs.getInstance(this).getToken().isEmpty()) {
            startActivity(new Intent(this, EKMainFieldActivity.class));
        } /*else {
            if (!Prefs.getInstance(this).getUserAccount().equals("")) {
                mEmailView.setText(Prefs.getInstance(this).getUserAccount());
            }
        }*/
    }

    private void initView() {
        // Set up the login form.
        mEmailView = findViewById(R.id.edEmail);
        mPasswordView = findViewById(R.id.edPassword);

        //mEmailView.setText("user_test7@tma.com.vn");
        //mPasswordView.setText("Ek@kash1!125");
        TextView tvForgotPassword = findViewById(R.id.tvForgotPass);
        TextView btnRegister = findViewById(R.id.btnRegister);
        Button mEmailSignInButton = findViewById(R.id.btnLogin);
        ImageView btnFacebook = findViewById(R.id.imgFacebook);
        ImageView btnGoogle = findViewById(R.id.imgGoogle);
        ImageView btnTwitter = findViewById(R.id.imgTwitter);
        btnLoginFacebook = findViewById(R.id.btnLoginFacebook);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Forgot password Button
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordScreen();
            }
        });

        //Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterScreen();
            }
        });

        //Sign in Email Button
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        //Sign in Facebook Button
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginType = LOGIN_FACEBOOK;
                //btnLoginFacebook.performClick();
                //loginFacebook();
                showSimpleMessage(getString(R.string.message_next_phase));
            }
        });

        //Sign in Google Button
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginType = LOGIN_GOOGLE;
                //loginGoogle();
                showSimpleMessage(getString(R.string.message_next_phase));
            }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginType = LOGIN_TWITTER;
                //loginTwitter();
                showSimpleMessage(getString(R.string.message_next_phase));
            }
        });
    }

    private void setUpSocialNetwork() {
        //Facebook
        //  FacebookSdk.sdkInitialize(getApplicationContext());


        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Twitter
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.CONSUMER_KEY),
                        getResources().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

    }

    private void loginTwitter() {
        Callback<TwitterSession> callback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LoginActivity.this, "email: " + result.data.getUserName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("loginTwitter", exception.getMessage());
            }
        };
        authClient = new TwitterAuthClient();
        authClient.authorize(this, callback);
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LOGIN_GOOGLE);
    }

    private void loginFacebook() {
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("loginFacebook", "onSuccess");
                String accessToken = loginResult.getAccessToken().getToken();
                Log.e("loginFacebook", accessToken);
                Toast.makeText(LoginActivity.this, "loginFacebook: " + loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("LoginActivity", response.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields",
                        "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e("loginFacebook", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("loginFacebook", "onCancel");
                Log.e("loginFacebook", error.getCause().toString());
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_email_is_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mPresenter.isEmailValid(email)) {
            //mEmailView.setError(getString(R.string.error_invalid_email));
            customConfirmDialog(getString(R.string.message_incorrect_email), getString(R.string.message_require_format_email));
            focusView = mEmailView;
            cancel = true;
        }else if (!TextUtils.isEmpty(password) && !mPresenter.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            //customConfirmDialog(getString(R.string.message_incorrect_password), getString(R.string.message_please_insert_valid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(TextUtils.isEmpty(password))
        {
            customConfirmDialog(getString(R.string.message_incorrect_password), getString(R.string.message_please_insert_valid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if (isNetworkOffline()) {
                return;
            }
            showLoadingDialog(getString(R.string.message_please_wait));
            mPresenter.doLoginByEmail(email, password);
        }
    }

    @Override
    public void loginSuccessful(LoginResponse response) {
        String token = String.format("%s %s", response.tokenType, response.accessToken);
        Prefs.getInstance(this).saveToken(token);
        Prefs.getInstance(this).saveRefreshToken(response.refreshToken);
        Prefs.getInstance(this).saveUserId(response.userLogin.id);
        Prefs.getInstance(this).saveUserAccount(mEmailView.getText().toString().trim());
        Log.e(TAG, token);
        mPresenter.getAccountUser();
    }

    @Override
    public void loginFail(String error) {
        hideLoadingDialog();
        if (error != null) {
            if (error.trim().equalsIgnoreCase(getString(R.string.message_user_password_incorrect))) {
                customConfirmDialog(getString(R.string.message_incorrect_password), getString(R.string.message_please_insert_valid_password));
            } else if (error.trim().equalsIgnoreCase(getString(R.string.internal_server_error))) {
                customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
            } else {
                customConfirmDialog(getString(R.string.message_incorrect_email), getString(R.string.message_please_insert_valid_email));
            }
        }
    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void showRegisterScreen() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void showForgotPasswordScreen() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    @Override
    public void onSendDeviceTokenSuccess() {
        hideLoadingDialog();
        Prefs.getInstance(getApplicationContext()).saveSendDeviceTokenStatus(true);
        startActivity(new Intent(this, EKMainFieldActivity.class));
    }

    @Override
    public void onSendDeviceTokenFail(String error) {
        hideLoadingDialog();
        startActivity(new Intent(this, EKMainFieldActivity.class));
        Prefs.getInstance(this).saveSendDeviceTokenStatus(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public AccountDao getAccountDao() {
        return mDatabaseHelper.getAccountDao();
    }

    @Override
    public void onGetAccountSuccess() {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("LoginActivity", "deviceToken: " + deviceToken);
        Prefs.getInstance(this).saveDeviceToken(deviceToken);
        mPresenter.sendDeviceToken(getApplicationContext(), deviceToken);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        mGoogleSignInClient.signOut();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (loginType) {
            case LOGIN_FACEBOOK:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case LOGIN_GOOGLE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
            case LOGIN_TWITTER:
                authClient.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                Log.e("Login", "loginType not exist");
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("LoginGoogle", "name: " + account.getDisplayName());
            Toast.makeText(this, "loginGoogle: " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            Log.w("GoogleSignInAccount", "signInResult:editFailed code=" + e.getStatusCode());
        }
    }

}

