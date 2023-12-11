package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.UserAddressMapper;
import com.sky.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressMapper userAddressMapper;

    /**
     * 查询当前登录用户的所有地址信息
     */
    @Override
    public List<AddressBook> list() {
        Long userId = BaseContext.getCurrentId();
        return userAddressMapper.listById(userId);
    }

    /**
     * 添加地址
     *
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        userAddressMapper.add(addressBook);
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @Override
    public AddressBook defaultAddress() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        return userAddressMapper.findByUserId(addressBook);
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     */
    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        userAddressMapper.update(addressBook);
        Long id = addressBook.getId();
        userAddressMapper.changeDefaultWhileHavaDefault(id);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     */
    @Override
    public void update(AddressBook addressBook) {
        userAddressMapper.update(addressBook);
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @Override
    public AddressBook findById(Long id) {
        return userAddressMapper.findById(id);
    }

    @Override
    public void delete(Long id) {
        userAddressMapper.deleteById(id);
    }


}
