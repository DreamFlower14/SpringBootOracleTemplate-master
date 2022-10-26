package kopo.poly.service;

import kopo.poly.dto.FoodDTO;

import java.util.List;

public interface IFoodService {
    // 식단 정보 가져오기
    List<FoodDTO> toDayFood() throws Exception;
}
