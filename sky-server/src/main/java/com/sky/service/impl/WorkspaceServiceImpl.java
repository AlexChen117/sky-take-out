package com.sky.service.impl;

import com.sky.mapper.WorkspaceMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/16 21:35:48
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceMapper workspaceMapper;

    /**
     * 查询今日运营数据
     *
     * @return
     */
    @Override
    public BusinessDataVO businessData(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> map = workspaceMapper.businessData(start, end);

        BusinessDataVO businessDataVO = new BusinessDataVO();
        businessDataVO.setTurnover(0.0D);
        businessDataVO.setValidOrderCount(0);
        businessDataVO.setOrderCompletionRate(0.0D);
        businessDataVO.setUnitPrice(0.0D);
        businessDataVO.setNewUsers(0);
        return businessDataVO;
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @Override
    public SetmealOverViewVO overviewSetmeals() {
        Map<String, Object> map = workspaceMapper.overviewSetmeals();
        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();
        Long sold = (Long) map.get("sold");
        Long discontinued = (Long) map.get("discontinued");
        setmealOverViewVO.setSold(Math.toIntExact(sold));
        setmealOverViewVO.setDiscontinued(Math.toIntExact(discontinued));
        return setmealOverViewVO;
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @Override
    public DishOverViewVO overviewDishes() {
        Map<String, Object> map = workspaceMapper.overviewDishes();
        Long sold = (Long) map.get("sold");
        Long discontinued = (Long) map.get("discontinued");
        DishOverViewVO dishOverViewVO = new DishOverViewVO();
        dishOverViewVO.setSold(Math.toIntExact(sold));
        dishOverViewVO.setDiscontinued(Math.toIntExact(discontinued));
        return dishOverViewVO;
    }
}
