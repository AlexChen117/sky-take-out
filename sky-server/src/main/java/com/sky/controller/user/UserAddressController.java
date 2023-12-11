package com.sky.controller.user;


import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿
 */
@RestController
@Slf4j
@RequestMapping("/user/addressBook")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;

    /**
     * 地址列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        log.info("地址列表");
        List<AddressBook> addressBooks = userAddressService.list();
        return Result.success(addressBooks);
    }

    /**
     * 添加地址
     *
     * @return
     */
    @PostMapping
    public Result<?> add(@RequestBody AddressBook addressBook) {
        log.info("添加地址");
        userAddressService.add(addressBook);
        return Result.success();
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> defaultAddress() {
        log.info("查询默认地址");
        AddressBook addressBook = userAddressService.defaultAddress();
        return Result.success(addressBook);
    }

    /**
     * 设置默认地址
     *
     * @return
     */
    @PutMapping("/default")
    public Result<?> setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址");
        userAddressService.setDefaultAddress(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> findById(@PathVariable Long id) {
        log.info("根据id查询地址");
        AddressBook addressBook = userAddressService.findById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     * @return
     */

    @PutMapping
    public Result<?> update(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址");
        System.out.println(addressBook);
        userAddressService.update(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     *
     * @return
     */
    @DeleteMapping
    public Result<?> delete(Long id) {
        log.info("根据id删除地址");
        userAddressService.delete(id);
        return Result.success();
    }
}
