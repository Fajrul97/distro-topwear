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
@Table(name = "t_color")
public class Color {

  @Id
  @Column
  private String colorId;

  @Column
  private String colorName;
}
