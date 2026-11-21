
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session;

import androidx.annotation.Nullable;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;

public class SessionManager {

    private static SessionManager instance;

    private User currentUser;

    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public void login(User user) {
        currentUser = user;
    }

    @Nullable
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}
