package com.f13.distrotopwear.controller;

import com.f13.distrotopwear.model.dto.CategoryDto;
import com.f13.distrotopwear.model.dto.DefaultResponse;
import com.f13.distrotopwear.model.entity.Category;
import com.f13.distrotopwear.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryRepository categoryRepository;

  /*@GetMapping("/all")*/
  @GetMapping
  public DefaultResponse getAllCategory(){
    DefaultResponse df = new DefaultResponse();
    List<CategoryDto> list;
    List<Category> lists = categoryRepository.findAll();
    if (lists.size() != 0) {
//      for (Category p : lists) {
//        df.setMessage("Berikut adalah daftar dari semua Kategori.");
//        list.add(convertEntitytoDto(p));
//      }
      list = lists.stream().map(this::convertEntitytoDto).collect(Collectors.toList());
      df.setData(list);
      df.setMessage("Berikut adalah daftar dari semua kategori.");
    }

    return df;
  }

  @GetMapping("/id")
  public DefaultResponse getBycategoryId(@RequestParam String categoryId) {
    DefaultResponse df = new DefaultResponse();
    Optional<Category> categoryOptional = categoryRepository.findByCategoryId(categoryId);
    if (categoryOptional.isPresent()) {
      df.setStatus(Boolean.TRUE);
      df.setData(convertEntitytoDto(categoryOptional.get()));
      df.setMessage("Kategori Ditemukan");
    } else {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Kategori Tidak Ditemukan");
    }
    return df;
  }

  /*@PostMapping("/save")*/
  @PostMapping
  public DefaultResponse<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
    Category category = convertDtotoEntity(categoryDto);
    DefaultResponse<CategoryDto> df = new DefaultResponse<>();
    Optional<Category> optionalCategory = categoryRepository.findByCategoryId(categoryDto.getCategoryId());
    if (optionalCategory.isPresent()) {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Gagal Menyimpan, Kategori Telah Tersedia");
    } else {
      df.setStatus(Boolean.TRUE);
      df.setMessage("Kategori Berhasil Disimpan");
      df.setData(convertEntitytoDto(categoryRepository.save(category)));
    }
    return df;
  }

  /*@DeleteMapping("/delete")*/
  @DeleteMapping
  public DefaultResponse deleteByCategoryId(@RequestParam String categoryId) {
    DefaultResponse df = new DefaultResponse();
    Optional<Category> optionalCategory = categoryRepository.findByCategoryId(categoryId);
    if (optionalCategory.isPresent()) {
      categoryRepository.delete(optionalCategory.get());
      df.setStatus(Boolean.TRUE);
      df.setMessage("Kategori Berhasil Dihapus");
      df.setData(optionalCategory.get());
    } else {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Kategori Tidak Ditemukan");
    }
    return df;
  }

  /*@PutMapping("/update")*/
  @PutMapping
  public DefaultResponse updateByCategoryId(@RequestBody CategoryDto categoryDto) {
    DefaultResponse df = new DefaultResponse();
    Optional<Category> optionalCategory = categoryRepository.findByCategoryId(categoryDto.getCategoryId());
    Category category = optionalCategory.get();
    if (optionalCategory.isPresent()) {
      categoryDto.setCategoryId(category.getCategoryId());
      df.setStatus(Boolean.TRUE);
      df.setData(convertEntitytoDto(categoryRepository.save(convertDtotoEntity(categoryDto))));
      df.setMessage("Perubahan Berhasil Tersimpan");
    } else {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Kategori Tidak Ditemukan");
    }
    return df;
  }

  public CategoryDto convertEntitytoDto(Category entity) {
    CategoryDto dto = new CategoryDto();
    BeanUtils.copyProperties(entity, dto);

    return dto;
  }

  public Category convertDtotoEntity(CategoryDto dto) {
    Category entity = new Category();
    BeanUtils.copyProperties(dto, entity);

    return entity;
  }
}
