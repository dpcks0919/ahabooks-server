package com.waywalkers.kbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Data
public class PageDto {
    private Integer totalPages;
    private long totalElements;
    private Integer numberOfElements;
    private Integer pageSize;
    private Integer pageNumber;
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageDto(Page page) {
        this.totalPages =    page.getTotalPages();
        this.totalElements =    page.getTotalElements();
        this.numberOfElements = page.getNumberOfElements();
        this.pageSize = page.getSize();
        this.pageNumber = page.getPageable().getPageNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }
}
