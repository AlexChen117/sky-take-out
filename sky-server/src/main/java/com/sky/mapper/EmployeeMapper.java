package com.sky.mapper;

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


    List<Employee> findEmpByPage(String name);


    void addEmp(Employee e);


    @Update("update employee set status=#{i} where id=#{id}")
    void changeStatus(Integer id, int i);

    @Select("select count(*) from employee where username = #{username}")
    int selectCountByUsername(String username);
}
