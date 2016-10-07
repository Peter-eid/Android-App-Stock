
package com.grizly.newposapp.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class RegisterRequest {

    @SerializedName("user")
    @Expose
    private UserRequest user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RegisterRequest() {
    }

    /**
     * 
     * @param user
     */
    public RegisterRequest(UserRequest user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The user
     */
    public UserRequest getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(UserRequest user) {
        this.user = user;
    }

    public RegisterRequest withUser(UserRequest user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
