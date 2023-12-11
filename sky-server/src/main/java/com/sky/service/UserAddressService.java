package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 */
public interface UserAddressService {
    List<AddressBook> list();

    void add(AddressBook addressBook);

    AddressBook defaultAddress();


    void setDefaultAddress(AddressBook addressBook);

    void update(AddressBook addressBook);

    AddressBook findById(Long id);

    void delete(Long id);
}
