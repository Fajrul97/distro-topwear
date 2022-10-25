package com.f13.distrotopwear.controller;

import com.f13.distrotopwear.model.dto.ColorDto;
import com.f13.distrotopwear.model.dto.DefaultResponse;
import com.f13.distrotopwear.model.entity.Color;
import com.f13.distrotopwear.repository.ColorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/color")
public class ColorController {

  @Autowired
  private ColorRepository colorRepository;

  @GetMapping("/all")
  public DefaultResponse getAllColor(){
    DefaultResponse df = new DefaultResponse();
    List<ColorDto> list;
    List<Color> lists = colorRepository.findAll();
    if (lists.size() != 0) {
//      for (color p : lists) {
//        df.setMessage("Berikut adalah daftar dari semua Warna.");
//        list.add(convertEntitytoDto(p));
//      }
      list = lists.stream().map(this::convertEntitytoDto).collect(Collectors.toList());
      df.setData(list);
      df.setMessage("Berikut adalah daftar dari semua warna.");
    }

    return df;
  }

  @GetMapping("/id")
  public DefaultResponse getByColorId(@RequestParam String colorId) {
    DefaultResponse df = new DefaultResponse();
    Optional<Color> colorOptional = colorRepository.findByColorId(colorId);
    if (colorOptional.isPresent()) {
      df.setStatus(Boolean.TRUE);
      df.setData(convertEntitytoDto(colorOptional.get()));
      df.setMessage("Warna Ditemukan");
    } else {
      df.setStatus(Boolean.FALSE);
      df.setMessage("Warna Tidak Ditemukan");
    }
    return df;
  }

  @PostMapping("/save")
  public DefaultResponse<ColorDto> saveColor(@RequestBody ColorDto colorDto) {
    Color color = convertDtotoEntity(colorDto);
    DefaultResponse<ColorDto> response = new DefaultResponse<>();
    Optional<Color> optionalColor = colorRepository.findByColorId(colorDto.getColorId());
    if (optionalColor.isPresent()) {
      response.setStatus(Boolean.FALSE);
      response.setMessage("Gagal Menyimpan, Warna Telah Tersedia");
    } else {
      response.setStatus(Boolean.TRUE);
      response.setMessage("Warna Berhasil Disimpan");
      response.setData(convertEntitytoDto(colorRepository.save(color)));
    }
    return response;
  }

  public ColorDto convertEntitytoDto(Color entity) {
    ColorDto dto = new ColorDto();
    BeanUtils.copyProperties(entity, dto);

    return dto;
  }

  public Color convertDtotoEntity(ColorDto dto) {
    Color entity = new Color();
    BeanUtils.copyProperties(dto, entity);

    return entity;
  }
}
