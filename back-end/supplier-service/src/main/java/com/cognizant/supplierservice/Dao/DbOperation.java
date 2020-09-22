package com.cognizant.supplierservice.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;
import com.cognizant.supplierservice.Exception.ProductNotExistsException;
import com.cognizant.supplierservice.Model.Product;
import com.cognizant.supplierservice.Model.SupplierManagement;
import com.cognizant.supplierservice.Model.SupplierProduct;

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
        // final Database db = getDbInstance(dbName);
        db.save(object);
        log.info("You have inserted the document");
        return null;
    }

    public Void update(final String dbName, final Object object) {
        final Database db = getDbInstance(dbName);
        db.update(object);
        return null;
    }

    // Get an Document out of the database and deserialize the JSON into a Java type
    public <T> Object findById(final Class<T> classType, final String id) {
        // final Database db = getDbInstance(dbName);

        final Object doc = db.find(classType, id);
        log.info(doc.toString());
        return doc;
    }

    public Product getProductSupplierById(String supplierId, String productId) {
        final Database db = getDbInstance("mobile_market");
        Selector selector = and(eq("docType", "supplier"), eq("supplierId", supplierId));

        QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        SupplierManagement supplierManagement = new SupplierManagement();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagement = doc.getDocs().get(0);
        }
        HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();

        selector = and(eq("docType", "product"), eq("_id", productId));
        QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
        Product product = pdoc.getDocs().get(0);
        product.setQuantity(supplierProductMap.get(productId).getQuantity());

        return product;
    }

    public ArrayList<Product> getProductSupplier(String supplierId) {
        final Database db = getDbInstance("mobile_market");
        Selector selector = and(eq("docType", "supplier"), eq("supplierId", supplierId));

        QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        SupplierManagement supplierManagement = new SupplierManagement();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagement = doc.getDocs().get(0);
        }

        HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();
        System.out.println("inside db2" + supplierManagement.toString());
        ArrayList<String> productIds = new ArrayList<String>();
        for (String key : supplierProductMap.keySet()) {
            System.out.println(key);
            productIds.add(key);
        }

        ArrayList<Product> products = new ArrayList<Product>();

        for (int i = 0; i < productIds.size(); i++) {
            selector = and(eq("docType", "product"), eq("_id", productIds.get(i)));
            QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
            Product product = pdoc.getDocs().get(0);
            product.setQuantity(supplierProductMap.get(productIds.get(i)).getQuantity());
            products.add(product);
        }

        System.out.println("djkhfjdb" + supplierManagement.toString());
        return products;
    }


    public Void increaseStock(String supplierId, String productId,int quantity) throws ProductNotExistsException{
        final Database db = getDbInstance("mobile_market");

        Selector selector = and(eq("docType", "product"), eq("_id", productId));
            QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
            if (pdoc.getDocs().isEmpty()) {
                throw new  ProductNotExistsException();
            } 

         selector = and(eq("docType", "supplier"), eq("supplierId", supplierId));

        QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        SupplierManagement supplierManagement = new SupplierManagement();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagement = doc.getDocs().get(0);
        }
        HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();
        supplierProductMap.get(productId).setQuantity((supplierProductMap.get(productId).getQuantity())+quantity);
        supplierManagement.setSupplierProducts(supplierProductMap);
        db.update(supplierManagement);
        return null;
    }

    public Void decreaseStock(String supplierId, String productId,int quantity) throws ProductNotExistsException{
        final Database db = getDbInstance("mobile_market");
        Selector selector = and(eq("docType", "product"), eq("_id", productId));
            QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
            if (pdoc.getDocs().isEmpty()) {
                throw new  ProductNotExistsException();
            } 
         selector = and(eq("docType", "supplier"), eq("supplierId", supplierId));

        QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        SupplierManagement supplierManagement = new SupplierManagement();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagement = doc.getDocs().get(0);
        }
        HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();
        int quantityCalculated = (supplierProductMap.get(productId).getQuantity())-quantity;

        if((quantityCalculated) < 0){
            quantityCalculated = 0;
        }

        supplierProductMap.get(productId).setQuantity(quantityCalculated);
        supplierManagement.setSupplierProducts(supplierProductMap);
        db.update(supplierManagement);
        return null;
    }

    public Void addProduct(String productId) throws ProductNotExistsException{
        final Database db = getDbInstance("mobile_market");

        Selector selector = and(eq("docType", "product"), eq("_id", productId));
            QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
            if (pdoc.getDocs().isEmpty()) {
                throw new  ProductNotExistsException();
            } 


         selector = eq("docType", "supplier");

            QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        List<SupplierManagement> supplierManagements = new ArrayList<SupplierManagement>();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagements = doc.getDocs();
        }

        for(int supplierIndex = 0; supplierIndex < supplierManagements.size(); supplierIndex++){
            SupplierManagement supplierManagement = new SupplierManagement();
            supplierManagement = supplierManagements.get(supplierIndex);
            HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();
            SupplierProduct supplierProduct = new SupplierProduct();
             supplierProduct.setQuantity(0);
            supplierProductMap.put(productId, supplierProduct);
            supplierManagement.setSupplierProducts(supplierProductMap);
            db.update(supplierManagement);
        }
        return null;
    }

    public Void deleteProduct(String productId){
        final Database db = getDbInstance("mobile_market");
        Selector selector = eq("docType", "supplier");

        QueryResult<SupplierManagement> doc = db.query(new QueryBuilder(selector).build(), SupplierManagement.class);
        List<SupplierManagement> supplierManagements = new ArrayList<SupplierManagement>();
        if (doc.getDocs().isEmpty()) {
            return null;
        } else {
            supplierManagements = doc.getDocs();
        }
        for(int supplierIndex = 0; supplierIndex < supplierManagements.size(); supplierIndex++){
            SupplierManagement supplierManagement = new SupplierManagement();
            supplierManagement = supplierManagements.get(supplierIndex);
            HashMap<String, SupplierProduct> supplierProductMap = supplierManagement.getSupplierProducts();
            supplierProductMap.remove(productId);
            supplierManagement.setSupplierProducts(supplierProductMap);
            db.update(supplierManagement);
        }
        return null;
    }

    public List<Product> getAllProducts(){
    Selector selector = eq("docType", "product");
    QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
    if(pdoc.getDocs().isEmpty())
    return null;
    else
    return pdoc.getDocs();
    }

 

}

