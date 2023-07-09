package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.HomeDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.HomeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final HomeService homeService;

    @GetMapping(path = Path.API_HOME)
    @ApiOperation(
            value = "홈 카드 조회"
    )
    public ResultDto<HomeDto.HomeCard> getHome(){
        return homeService.getHome();
    }
}
