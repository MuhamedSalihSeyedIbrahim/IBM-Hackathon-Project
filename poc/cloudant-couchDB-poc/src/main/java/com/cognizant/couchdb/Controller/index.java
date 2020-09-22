package com.cognizant.couchdb.Controller;

import com.cognizant.couchdb.Dao.DaoOperation;
import com.cognizant.couchdb.Model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class index {

    @Autowired
    private DaoOperation service;

    @PostMapping("/createuser")
    public String createUser(@RequestBody UserModel user) {
        service.save("user", user);
        return "UserCreated";
    }

    @GetMapping("/getalluser")
    public String getAllUser( ) {
        return service.getAllUser("user", UserModel.class).toString();
    }

}