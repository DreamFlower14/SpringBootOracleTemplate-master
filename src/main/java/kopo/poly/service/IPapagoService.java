package kopo.poly.service;

import kopo.poly.dto.PapagoDTO;

public interface IPapagoService {

    // papago 언어 감지 API
    String detectLangsApiURL = "https://openapi.naver.com/v1/papago/detectLangs";

    // papago 언어 번역 API
    String translateApiURL = "https://openapi.naver.com/v1/papago/n2mt";


    // 네이버 파파고 API를 호출하여 입력된 언어가 어느 나라 언어인지 찾기
    PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception;

    // 네이버 파파고 API를 호출하여 언어 감지 후, 번역하기
    PapagoDTO translate(PapagoDTO pDTO) throws Exception;
}
