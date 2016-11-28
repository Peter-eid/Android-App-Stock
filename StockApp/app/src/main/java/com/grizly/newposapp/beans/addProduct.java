
package com.grizly.newposapp.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class addProduct {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stock")
    @Expose
    private int stock;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("productimage")
    @Expose
    private String productimage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public addProduct() {
    }

    /**
     * 
     * @param productimage
     * @param stock
     * @param name
     * @param barcode
     */
    public addProduct(String name, int stock, String barcode, String productimage) {
        this.name = name;
        this.stock = stock;
        this.barcode = barcode;
        this.productimage = productimage;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public addProduct withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * 
     * @param stock
     *     The stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    public addProduct withStock(int stock) {
        this.stock = stock;
        return this;
    }

    /**
     * 
     * @return
     *     The barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * @param barcode
     *     The barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public addProduct withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     * 
     * @return
     *     The productimage
     */
    public String getProductimage() {
        return productimage;
    }

    /**
     * 
     * @param productimage
     *     The productimage
     */
    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public addProduct withProductimage(String productimage) {
        this.productimage = productimage;
        return this;
    }

}
