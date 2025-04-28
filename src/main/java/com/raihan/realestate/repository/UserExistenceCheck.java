package com.raihan.realestate.repository;

import com.raihan.realestate.model.User;

public interface UserExistenceCheck {
    boolean checkUserExistence(User user);
}
