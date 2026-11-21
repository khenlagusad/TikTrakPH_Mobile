
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void loadProfile() {
        currentUser.setValue(
                SessionManager.getInstance().getCurrentUser()
        );
    }
}
