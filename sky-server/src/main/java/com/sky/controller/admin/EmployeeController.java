package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Result<PageResult> findEmpByPage(String name,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询");
        PageResult ps = employeeService.findEmpByPage(name, page, pageSize);
        return Result.success(ps);
    }

    /**
     * 添加员工
     *
     * @param employeeDTO
     * @return
     */
    @ApiOperation(value = "添加员工")
    @PostMapping
    public Result addEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("添加员工:{}", employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success("添加成功!");
    }

    /**
     * 启用、禁用员工账号
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "启用、禁用员工账号")
    @PostMapping("/status/{status}")
    public Result changeStatus(Integer id, @PathVariable String status) {
        log.info("启用、禁用员工账号");
        employeeService.changeStatus(id, status);
        return Result.success();
    }


}
