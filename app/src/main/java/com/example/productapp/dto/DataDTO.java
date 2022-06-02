package com.example.productapp.dto;

import java.util.List;

public class DataDTO {
    List<ProductDTO> data;

    public List<ProductDTO> getData() {
        return data;
    }

    public void setData(List<ProductDTO> productDTOS) {
        this.data = productDTOS;
    }
}
