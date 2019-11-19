package com.learning.tacos.model;

import lombok.Data;

@Data
public class Order {
    public String name;
    public String street;
    public String city;
    public String state;
    public String zip;
    public String ccNumber;
    public String ccExpiration;
    public String ccCVV;
}
