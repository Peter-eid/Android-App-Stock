
package com.grizly.newposapp.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OperationRequest {

    @SerializedName("operation")
    @Expose
    private Operation operation;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OperationRequest() {
    }

    /**
     * 
     * @param operation
     */
    public OperationRequest(Operation operation) {
        this.operation = operation;
    }

    /**
     * 
     * @return
     *     The operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * 
     * @param operation
     *     The operation
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public OperationRequest withOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

}
