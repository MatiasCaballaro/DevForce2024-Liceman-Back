package com.liceman.application.shared.application.loggeduser;

import com.liceman.application.user.domain.User;

public class UserContext {
    private static final ThreadLocal<User> USER_CONTEXT = new ThreadLocal<>();

    public static User getUser() {
        return USER_CONTEXT.get();
    }

    public static void setUser(User user) {
        USER_CONTEXT.set(user);
    }

    public static void clearUser() {
        USER_CONTEXT.remove();
    }
}