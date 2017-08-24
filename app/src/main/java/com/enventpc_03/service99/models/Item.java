package com.enventpc_03.service99.models;

import java.io.Serializable;

/**
 * Created by eNventPC-03 on 09-Aug-16.
 */
public class Item implements Serializable {
    public String sText;
    public String iD;

    public Item(String serviceName, String serviceId) {
    }

    public String getiD() {

        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public Item() {
        this.sText = sText;
        this.iD=iD;
    }

    public String getsText() {
        return sText;
    }

    public void setsText(String sText) {
        this.sText = sText;
    }

    @Override
    public boolean equals(Object o) {
        Item item = (Item) o;
        if (item.getiD()==iD)
            return true;
        else
            return false;

    }

    @Override
    public String toString() {
        return this.iD;

        // What to display in the Spinner list.
    }
}