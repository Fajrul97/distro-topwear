package com.f13.distrotopwear.model.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DefaultResponse<T> {

  private Boolean status;
  private T data;
  private String message;
}
