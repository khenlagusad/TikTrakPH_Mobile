
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<User> registeredUser =
            new MutableLiveData<>();

    private final MutableLiveData<String> registerError =
            new MutableLiveData<>();

    public LiveData<User> getRegisteredUser() {
        return registeredUser;
    }

    public LiveData<String> getRegisterError() {
        return registerError;
    }

    public void register(
            String firstName,
            String lastName,
            String username,
            String password,
            String confirmPassword,
            String phone,
            String licenseNumber
    ) {

        registeredUser.setValue(null);
        registerError.setValue(null);

        if (firstName.isEmpty() || lastName.isEmpty()
                || username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty() || phone.isEmpty()
                || licenseNumber.isEmpty()) {

            registerError.setValue("Please complete all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerError.setValue("Passwords do not match.");
            return;
        }

        if (RepositoryProvider.getUserRepository()
                .isUsernameTaken(username)) {
            registerError.setValue("Username is already taken.");
            return;
        }

        if (RepositoryProvider.getUserRepository()
                .isPhoneTaken(phone)) {
            registerError.setValue("Phone number is already registered.");
            return;
        }

        if (RepositoryProvider.getUserRepository()
                .isLicenseNumberTaken(licenseNumber)) {
            registerError.setValue("Driver's license number is already registered.");
            return;
        }

        User user = RepositoryProvider.getUserRepository().register(
                firstName,
                lastName,
                username,
                password,
                phone,
                licenseNumber
        );

        if (user == null) {
            registerError.setValue("Registration failed. Please try again.");
            return;
        }

        registeredUser.setValue(user);
    }
}
