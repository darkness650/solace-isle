package com.solaceisle.pojo.dto;

import lombok.Data;

@Data
public class PageQuertDTO {
    private int order;
    private int page;
    private int pageSize;
    private String studentId;
    public PageQuertDTO(int order, int page, int pageSize) {
        this.order = order;
        this.page = page;
        this.pageSize = pageSize;
    }
}
