package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    @Select("select * from address_book where user_id=#{userId}")
    List<AddressBook> listById(Long userId);

    void add(AddressBook addressBook);

    AddressBook findByUserId(AddressBook addressBook);

    void update(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook findById(Long id);

    @Delete("delete from address_book where id = #{id};")
    void deleteById(Long id);

    @Update("update address_book set is_default = 0 where id != #{id}")
    void changeDefaultWhileHavaDefault(Long id);
}
