package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.AddressRequest;
import com.mobilemart.backend.dto.AddressResponse;
import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.entity.Address;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.AddressRepository;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ApiResponse addAddress(String username, AddressRequest request) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Address address = Address.builder()
                .user(user)
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .streetAddress(request.getStreetAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : false)
                .build();

        if (address.getIsDefault()) {
            resetDefaultAddress(user.getUserId());
        }

        address = addressRepository.save(address);
        return new ApiResponse(true, "Address added successfully", mapToDto(address));
    }

    public ApiResponse getUserAddresses(String username) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        List<Address> addresses = addressRepository.findByUser_UserId(user.getUserId());
        List<AddressResponse> dtos = addresses.stream().map(this::mapToDto).collect(Collectors.toList());
        return new ApiResponse(true, "Addresses fetched successfully", dtos);
    }

    @Transactional
    public ApiResponse updateAddress(String username, Long addressId, AddressRequest request) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Address address = addressRepository.findByAddressIdAndUser_UserId(addressId, user.getUserId()).orElse(null);
        if (address == null) {
            return new ApiResponse(false, "Address not found");
        }

        address.setFullName(request.getFullName());
        address.setMobileNumber(request.getMobileNumber());
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        
        if (request.getIsDefault() != null && request.getIsDefault() && !address.getIsDefault()) {
            resetDefaultAddress(user.getUserId());
            address.setIsDefault(true);
        } else if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }

        address = addressRepository.save(address);
        return new ApiResponse(true, "Address updated successfully", mapToDto(address));
    }

    @Transactional
    public ApiResponse deleteAddress(String username, Long addressId) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Address address = addressRepository.findByAddressIdAndUser_UserId(addressId, user.getUserId()).orElse(null);
        if (address == null) {
            return new ApiResponse(false, "Address not found");
        }

        addressRepository.delete(address);
        return new ApiResponse(true, "Address deleted successfully");
    }

    private void resetDefaultAddress(Long userId) {
        List<Address> addresses = addressRepository.findByUser_UserId(userId);
        for (Address addr : addresses) {
            if (addr.getIsDefault()) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
        }
    }

    public AddressResponse mapToDto(Address address) {
        if (address == null) return null;
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .fullName(address.getFullName())
                .mobileNumber(address.getMobileNumber())
                .streetAddress(address.getStreetAddress())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .isDefault(address.getIsDefault())
                .build();
    }
}
