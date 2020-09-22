package product.spring.boot.autoconfigure.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    	/** 
	 * Revision of the document in the cloudant database. 
	 * Cloudant will create this value for new documents.
	 */
    private String _rev;
    
    private String docType;

	/** 
	 * ID of the document in the cloudant database  
	 * Cloudant will create this value for new documents.
	 */
    @JsonProperty(value="productId")
    private String _id;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String image;

    String displayCategory;

    String category;

    Float price;

    Integer quantity;

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDisplayCategory() {
        return displayCategory;
    }

    public void setDisplayCategory(String displayCategory) {
        this.displayCategory = displayCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

};