package com.raihan.realestate.dto;

public class SignUpDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String userType;
    private boolean isRegistered;

    public boolean isRegistered() {
        if(isRegistered)
            return isRegistered;
        else return false;
    }

    public void setRegistered(boolean registered) {
        this.isRegistered = registered;
    }

    public SignUpDto() {

    }

    public SignUpDto(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public SignUpDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
