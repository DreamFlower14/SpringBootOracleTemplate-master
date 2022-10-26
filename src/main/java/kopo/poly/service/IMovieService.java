package kopo.poly.service;

public interface IMovieService {
    // 웹상 (cgv 홈페이지)에서 영화 순위 정보 가져오기
    int collectMovieRank() throws Exception;
}
