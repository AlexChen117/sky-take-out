package com.sky.mapper;

import com.sky.annotation.AutoFillAdd;
import com.sky.annotation.AutoFillUpdate;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    List<Employee> findEmpByPage(Employee employeeQuery);


    @AutoFillAdd
    void addEmp(Employee e);


    @AutoFillUpdate
    @Update("update employee set status=#{i} where id=#{id}")
    void changeStatus(Long id, Integer status);

    @Select("select count(*) from employee where username = #{username}")
    int selectCountByUsername(String username);

    @AutoFillUpdate
    void updateById(Employee employee);

    @Select("select id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user from employee where id=#{id}")
    Employee findEmpById(Long id);

    @Select("select count(*) from employee where username= #{username} and id != #{id}")
    int selectCountByUsernameAndNotId(String username, Long id);
}
