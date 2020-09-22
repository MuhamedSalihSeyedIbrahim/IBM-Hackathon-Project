// package com.cognizant.orderservice.Controller;

// import com.cognizant.orderservice.Dao.dbOperation;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class index {

//     @Autowired
//     private dbOperation service;

//     @PostMapping("/createuser")
//     public String createUser(@RequestBody UserModel user) {
//         service.save("user", user);
//         return "UserCreated";
//     }

//     @GetMapping("/getalluser")
//     public String getAllUser( ) {
//         return service.getAllUser("user", UserModel.class).toString();
//     }

// }