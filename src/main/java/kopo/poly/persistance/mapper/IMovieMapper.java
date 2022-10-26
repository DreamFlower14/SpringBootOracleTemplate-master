package kopo.poly.persistance.mapper;

import kopo.poly.dto.MovieDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMovieMapper {

    // 수집된 내용 DB에 등록
    int InsertMovieInfo(MovieDTO pDTO) throws Exception;

    // DB에서 조회
    MovieDTO getMovieInfo(MovieDTO pDTO) throws Exception;
}
