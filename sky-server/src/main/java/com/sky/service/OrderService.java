package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:22:07
 */
public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO submitDTO);
}
