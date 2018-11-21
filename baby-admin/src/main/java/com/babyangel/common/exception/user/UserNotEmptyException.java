package com.babyangel.common.exception.user;

public class UserNotEmptyException  extends UserException{
    private static final long serialVersionUID = 1L;

    public UserNotEmptyException() {
        super("user.not.empty", null);
    }
}
