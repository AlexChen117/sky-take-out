package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.mapper.UserAddressMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:22:26
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final UserAddressMapper addressMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO submitDTO) {
        //判断地址是否存在
        AddressBook addressBook = addressMapper.findById(submitDTO.getAddressBookId());
        if (Objects.isNull(addressBook)) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //查询购物车
        ShoppingCart scQuery = new ShoppingCart();
        scQuery.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(scQuery);
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //生成订单,设置主键返回
        Orders orders = new Orders();
        long timeMillis = System.currentTimeMillis();
        orders.setNumber(String.valueOf(timeMillis));
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(BaseContext.getCurrentId());
        orders.setAddressBookId(addressBook.getId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayMethod(1);
        orders.setPayStatus(Orders.UN_PAID);
        //总金额校验
        BigDecimal decimal = new BigDecimal(6);
        decimal = decimal.add(new BigDecimal(submitDTO.getPackAmount()));
        for (ShoppingCart cart : shoppingCarts) {
            BigDecimal multiply = cart.getAmount().multiply(new BigDecimal(cart.getNumber()));
            decimal = decimal.add(multiply);
        }
        orders.setAmount(decimal);
        orders.setRemark(submitDTO.getRemark());
        orders.setUserName(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        String address = addressBook.getProvinceName() + addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail();
        orders.setAddress(address);
        orders.setConsignee(addressBook.getConsignee());
        orders.setEstimatedDeliveryTime(LocalDateTime.now());
        orders.setDeliveryStatus(0);
        orders.setPackAmount(submitDTO.getPackAmount());
        orders.setTablewareNumber(submitDTO.getTablewareNumber());
        orders.setTablewareStatus(submitDTO.getTablewareStatus());
        orderMapper.add(orders);
        //生成订单详情
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(
                shoppingCart -> {
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(shoppingCart, orderDetail);
                    orderDetail.setOrderId(orders.getId());
                    return orderDetail;
                }).collect(Collectors.toList());
        orderDetailMapper.add(orderDetails);
        //清空购物车
        shoppingCartMapper.delete(scQuery);
        //返回值
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderAmount(orders.getAmount());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        return orderSubmitVO;
    }
}
