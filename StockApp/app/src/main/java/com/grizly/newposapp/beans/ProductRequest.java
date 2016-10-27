
package com.grizly.newposapp.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductRequest {

    @SerializedName("product")
    @Expose
    private addProduct product;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductRequest() {
    }

    /**
     * 
     * @param product
     */
    public ProductRequest(addProduct product) {
        this.product = product;
    }

    /**
     * 
     * @return
     *     The product
     */
    public addProduct getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(addProduct product) {
        this.product = product;
    }

    public ProductRequest withProduct(addProduct product) {
        this.product = product;
        return this;
    }

}
