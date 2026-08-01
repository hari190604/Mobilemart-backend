package com.mobilemart.backend.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String fullName;
    private String mobileNumber;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Boolean isDefault;
}
