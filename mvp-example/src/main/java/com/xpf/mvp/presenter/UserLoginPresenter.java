package com.xpf.mvp.presenter;

import com.xpf.mvp.bean.User;
import com.xpf.mvp.contract.LoginContract;
import com.xpf.mvp.listener.OnLoginListener;
import com.xpf.mvp.model.UserBusinessImpl;
import com.xpf.mvp.utils.UIUtils;

/**
 * Created by xpf on 2018/4/5 :)
 * GitHub:xinpengfei520
 * Function:Presenter的业务逻辑的具体实现
 */
public class UserLoginPresenter implements LoginContract.IUserLoginPresenter {

    private LoginContract.IUserBusiness userBusiness;
    private LoginContract.IUserLoginView userLoginView;

    // Presenter必须要能拿到View和Model的实现类
    public UserLoginPresenter(LoginContract.IUserLoginView view) {
        this.userLoginView = view;
        this.userBusiness = new UserBusinessImpl();
    }

    @Override
    public void login() {
        userLoginView.showLoading();
        userBusiness.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                UIUtils.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                UIUtils.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void clear() {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }
}
