package com.example.zero.bean;

/**
 * Created by jojo on 2017/11/15.
 */

public class AddressBean {

    private String name, phone, address, default_addr;
    private boolean select;
    private boolean change;

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDefaultAddr(String default_addr) {
        this.default_addr = default_addr;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDefaultAddr() {
        return default_addr;
    }

    public boolean isSelected() {
        return select;
    }

    public boolean isChanged() {
        return change;
    }
}
