package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.PageBeanVO;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    PageResult findEmpByPage(String name, Integer page, Integer pageSize);

    void addEmp(EmployeeDTO employeeDTO);

    void changeStatus(Integer id, String status);
}
