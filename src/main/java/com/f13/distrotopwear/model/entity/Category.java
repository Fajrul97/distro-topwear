package com.f13.distrotopwear.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "t_category")
public class Category {

  @Id
  @Column
  private String categoryId;
  @Column
  private String categoryName;
}
