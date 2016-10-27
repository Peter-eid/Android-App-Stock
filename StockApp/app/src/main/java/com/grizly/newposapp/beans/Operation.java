
package com.grizly.newposapp.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Operation {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("qtt")
    @Expose
    private String qtt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Operation() {
    }

    /**
     * 
     * @param uid
     * @param pid
     * @param date
     * @param type
     * @param qtt
     */
    public Operation(String uid, String pid, String qtt, String type, String date) {
        this.uid = uid;
        this.pid = pid;
        this.qtt = qtt;
        this.type = type;
        this.date = date;
    }

    /**
     * 
     * @return
     *     The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    public Operation withUid(String uid) {
        this.uid = uid;
        return this;
    }

    /**
     * 
     * @return
     *     The pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * 
     * @param pid
     *     The pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    public Operation withPid(String pid) {
        this.pid = pid;
        return this;
    }

    /**
     * 
     * @return
     *     The qtt
     */
    public String getQtt() {
        return qtt;
    }

    /**
     * 
     * @param qtt
     *     The qtt
     */
    public void setQtt(String qtt) {
        this.qtt = qtt;
    }

    public Operation withQtt(String qtt) {
        this.qtt = qtt;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Operation withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public Operation withDate(String date) {
        this.date = date;
        return this;
    }

}
