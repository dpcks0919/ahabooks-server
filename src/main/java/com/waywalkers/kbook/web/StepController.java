package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.dto.StepDto;
import com.waywalkers.kbook.service.StepService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StepController {

    private final StepService stepService;

    @GetMapping(path = Path.API_STEPS)
    @ApiOperation(
            value = "단계 목록 조회"
    )
    public ResultDto<List<StepDto.ListOfSteps>> getListOfSteps(){
        return stepService.getListOfSteps();
    }
}
