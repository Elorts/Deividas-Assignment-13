package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepo;

    public AddressService(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

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
