/*
 * Copyright © 2017 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package product.spring.boot.autoconfigure.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import com.cloudant.client.api.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import product.spring.boot.autoconfigure.Exception.SupplierServiceNotWorkingException;
import product.spring.boot.autoconfigure.model.Product;
import product.spring.boot.autoconfigure.service.ProductService;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;



@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.SupplierService.addSupplierProductUri}")
    private String addSupplierProductUri;

    @Value("${com.cognizant.SupplierService.deleteSupplierProductUri}")
    private String deleteSupplierProductUri;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Product> getAll() throws IOException {
		return productService.getAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Product getProduct(@PathVariable String id) throws IOException {
		Product a = productService.getProduct(id);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(a);
		System.out.println("cjedg " + jsonString);
		return productService.getProduct(id);

	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String add(@RequestBody Product product,@RequestHeader("Authorization") String headers) throws SupplierServiceNotWorkingException {
		//public @ResponseBody String add(@RequestBody Product product) throws SupplierServiceNotWorkingException {
		String productId = productService.add(product);
        HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
	    header.add("Authorization", headers);
	     HttpEntity<String> entity = new HttpEntity<String>(productId, header);


	  ResponseEntity<String> responseEntity = restTemplate.exchange(addSupplierProductUri, HttpMethod.POST, entity, String.class, productId);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new SupplierServiceNotWorkingException();
		}

		return productId;
	}

	@RequestMapping(method = RequestMethod.POST, value="/update", consumes = "application/json")	
	public Response update(@RequestBody Product product) {
		
		Response update  = productService.update(product);

		return update;
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id,@RequestHeader("Authorization") String headers) throws IOException,
			SupplierServiceNotWorkingException {
		
			String productId = id;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add("Authorization", headers);
			HttpEntity<String> entity = new HttpEntity<String>(productId, header);
			ResponseEntity<String> responseEntity = restTemplate.exchange(deleteSupplierProductUri, HttpMethod.DELETE, entity, String.class, productId);
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode != HttpStatus.OK) {
				throw new SupplierServiceNotWorkingException();
			}
			Response remove = productService.deleteProduct(id);
			return new ResponseEntity<String>(remove.getReason(), HttpStatus.valueOf(remove.getStatusCode()));
	}
	
}