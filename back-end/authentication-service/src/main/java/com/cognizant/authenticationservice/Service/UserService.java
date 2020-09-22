package com.cognizant.authenticationservice.Service;

import java.util.ArrayList;
import java.util.List;

import com.cognizant.authenticationservice.Dao.DaoOperation;
import com.cognizant.authenticationservice.Model.Coordinates;
import com.cognizant.authenticationservice.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    DaoOperation daoOperation;

    public ArrayList<String> getMappedSupplierId(String id){
       List<User> obtainedUser = daoOperation.getMappedSuplierId(id);
        ArrayList<String> mappedId = obtainedUser.get(0).getMappedId();
        return mappedId;
    }

    public ArrayList<String> getMappedDriverId(String id){
        List<User> obtainedUser = daoOperation.getMappedDriverId(id);
         return obtainedUser.get(0).getMappedId();
     }

    public Coordinates getCoordinates(String customerId){
        List<User> obtainedUser = daoOperation.getUser(customerId);
        Coordinates coordinates = obtainedUser.get(0).getCoordinate();
        return coordinates;
    }

    public List<User> getSuppliers(String pincode){
        List<User> suppliers = daoOperation.getSuppliers(pincode);
        return suppliers;
    }

    public User getCustomer(String customerId){
        return daoOperation.getUser(customerId).get(0);
    }

    public String getAdmin(){
        return daoOperation.getAdminId().get(0).getEmailId();
    }
}