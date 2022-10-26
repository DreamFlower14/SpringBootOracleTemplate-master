package kopo.poly.service.impl;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service("FoodService")
public class FoodService implements IFoodService {

    @Override
    public List<FoodDTO> toDayFood() throws Exception{
        log.info(this.getClass().getName() + ".toDayFood 시작!!");

        int res = 0;

        String url = "https://www.kopo.ac.kr/kangseo/content.do?menu=262";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트 전체 html소스 저장할 변수
        Document doc = null;

        doc = Jsoup.connect(url).get();

        Elements element = doc.select("table.tbl_table tbody");

        // Iterator을 사용하여 영화정보 순위 가져오기
        Iterator<Element> foodIt = element.select("tr").iterator();

        FoodDTO pDTO = null;

        List<FoodDTO> plist = new ArrayList<>();

        int idx = 0;

        while (foodIt.hasNext()){
            //반복횟수 카운팅하기, 5번째가 금요일이라 6번째인 토요일을 실행안되게하기 위함
            // 반복문 5번 돌기
            if (idx++ > 4){
                break;
            }
            pDTO = new FoodDTO();

            String food = CmmUtil.nvl(foodIt.next().text().trim());

            log.info("food : "+food);

            pDTO.setDay(food.substring(0,3));
            pDTO.setFood_nm(food.substring(4));

            plist.add(pDTO);
        }

        log.info(this.getClass().getName() + ".toDayFood 끝!!");
        return plist;
    }
}
