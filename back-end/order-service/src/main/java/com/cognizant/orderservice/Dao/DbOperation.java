package com.cognizant.orderservice.Dao;

import java.util.List;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;
import com.cognizant.orderservice.Model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Operation.*;

@Slf4j
@Repository
public class DbOperation {

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
    public Void save(final Object object) {
        //final Database db = getDbInstance(dbName);
        db.save(object);
        log.info("You have inserted the document");
        return null;
    }

    public Void update( final Object object){
        //final Database db = getDbInstance(dbName);
        db.update(object);
        return null;
    }

    // Get an Document out of the database and deserialize the JSON into a Java type
    public <T> Object findById( final Class<T> classType, final String id) {
        //final Database db = getDbInstance(dbName);

        final Object doc = db.find(classType, id);
        log.info(doc.toString());
        return doc;
    }

    public Order getSupplierPendingRequest(String customerId) {
        //final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("customerId", customerId), eq("status", "waiting"));

       QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info("db"+doc.getDocs());
        if(doc.getDocs().isEmpty())
        return null;
        else
        return doc.getDocs().get(0);
    }

    public List<Order> getOrderOnCustomerId( String customerId) {
       // final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("customerId", customerId));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }

    public Order getOrderOnOrderId( String orderId) {
       // final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("orderId", orderId));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        if(doc.getDocs().isEmpty())
        return null;
        else
        return doc.getDocs().get(0);
    }

    public List<Order> getOrderOnSupplierId( String supplierId) {
       // final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("supplierId", supplierId));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }

    
    public List<Order> getOrderOnDeliveryId( String deliveryId) {
        //final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("deliveryId", deliveryId), eq("status", "adminApproved"));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }
    public List<Order> getDeliveredOrderOnCustomerId( String customerId) {
        //final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("customerId", customerId), eq("status", "delivered"));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }

    public List<Order> getReviewOrderOnSupplierId(String supplierId) {
        //final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("supplierId", supplierId), or(eq("status", "delivered"), eq("status","adminViewedAndSupplierPending")));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }


    public List<Order> getReviewOrderOnAdminId( String adminId) {
        //final Database db = getDbInstance(dbName);

        Selector selector = and(eq("docType", "order"), eq("adminId", adminId), or(eq("status", "delivered"),eq("status","viewedBySupplier")));

        QueryResult<Order> doc = db.query(new QueryBuilder(selector).build(), Order.class);

        log.info(doc.getDocs().toString());
        return doc.getDocs();
    }



    

}