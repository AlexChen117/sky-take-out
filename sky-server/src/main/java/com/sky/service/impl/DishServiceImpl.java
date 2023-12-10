package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.DishException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜品管理Service的实现类
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:10:05
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final FlavorMapper flavorMapper;
    private final SetmealDishMapper setmealDishMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void add(DishDTO dishDTO) {
        //校验
        Dish dishQuery = new Dish();
        dishQuery.setName(dishDTO.getName());
        Integer count = dishMapper.selectCount(dishQuery);
        if (count > 0) {
            throw new DishException("菜品已存在");
        }
        //核心代码
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //默认停售
        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.add(dish);
        //添加口味
        saveDishFlavors(dishDTO, dish);
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Dish dishQuery = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO, dishQuery);
        Page<Dish> page = dishMapper.selectPage(dishQuery);
        //Dish 转换为 DishVO
        List<DishVO> collect = page.getResult().stream()
                .map(dish -> {
                    DishVO dishVO = new DishVO();
                    BeanUtils.copyProperties(dish, dishVO);
                    return dishVO;
                }).collect(Collectors.toList());
        return new PageResult(page.getTotal(), collect);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        Integer countByIdsAndStatus = dishMapper.selectCountByIdsAndStatus(ids, StatusConstant.ENABLE);
        if (countByIdsAndStatus > 0) {
            throw new DeletionNotAllowedException("启售商品无法删除!");
        }
        Integer countByDishIds = setmealDishMapper.selectCountByDishIds(ids, StatusConstant.ENABLE);
        if (countByDishIds > 0) {
            throw new DeletionNotAllowedException("套餐关联的商品无法删除!");
        }
        dishMapper.deleteByIds(ids);
        ids.forEach(id -> {
            flavorMapper.deleteByDishId(Long.valueOf(id));
        });
    }

    /**
     * 回显信息
     *
     * @param id
     */
    @Override
    public DishVO findById(Long id) {
        Dish byId = dishMapper.findById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(byId, dishVO);
        dishVO.setFlavors(flavorMapper.selectByDishId(id));
        return dishVO;
    }

    /**
     * 更新菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        //校验
        Integer count = dishMapper.selectCountByNameAndNotId(dishDTO.getName(), dishDTO.getId());
        if (count > 0) {
            throw new DishException("菜品名称重复!");
        }
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        flavorMapper.deleteByDishId(dish.getId());
        saveDishFlavors(dishDTO, dish);
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     */
    @Override
    public void statusChange(Integer status, Long id) {
        Dish byId = dishMapper.findById(id);
        byId.setStatus(status);
        dishMapper.update(byId);
    }


    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> list(Long categoryId) {
        //走redis取数据
        //String key = "DISH:" + categoryId;
        //String dishJson = (String) redisTemplate.opsForValue().get(key);
        //if (StringUtils.isNoneEmpty(dishJson)) {
        //    return JSON.parseArray(dishJson, Dish.class);
        //}
        //走数据库
        Integer status = StatusConstant.ENABLE;
        List<Dish> list = dishMapper.list(categoryId, status);
        //if (Objects.nonNull(list) && !list.isEmpty()) {
        //    redisTemplate.opsForValue().set(key, JSON.toJSONString(list));
        //}
        return list;
    }

    /**
     * 保存菜肴口味
     *
     * @param dishDTO
     * @param dish
     */
    private void saveDishFlavors(DishDTO dishDTO, Dish dish) {
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            //将flavors中的dishId替换成对应的dishId
            flavors.forEach(item -> item.setDishId(dish.getId()));
            flavorMapper.addBatch(flavors);
        }
    }
}
