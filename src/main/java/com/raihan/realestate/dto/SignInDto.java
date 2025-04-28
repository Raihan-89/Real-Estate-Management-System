package com.raihan.realestate.dto;

public class SignInDto {
    private String email;
    private String password;
    private boolean signUpSuccess;
    private boolean isSignInFail;

    public SignInDto() {

    }

    public SignInDto(boolean isSignInFail) {
        this.isSignInFail = isSignInFail;
    }


    public SignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isSignInFail() {
        return isSignInFail;
    }

    public void setSignInFail(boolean signInFail) {
        isSignInFail = signInFail;
    }

    public boolean isSignUpSuccess() {
        return signUpSuccess;
    }
    public void setSignUpSuccess(boolean signUpSuccess) {
        this.signUpSuccess = signUpSuccess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
