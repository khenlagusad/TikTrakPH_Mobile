
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccessful = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getUpdateSuccessful() {
        return updateSuccessful;
    }

    public void saveProfile(
            String username,
            String phone,
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
        User user = SessionManager.getInstance().getCurrentUser();

        if (user == null) {
            errorMessage.setValue("Your session has expired. Please log in again.");
            return;
        }

        username = username.trim();
        phone = phone.trim();

        if (username.isEmpty() || phone.isEmpty()) {
            errorMessage.setValue("Username and phone number are required.");
            return;
        }

        if (RepositoryProvider.getUserRepository()
                .isUsernameTakenByAnotherUser(username, user.getId())) {
            errorMessage.setValue("That username is already taken.");
            return;
        }

        if (RepositoryProvider.getUserRepository()
                .isPhoneTakenByAnotherUser(phone, user.getId())) {
            errorMessage.setValue("That phone number is already registered.");
            return;
        }

        boolean changingPassword = !newPassword.isEmpty()
                || !confirmPassword.isEmpty()
                || !currentPassword.isEmpty();

        if (changingPassword) {
            if (currentPassword.isEmpty()) {
                errorMessage.setValue("Enter your current password to change it.");
                return;
            }

            if (!user.getPasswordHash().equals(currentPassword)) {
                errorMessage.setValue("Your current password is incorrect.");
                return;
            }

            if (newPassword.isEmpty()) {
                errorMessage.setValue("Enter your new password.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                errorMessage.setValue("New passwords do not match.");
                return;
            }
        }

        String passwordToSave = changingPassword ? newPassword : null;

        boolean updated = RepositoryProvider.getUserRepository()
                .updateProfile(
                        user.getId(),
                        username,
                        phone,
                        passwordToSave
                );

        if (updated) {
            updateSuccessful.setValue(true);
        } else {
            errorMessage.setValue("Unable to update your profile. Please try again.");
        }
    }
}
