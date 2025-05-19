package com.ride.service;

import com.ride.entity.User;
import com.ride.repository.UserRepository;
public class UserService {

    private UserRepository urepo=new UserRepository();

    // public void printU(){
    //     urepo.printU();
    // }
    public User login(String username,String password){
        return urepo.login(username, password);
    }

    public boolean createUser(String username,String password,String contactNumber){
        return urepo.createUser(username, password, contactNumber);
    }
}
