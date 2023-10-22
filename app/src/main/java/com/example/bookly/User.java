package com.example.bookly;

public class User {
    private String Name,Phone,Address,UserId,Image;

    public User(String name, String phone, String address, String userId,String image) {
        Name = name;
        Phone = phone;
        Address = address;
        UserId=userId;
        Image=image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
