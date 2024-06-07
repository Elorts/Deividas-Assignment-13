package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
//import com.coderscampus.assignment13.repository.AddressRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepo;

    public Address getAddress (Long userId) {

        return addressRepo.getOne(userId);
    }

    public void saveAddress(Address address) {
        addressRepo.save(address);
    }

    public void delete(Long userId) {
        addressRepo.deleteById(userId);
    }
}
