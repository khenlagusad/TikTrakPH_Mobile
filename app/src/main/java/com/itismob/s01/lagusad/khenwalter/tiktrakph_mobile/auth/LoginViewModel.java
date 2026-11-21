
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<User> authenticatedUser =
            new MutableLiveData<>();

    private final MutableLiveData<String> loginError =
            new MutableLiveData<>();

    public LiveData<User> getAuthenticatedUser() {
        return authenticatedUser;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public void login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            authenticatedUser.setValue(null);
            loginError.setValue("Please enter your username.");
            return;
        }

        if (password == null || password.isEmpty()) {
            authenticatedUser.setValue(null);
            loginError.setValue("Please enter your password.");
            return;
        }

        User user = RepositoryProvider
                .getUserRepository()
                .login(username.trim(), password);

        if (user == null) {
            authenticatedUser.setValue(null);
            loginError.setValue("Invalid username or password.");
            return;
        }

        SessionManager.getInstance().login(user);

        loginError.setValue(null);
        authenticatedUser.setValue(user);
    }
}
