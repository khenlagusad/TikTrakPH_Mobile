
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserRepository {

    public List<User> getAllUsers() {
        return MockData.getUsers();
    }

    public User getUserById(int userId) {
        for (User user : MockData.getUsers()) {
            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : MockData.getUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    public User login(String username, String password) {
        User user = getUserByUsername(username);

        if (user != null && user.getPasswordHash().equals(password)) {
            return user;
        }

        return null;
    }

    public boolean isUsernameTaken(String username) {
        return getUserByUsername(username) != null;
    }

    public boolean isPhoneTaken(String phone) {
        for (User user : MockData.getUsers()) {
            if (user.getPhone().equals(phone)) {
                return true;
            }
        }

        return false;
    }

    public boolean isLicenseNumberTaken(String licenseNumber) {
        for (User user : MockData.getUsers()) {
            if (user.getLicenseNumber().equalsIgnoreCase(licenseNumber)) {
                return true;
            }
        }

        return false;
    }

    public boolean isUsernameTakenByAnotherUser(
            String username,
            int currentUserId
    ) {
        for (User user : MockData.getUsers()) {
            if (user.getId() != currentUserId
                    && user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPhoneTakenByAnotherUser(
            String phone,
            int currentUserId
    ) {
        for (User user : MockData.getUsers()) {
            if (user.getId() != currentUserId
                    && user.getPhone().equals(phone)) {
                return true;
            }
        }

        return false;
    }

    public User register(
            String firstName,
            String lastName,
            String username,
            String password,
            String phone,
            String licenseNumber
    ) {
        if (isUsernameTaken(username)
                || isPhoneTaken(phone)
                || isLicenseNumberTaken(licenseNumber)) {
            return null;
        }

        int nextId = 1;

        for (User user : MockData.getUsers()) {
            if (user.getId() >= nextId) {
                nextId = user.getId() + 1;
            }
        }

        String createdAt = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
        ).format(new Date());

        User newUser = new User(
                nextId,
                firstName,
                lastName,
                username,
                password,
                phone,
                licenseNumber,
                createdAt
        );

        MockData.getUsers().add(newUser);

        return newUser;
    }

    public boolean updateProfile(
            int userId,
            String username,
            String phone,
            String newPassword
    ) {
        User user = getUserById(userId);

        if (user == null) {
            return false;
        }

        if (isUsernameTakenByAnotherUser(username, userId)
                || isPhoneTakenByAnotherUser(phone, userId)) {
            return false;
        }

        user.setUsername(username);
        user.setPhone(phone);

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPasswordHash(newPassword);
        }

        return true;
    }
}
