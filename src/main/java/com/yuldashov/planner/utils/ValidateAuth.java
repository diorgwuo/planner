package com.yuldashov.planner.utils;

import com.yuldashov.planner.exceptions.UnautorizedException;
import com.yuldashov.planner.models.User;
import org.springframework.security.core.Authentication;

public class ValidateAuth {
    public static User validate(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (!(principal instanceof User user)) {
            throw new UnautorizedException();
        }
        return user;
    }
}
