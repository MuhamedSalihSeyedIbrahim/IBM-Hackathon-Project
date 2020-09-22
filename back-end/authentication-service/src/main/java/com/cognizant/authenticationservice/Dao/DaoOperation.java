package com.cognizant.authenticationservice.Dao;

import java.util.List;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;
import com.cognizant.authenticationservice.Exceptions.UserAlreadyExistsException;
import com.cognizant.authenticationservice.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Operation.and;

@Slf4j
@Repository
public class DaoOperation {

    @Autowired
    private CloudantClient client;

    @Autowired
    Database db;

    // Get a List of all the databases this Cloudant account
    public List<String> getAllDbs() {
        final List<String> databases = this.client.getAllDbs();
        log.info("All my databases : ");
        for (final String db : databases) {
            log.info(db);
        }
        return databases;
    }

    // Show the server version
    void getDbVersion() {
        log.info("Server Version: " + this.client.serverVersion());
    }

    // Delete a database we created previously.
    void deleteDB(final String dbName) {
        this.client.deleteDB(dbName);
    }

    // Create a new database.
    void createDB(final String dbName) {
        this.client.createDB(dbName);
    }

    // Get a Database instance to interact with, but don't create it if it doesn't
    Database getDbInstance(final String dbName) {
        final Database db = client.database(dbName, false);
        return db;
    }

    // Create an Doc and save it in the database
    public Void save(final String dbName, final Object object) {
        final Database db = getDbInstance(dbName);
        db.save(object);
        log.info("You have inserted the document");
        return null;
    }

    // Get an Document out of the database and deserialize the JSON into a Java type
    public <T> Object findById(final String dbName, final Class<T> classType, final String id) {
        final Database db = getDbInstance(dbName);

        final Object doc = db.find(classType, id);
        log.info(doc.toString());
        return doc;
    }

    /*
     * instead of below function keep your own function to manipulate
     * 
     * use the below method to create a query db and( gt("Movie_year", 1960),
     * eq("Person_name", "Alec Guinness"))). sort(Sort.desc("Movie_year")).
     * fields("Movie_name", "Movie_year"). build();
     */
    public <T> List<T> getAllUser(final String dbName, final Class<T> classType) {
        final Database db = getDbInstance(dbName);

        Selector selector = eq("docType", "user");

        QueryResult<T> doc = db.query(new QueryBuilder(selector).build(), classType);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }

    public List<User> getUser(String id) {

        QueryBuilder queryBuilder = new QueryBuilder(eq("emailId", id));
        QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info(obtainedUser.toString());
        List<User> user = obtainedUser.getDocs();
        return user;
    }

    public List<User> getSuppliers(String pincode) {
        QueryBuilder queryBuilder = new QueryBuilder(and(eq("pincode", pincode), eq("userType", "supplier")));
        QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info(obtainedUser.toString());
        return obtainedUser.getDocs();
    }

    public List<User> getMappedSuplierId(String id){
        QueryBuilder queryBuilder = new QueryBuilder(and(
            eq("emailId", id),
            eq("userType", "customer"))
            );
            QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info(obtainedUser.toString());
        return obtainedUser.getDocs();
    }

    public List<User> getAdminId(){
        QueryBuilder queryBuilder = new QueryBuilder(
            eq("userType", "admin")
            );
            QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info(obtainedUser.toString());
        return obtainedUser.getDocs();
    }

    public List<User> getMappedDriverId(String id){
        QueryBuilder queryBuilder = new QueryBuilder(and(
            eq("emailId", id),
            eq("userType", "supplier"))
            );
            QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info(obtainedUser.toString());
        return obtainedUser.getDocs();
    }

    public User loadUserByUsername(String emailId) throws UsernameNotFoundException {
        log.info("Email Id :" + emailId);
        QueryBuilder queryBuilder = new QueryBuilder(eq("emailId", emailId));
        QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        System.out.println("User " + obtainedUser.getDocs().toString());
        if (obtainedUser.getDocs().isEmpty()) {
            throw new UsernameNotFoundException("User not present");
        }
        User user = obtainedUser.getDocs().get(0);
        log.info("user:" + user);
        return user;

    }

    public boolean signup(User user ,PasswordEncoder passwordEncoder) throws UserAlreadyExistsException{
        QueryBuilder queryBuilder = new QueryBuilder(eq("emailId", user.getEmailId()));
        QueryResult<User> obtainedUser = db.query(queryBuilder.build(), User.class);
        log.info("User " + obtainedUser.getDocs().toString());

        if (obtainedUser.getDocs().size() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setDocType("user");
            db.save(user);
            return true;
        } else {
            throw new UserAlreadyExistsException();
        }
    }

}