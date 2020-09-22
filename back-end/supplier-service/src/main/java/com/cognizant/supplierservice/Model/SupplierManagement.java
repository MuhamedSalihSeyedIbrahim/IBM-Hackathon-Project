package com.cognizant.supplierservice.Model;

import java.util.HashMap;

import lombok.Data;

@Data
public class SupplierManagement {
    	/** 
	 * Revision of the document in the cloudant database. 
	 * Cloudant will create this value for new documents.
	 */
	private String _rev;

	/** 
	 * ID of the document in the cloudant database  
	 * Cloudant will create this value for new documents.
	 */
    private String _id;

    private String docType;


    private String supplierId;

    // private String productId;

    private HashMap<String,SupplierProduct> supplierProducts;
    
}