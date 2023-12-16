package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.AdminOrderService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 报表控制器
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/16 09:28:01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {
    private final AdminOrderService adminOrderService;

    /**
     * 营业额统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("营业额统计接口");
        TurnoverReportVO reportVO = adminOrderService.turnoverStatistics(begin, end);
        return Result.success(reportVO);
    }

    /**
     * 用户统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("用户统计接口");
        UserReportVO userReportVO = adminOrderService.userStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("订单统计接口");
        OrderReportVO orderReportVO = adminOrderService.ordersStatistics(begin, end);
        return Result.success(orderReportVO);
    }
}
