package com.f13.distrotopwear.controller;


import com.f13.distrotopwear.model.dto.DefaultResponse;
import com.f13.distrotopwear.model.dto.ProductDto;
import com.f13.distrotopwear.model.entity.Category;
import com.f13.distrotopwear.model.entity.Color;
import com.f13.distrotopwear.model.entity.Product;
import com.f13.distrotopwear.repository.CategoryRepository;
import com.f13.distrotopwear.repository.ColorRepository;
import com.f13.distrotopwear.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ColorRepository colorRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("/all")
  public DefaultResponse getAllProduct(){
    DefaultResponse df = new DefaultResponse();
    List<ProductDto> list;
    List<Product> lists = productRepository.findAll();
    if (lists.size() != 0) {
//      for (Product p : lists) {
//        df.setMessage("Berikut adalah daftar dari semua produk.");
//        list.add(convertEntitytoDto(p));
//      }
      list = lists.stream().map(this::convertEntitytoDto).collect(Collectors.toList());
      df.setData(list);
      df.setMessage("Berikut adalah daftar dari semua produk.");

    }

    return df;
  }

  @GetMapping("/id")
  public DefaultResponse getByProductId(@RequestParam String productId) {
    DefaultResponse df = new DefaultResponse();
    Optional<Product> productOptional = productRepository.findByProductId(productId);
    if (productOptional.isPresent()) {
      df.setStatus(Boolean.TRUE);
      df.setData(convertEntitytoDto(productOptional.get()));
      df.setMessage("Produk Ditemukan");
    } else {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Produk Tidak Ditemukan");
    }
    return df;
  }

  @PostMapping("/save")
  public DefaultResponse<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
    Product product = convertDtotoEntity(productDto);
    DefaultResponse<ProductDto> response = new DefaultResponse<>();
    Optional<Product> optionalProduct = productRepository.findByProductId(productDto.getProductId());
    if (optionalProduct.isPresent()) {
      response.setStatus(Boolean.FALSE);
      response.setMessage("Gagal Menyimpan, Produk Telah Tersedia");
    } else {
      response.setStatus(Boolean.TRUE);
      response.setMessage("Produk Berhasil Disimpan");
      response.setData(convertEntitytoDto(productRepository.save(product)));
    }
    return response;
  }

  public ProductDto convertEntitytoDto(Product entity) {
    ProductDto dto = new ProductDto();
    BeanUtils.copyProperties(entity, dto);
    dto.setColorId(entity.getColor().getColorId());
    dto.setColorName(entity.getColor().getColorName());
    dto.setCategoryId(entity.getCategory().getCategoryId());
    dto.setCategoryName(entity.getCategory().getCategoryName());

    return dto;
  }

  public Product convertDtotoEntity(ProductDto dto) {
    Product entity = new Product();
    BeanUtils.copyProperties(dto, entity);
    Color color = colorRepository.findByColorId(dto.getColorId()).get();
    entity.setColor(color);
    Category category = categoryRepository.findByCategoryId(dto.getCategoryId()).get();
    entity.setCategory(category);

    return entity;
  }
}
