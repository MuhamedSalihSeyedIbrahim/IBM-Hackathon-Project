package product.spring.boot.autoconfigure.service;

import org.springframework.stereotype.Service;

import product.spring.boot.autoconfigure.Exception.SupplierServiceNotWorkingException;
import product.spring.boot.autoconfigure.model.Product;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;

import static com.cloudant.client.api.query.Expression.eq;
import org.springframework.beans.factory.annotation.Autowired;
import static com.cloudant.client.api.query.Operation.*;



// import product.spring.boot.autoconfigure.model.Cloth;
// import product.spring.boot.autoconfigure.model.Pharmacy;
// import product.spring.boot.autoconfigure.model.Product;
// import product.spring.boot.autoconfigure.model.Vegetables;

@Service
public class ProductService {

    @Autowired
    private Database db;

    @Autowired
    ObjectMapper mapper;

    public List<Product> getAll() throws IOException {
        // List<Product> allDocs =
        // db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Product.class);
        Selector selector = eq("docType", "product");
        QueryResult<Product> pdoc = db.query(new QueryBuilder(selector).build(), Product.class);
        if (pdoc.getDocs().isEmpty())
            return null;
        else
            return pdoc.getDocs();
        // return allDocs;
    }

    public Product getProduct(String id) throws IOException {
        Product product = db.find(Product.class, id);
        return product;

    }

    public String add(Product product) throws SupplierServiceNotWorkingException{
        product.setDocType("product");
        Response response = db.post(product);
        String id = response.getId();
        return id;
    }

    public Response update(Product product) {
        Response update = db.update(product);
        return update;
    }
    

    public Response deleteProduct(String id) throws IOException, SupplierServiceNotWorkingException  {
        Product product = db.find(Product.class, id);

       
        Response remove = db.remove(product.get_id(), product.get_rev());
        return remove;
    }
}