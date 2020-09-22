package com.cognizant.supplierservice.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cognizant.supplierservice.Dao.DbOperation;
import com.cognizant.supplierservice.Exception.ProductNotExistsException;
import com.cognizant.supplierservice.Model.Product;
import com.cognizant.supplierservice.Model.SupplierManagement;
import com.cognizant.supplierservice.Model.SupplierProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierManagementService {

    @Autowired
    DbOperation dbOperation;

    

    public String addSupplier(String supplierId) {

     SupplierManagement supplierData = new SupplierManagement();

          supplierData = new SupplierManagement();
         supplierData.setSupplierId(supplierId);
          supplierData.setDocType("supplier");
         HashMap<String, SupplierProduct> productList = new HashMap<String, SupplierProduct>();
         List<Product> products = dbOperation.getAllProducts();
         for(int productIndex = 0; productIndex<products.size(); productIndex++){
          SupplierProduct supplierProduct = new SupplierProduct();
          supplierProduct.setQuantity(0);
          productList.put(products.get(productIndex).get_id(),supplierProduct);
         }
         supplierData.setSupplierProducts(productList);

         dbOperation.save(supplierData);
         return ("Added Sucessfully");
    }

 

    public ArrayList<Product> getAllProducts(String supplierId){
      System.out.println("inside service");
      return dbOperation.getProductSupplier(supplierId);
    }

    public Product getProduct(String supplierId, String productId) {
      return dbOperation.getProductSupplierById(supplierId, productId);
  }
  
  public String increaseProductQuantity(String supplierId, String productId,int quantity) throws ProductNotExistsException{
    dbOperation.increaseStock(supplierId, productId,quantity);
    return "Updated Sucessfully";
  }

  public String decreaseProductQuantity(String supplierId, String productId,int quantity) throws ProductNotExistsException{
    dbOperation.decreaseStock(supplierId, productId,quantity);
    return "Updated Sucessfully";
  }

  public String addProduct(String productId) throws ProductNotExistsException{
    dbOperation.addProduct(productId);
    return "Added Sucessfully";
  }

  public String deleteProduct(String productId ){
    dbOperation.deleteProduct(productId);
    return "Deleted Sucessfully";
  }
  
   
  

   }

