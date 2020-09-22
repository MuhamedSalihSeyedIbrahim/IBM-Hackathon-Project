package com.cognizant.couchdb;

 import com.ibm.cloudant.spring.framework.EnableCloudant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 @EnableCloudant
@SpringBootApplication
public class CouchDb {

	public static void main(String[] args) {
		SpringApplication.run(CouchDb.class, args);
	}

}
