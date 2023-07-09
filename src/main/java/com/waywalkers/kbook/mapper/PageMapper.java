package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public PageDto PageToPageableDto(Page page){
        return new PageDto(page);
    }
}
