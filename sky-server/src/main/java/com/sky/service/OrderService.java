package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:22:07
 */
public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO submitDTO);

    void paySuccess(String number);

    PageResult historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO orderDetail(Integer id);

    void cancel(Integer id);

    void repetition(Integer id);

    void reminder(Integer id);
}
