package kopo.poly.persistance;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoMapper {

    UserInfoDTO getUserLoginCheck(UserInfoDTO pDTO) throws Exception;
}
