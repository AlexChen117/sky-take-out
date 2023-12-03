package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.*;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!s.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 分页查询
     *
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findEmpByPage(String name, Integer page, Integer pageSize) {
        // 设置当前页和 记录数  [PageHelper]
        PageHelper.startPage(page, pageSize);
        // 根据条件查询数据
        List<Employee> employeeList = employeeMapper.findEmpByPage(name);
        // 插件会帮我们把数据封装到一个Page对象中
        Page<Employee> p = (Page<Employee>) employeeList;
        // 从Page[也是属于分页插件的类]对象中获取 总条数 以及 分页数据,把数据封装到PageBean里面 ，返回即可
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 添加员工
     *
     * @param employeeDTO
     */
    @Override
    public void addEmp(EmployeeDTO employeeDTO) {
        //信息校验
        checkInfo(employeeDTO);
        Employee e = new Employee();
        //属性拷贝
        BeanUtils.copyProperties(employeeDTO, e);
        e.setCreateTime(LocalDateTime.now());
        e.setUpdateTime(LocalDateTime.now());
        //常量设定员工初始状态
        e.setStatus(StatusConstant.ENABLE);
        //默认密码MD5加密,默认密码常量使用
        e.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //获取请求头里的token,然后解析id
        Long empId = BaseContext.getCurrentId();
        e.setCreateUser(empId);
        e.setUpdateUser(empId);
        employeeMapper.addEmp(e);

    }

    /**
     * 启用、禁用员工账号
     *
     * @param id
     * @param status
     */
    @Override
    public void changeStatus(Integer id, String status) {
        employeeMapper.changeStatus(id, Integer.parseInt(status));
    }

    /**
     * 信息校验
     *
     * @param employeeDTO
     */
    private void checkInfo(EmployeeDTO employeeDTO) {
        //用户名校验
        String username = employeeDTO.getUsername();
        if (Objects.isNull(username) || username.length() < 3 || username.length() > 120) {
            throw new AccountException("账号输入错误,请输入3-20字符");
        }
        int count = employeeMapper.selectCountByUsername(username);
        if (count > 0) {
            throw new AccountException("账号已存在,请重新输入");
        }
        //手机号校验
        String phone = employeeDTO.getPhone();
        Pattern patternPhone = Pattern.compile("^1[3456789]\\d{9}$");
        Matcher matcherPhone = patternPhone.matcher(phone);
        if (Objects.isNull(phone) || phone.length() != 11 || !matcherPhone.matches()) {
            throw new PhoneException("请输入正确的手机号");
        }
        //身份证号校验
        String id = employeeDTO.getIdNumber();
        Pattern patternID = Pattern.compile("^\\d{17}[\\d|X]$");
        Matcher matcherID = patternID.matcher(id);
        if (Objects.isNull(id) || id.length() != 18 || !matcherID.matches()) {
            throw new IDNumberException("请输入正确的身份证号");
        }
    }
}
