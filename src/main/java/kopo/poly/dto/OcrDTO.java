package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrDTO {
    /*
    한글을 잘 못 읽기 때문에 원래 파일 이름이랑 영어와 한글이 썪인 저장된 이미지 파일 이름 2가지를 만들고
    나중에 다운로드 된 파일 이름은 원래 파일 이름으로 나온다.
    기업의 저장방식을 실제 저장되는 파일명을 파일의 확장자를 제거시키고 1234로 저장하고 다운받을 때 원래 파일이름을 연결시켜서 보내준다
     */
    private String filePath;    // 저장된 이미지 파일의 파일 저장 경로
    private String fileName;    // 저장된 이미지 파일 이름
    private String fileFromImage;   // 저장된 이미지로부터 읽은 글씨
    private String textFromImage;   // 저장된 이미지로부터 읽은 글씨

    private String org_file_name;   // 원래 파일 이름
    private String ext; // 확장자
    private String reg_id;  // 등록자
    private String chg_id;  // 수정자
}
