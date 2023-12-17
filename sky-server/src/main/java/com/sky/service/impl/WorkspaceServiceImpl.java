package com.sky.service.impl;

import com.sky.mapper.WorkspaceMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


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
        Map<String, Objects> map = workspaceMapper.businessData(start, end);
        BusinessDataVO businessDataVO = new BusinessDataVO();
        businessDataVO.setTurnover(0.0D);
        businessDataVO.setValidOrderCount(0);
        businessDataVO.setOrderCompletionRate(0.0D);
        businessDataVO.setUnitPrice(0.0D);
        businessDataVO.setNewUsers(0);
        return businessDataVO;
    }
}
