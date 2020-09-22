package com.cognizant.couchdb.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserModel extends RootModel {
   

        private String username;
    
        private String emailId;

        private String password;

        public UserModel(String _rev, String _id, String docType, String username, String emailId, String password) {
                super(_rev, _id, docType);
                this.username = username;
                this.emailId = emailId;
                this.password = password;
        }
    
}