package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("PapagoService")
public class PapagoService implements IPapagoService {

    @Value("${naver.papago.clientId}")
    private String clientId;

    @Value("${naver.papago.clientSecret}")
    private String clientSecret;

    /**
     * 네이버 APi 사용을 위한 접속 정보 설정하기
     */
    private Map<String, String> setNaverInfo() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-Naver-Client-Id", clientId);   // papago 사용자 Id
        requestHeader.put("X-Naver-Client-Secret", clientSecret);   // papago 사용자 비밀번호

        log.info("clientId : " + clientId);
        log.info("clientSecret : " + clientSecret);

        return requestHeader;
    }

    @Override
    public PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "detectLangs 시작!!");

        String text = CmmUtil.nvl(pDTO.getText()); // 영작할 문장

        // 호출할 Papago 번역 API 정보 설정
        String param = "query=" + URLEncoder.encode(text, "UTF-8"); // 언어 감지할 문장

        // PapagoAPI 호출하기
        String json = NetworkUtil.post(IPapagoService.detectLangsApiURL, this.setNaverInfo(), param);

        // json 변수 형태 예 : {"langCode" : "Ko"}
        log.info("json" + json);

        // JSON 구조를 Map 데이터 구조로 변경하기
        // 키와 값 구조의 JSON 구조로부터 데이터를 쉽게 가져오기 위해 Map 데이터구조로 변경함
        PapagoDTO rDTO = new ObjectMapper().readValue(json, PapagoDTO.class);

        String LangCode = rDTO.getLangCode();
        log.info("LangCode : " + LangCode);

        // 언어 감지를 위한 원문 저장하기
        rDTO.setText(text);
        rDTO.setLangCode(LangCode);

        log.info(this.getClass().getName() + "detectLangs 끝!!");

        return rDTO;
    }

    @Override
    public PapagoDTO translate(PapagoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".translate Start!!");

        // 언어 종류 찾기
        PapagoDTO rDTO = this.detectLangs(pDTO);

        // 찾은 언어 종류
        String langcode = CmmUtil.nvl(rDTO.getLangCode());

        String source = ""; // 원문 언어
        String target = ""; // 번역할 언어

        if (langcode.equals("ko")) {
            source = "ko";
            target = "en";
        }else if (langcode.equals("en")){
            source = "en";
            target = "ko";
        }else {
            // 한국어나 영어가 아니면, 한국어로 번역하기
            source = rDTO.getLangCode();
            target = "ko";
        }

        rDTO = null; // 사용 용도가 끝나서 메모리에서 지우기

        String text = CmmUtil.nvl(pDTO.getText());

        // 언어를 번역하기 위한 파라미터 설정
        String postParams = "source=" + source + "&target=" + target + "&text=" + URLEncoder.encode(text, "UTF-8");

        log.info("postParams : " + postParams);

        // PapagoAPI 호출하기
        String json = NetworkUtil.post(IPapagoService.translateApiURL, this.setNaverInfo(), postParams);

        log.info("json : " + json);

        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

        // 결과 내용 중 message 정보 가져오기
        Map<String, Object> messageMap = (Map<String, Object>) rMap.get("message");

        // 결과 내용 중 result 정보 가져오기
        Map<String, String> resultMap = (Map<String, String>) messageMap.get("result");

        log.info("resultMap : " + resultMap);

        String srcLangType = CmmUtil.nvl(resultMap.get("srcLangType"));
        String tarLangType = CmmUtil.nvl(resultMap.get("tarLangType"));
        String translatedText = CmmUtil.nvl(resultMap.get("translatedText"));

        log.info("srcLangType : " + srcLangType);
        log.info("tarLangType : " + tarLangType);
        log.info("translatedText : " + translatedText);

        rDTO = new PapagoDTO();
        rDTO.setText(text);
        rDTO.setTranslatedText(translatedText);
        rDTO.setScrLangType(srcLangType);
        rDTO.setTarLangType(tarLangType);

        resultMap = null;
        messageMap = null;
        rMap = null;

        log.info(this.getClass().getName() + ".translate Start!");
        return rDTO;
    }
}
