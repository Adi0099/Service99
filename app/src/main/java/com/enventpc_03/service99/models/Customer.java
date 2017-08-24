package com.enventpc_03.service99.models;

/**
 * Created by eNventPC-03 on 28-Jul-16.
 */

public class Customer {
    private String jobId,serial,service,status, date;

    public Customer() {
    }

    public Customer(String jobId, String serial, String service, String status ,String date) {

        this.jobId = jobId;
        this.serial = serial;
        this.service = service;
        this.status = status;
        this.date=date;

    }

    public String getTitle() {
        return jobId;
    }
    public void setTitle(String jobId) {
        this.jobId = jobId;
    }

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }




}
