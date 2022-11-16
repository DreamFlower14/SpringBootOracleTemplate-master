package kopo.poly.persistance.mapper;

import kopo.poly.dto.OcrDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOcrMapper {
    // DB에 데이터 저장하기
    int insertOcrData(OcrDTO pDTO) throws Exception;
}
