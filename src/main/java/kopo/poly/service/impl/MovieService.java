package kopo.poly.service.impl;

import kopo.poly.dto.MovieDTO;
import kopo.poly.persistance.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Iterator;

@Slf4j
@Service("MovieService")
public class MovieService implements IMovieService {

    private final IMovieMapper movieMapper;

    public MovieService(IMovieMapper movieMapper){
        this.movieMapper = movieMapper;
    }

    @Transactional
    @Override
    public int collectMovieRank() throws Exception{
        log.info(this.getClass().getName() + ".collectMovieRank 시작!!");

        int res = 0; // 크롤링 결과 (0보다 크면 크롤링 성공)

        // CGV 영화 순위 정보 가져올 사이트 주소
        String url = "https://www.cgv.co.kr/movies/";

        //JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 html소스 저장할 변수
        Document doc = null;

        // 사이트 접속(http 프로토콜만 가능, https 프로토콜은 보안산 안됨)
        doc = Jsoup.connect(url).get();

        // cgv 웹페이지의 전체 소스 중 일부 태그를 선택하기 위해 사용
        // <div class="sect-movie-chart"> 이 태그 내에서 있는 html 소스만 element에 저장
        Elements element = doc.select("div.sect-movie-chart");

        // Iterator을 사용하여 영화 순위 정보를 가져오기
        // 영화순위는 기본적으로 1개 이상의 영화가 존재하기 때문에 태그의 반복이 존재할 수 밖에 없음
        Iterator<Element> movie_rank = element.select("strong.rank").iterator();
        Iterator<Element> movie_name = element.select("strong.title").iterator();
        Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();
        Iterator<Element> score = element.select("span.percent").iterator();
        Iterator<Element> open_day = element.select("span.txt-info").iterator();

        MovieDTO pDTO = null;

        while (movie_rank.hasNext()){

            pDTO = new MovieDTO();

            // 수집 시간을 기본키로 사용
            pDTO.setCollect_time(DateUtil.getDateTime("yyyyMMddhhmmss"));

            // 영화 순위 => trim 추가 이용 : 앞 뒤 공백 제거
            // 데이터 수집시 홈페이지 개발자들을 앞뒤 공백 집어넣을 수 있어서 추가
            String rank = CmmUtil.nvl(movie_rank.next().text()).trim(); // NO.1 들어옴
            pDTO.setMovie_rank(rank.substring(3, rank.length()));
            log.info(pDTO.getMovie_rank());

            // 영화 제목
            pDTO.setMovie_nm(CmmUtil.nvl(movie_name.next().text()).trim());
            log.info(pDTO.getMovie_nm());

            // 영화 예매율
            pDTO.setMovie_reserve(CmmUtil.nvl(movie_reserve.next().text()).trim());
            log.info(pDTO.getMovie_reserve());

            // 영화 점수
            pDTO.setScore(CmmUtil.nvl(score.next().text()).trim());
            log.info(pDTO.getScore());

            // 수집되는 데이터가 '2019.10.23 개봉'이기 때문에 앞에 10자리만 저장
            pDTO.setOpen_day(CmmUtil.nvl(open_day.next().text()).trim().substring(0,10));
            log.info(pDTO.getOpen_day());

            // 등록자
            pDTO.setReg_id("admin");

            // 바꾼 사람
            pDTO.setChg_id("sh");

            // 영화 한개씩 추가
            res += movieMapper.InsertMovieInfo(pDTO);
        }
        log.info(this.getClass().getName() + ".collectMovieRank 끝!!");
        return res;
    }
}
