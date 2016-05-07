package com.finalyearproject.dto;

public class UserDto {
    private int id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String phNo;
    private String email;

    public UserDto (String name, String username, String password, String address,
                    String phNo, String email){
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phNo = phNo;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
