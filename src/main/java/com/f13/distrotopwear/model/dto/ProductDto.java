package com.f13.distrotopwear.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

  private Integer id;
  private String productId;
  private String productName;
  private String categoryId;
  private String categoryName;
  private String colorId;
  private String colorName;
  private Double price;
}
