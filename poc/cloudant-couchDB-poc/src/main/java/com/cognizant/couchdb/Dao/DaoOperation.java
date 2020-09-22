package com.cognizant.couchdb.Dao;

import java.util.List;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import static com.cloudant.client.api.query.Expression.eq;

@Slf4j
@Repository
public class DaoOperation {

    @Autowired
    private CloudantClient client;

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
     * use the below method to create a query db 
     * and( gt("Movie_year", 1960),
     * eq("Person_name", "Alec Guinness"))). sort(Sort.desc("Movie_year")).
     * fields("Movie_name", "Movie_year"). build();
     */
    public <T> List<T> getAllUser(final String dbName, final Class<T> classType) {
        final Database db = getDbInstance(dbName);


       Selector selector = eq("docType", "order");

        QueryResult<T> doc = db.query(new QueryBuilder(selector).build(), classType);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }

}