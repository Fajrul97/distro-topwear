package com.f13.distrotopwear.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "t_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product")
  @Column
  private Integer id;

  @Column
  private String productId;

  @Column
  private String productName;

  @Column
  private Double price;

  @ManyToOne
  @JoinColumn(name = "color_id")
  private Color color;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
