package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.WorkspaceMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


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
        //订单完成率
        BigDecimal round = (BigDecimal) map.get("round");
        //营业额
        BigDecimal amount = (BigDecimal) map.get("amount");
        //平均客单价
        BigDecimal avg = (BigDecimal) map.get("avg");
        //有效订单
        Long complete = (Long) map.get("complete");
        //新增用户
        Long newUser = (Long) map.get("new_user");
        BusinessDataVO businessDataVO = new BusinessDataVO();
        businessDataVO.setTurnover(amount.doubleValue());
        businessDataVO.setValidOrderCount(Math.toIntExact(complete));
        businessDataVO.setOrderCompletionRate(round.doubleValue());
        businessDataVO.setUnitPrice(avg.doubleValue());
        businessDataVO.setNewUsers(Math.toIntExact(newUser));
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

    @Override
    public OrderOverViewVO overviewOrders() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = workspaceMapper.countByMap(map);

        //待派送
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = workspaceMapper.countByMap(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = workspaceMapper.countByMap(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = workspaceMapper.countByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = workspaceMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }
}
