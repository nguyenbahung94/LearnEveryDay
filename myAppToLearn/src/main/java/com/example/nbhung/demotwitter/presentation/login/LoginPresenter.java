package com.example.nbhung.demotwitter.presentation.login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.common.Strings;
import com.example.nbhung.demotwitter.datalayer.model.LoginResponse;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;
import com.example.nbhung.demotwitter.domain.usercases.Login;
import com.example.nbhung.demotwitter.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private Login login;

    @Inject
    public LoginPresenter(TwitterAppDataRepository twitterAppDataRepository) {
        this.login = new Login(twitterAppDataRepository);
    }

    void getLogin(String useName, String password) {
        if (validateEmail(useName) && validPassWord(password)) {
            getView().showLoading();
            disposable.add(login.param(Login.Params.forLogin(useName, password))
                    .on(Schedulers.io(), AndroidSchedulers.mainThread())
                    .excute(new LoginObserver()));
        }

    }

    void createNotification(Context context) {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(Strings.URL_OPEN_GMAIL));
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.gmail)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.gmail))
                        .setContentTitle(context.getString(R.string.title_register_success))
                        .setContentText(context.getString(R.string.message_confirm_email))
                        .setAutoCancel(false)
                        .setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, 0));
        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private boolean validateEmail(String userName) {
        if (userName.isEmpty()) {
            getView().loginFailed(ErrorType.ERROR_EMAIL_EMPTY);
            return false;
        }
       /* if (!userName.contains("@")) {
            getView().loginFailed(ErrorType.ERROR_EMAIL_NOT_CONTAIN_A);
            return false;
        }*/
        return true;
    }

    private boolean validPassWord(String passWord) {
        if (passWord.isEmpty()) {
            getView().loginFailed(ErrorType.ERROR_PASSWORD_EMPTY);
            return false;
        }
        return true;
    }

    private final class LoginObserver extends DisposableObserver<LoginResponse> {

        @Override
        public void onNext(LoginResponse loginResponse) {
            if (loginResponse.getMessage().equals(Strings.success)) {
                getView().loginSuccess(Strings.success);
            } else {
                getView().loginFailed(ErrorType.ERROR_LOGIN);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_LOGIN);
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
