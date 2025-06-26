import { useUserPlanInfoStore } from "../../store/userPlanInfoStore";
import { formatDate } from "../../utils";
import * as S from "../../styles/UserInfoForm.style";
import type React from "react";

interface UserInfoFormPropsType {
  isSmall?: boolean;
}

const UserInfoForm: React.FC<UserInfoFormPropsType> = ({ isSmall = false }) => {
  const { userPlanInfo, updateUserPlanInfoField } = useUserPlanInfoStore();

  const maxEndDate =
    userPlanInfo.startDate &&
    formatDate(
      new Date(
        new Date(userPlanInfo.startDate).getTime() + 6 * 24 * 60 * 60 * 1000
      )
    );

  return (
    <S.Form $isSmall={isSmall}>
      <S.InputBox>
        <S.Label>여행 기간</S.Label>

        <S.DateBox>
          <S.Input
            name="startDate"
            type="date"
            value={userPlanInfo.startDate}
            onChange={(e) => {
              const { value } = e.target;
              updateUserPlanInfoField("startDate", value);
              updateUserPlanInfoField("endDate", "");
            }}
            max={userPlanInfo.endDate || undefined}
          />

          <S.Input
            name="endDate"
            type="date"
            value={userPlanInfo.endDate}
            onChange={(e) => {
              const { value } = e.target;
              updateUserPlanInfoField("endDate", value);
            }}
            min={userPlanInfo.startDate || undefined}
            max={maxEndDate || undefined}
          />
        </S.DateBox>
      </S.InputBox>

      <S.InputBox>
        <S.Label>여행 장소</S.Label>
        <S.Input
          type="text"
          name="region"
          value={userPlanInfo.region}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            const { value } = e.target;
            updateUserPlanInfoField("region", value);
          }}
          placeholder="예) 서울, 대구, 부산"
        />
      </S.InputBox>

      <S.InputBox>
        <S.Label>여행 목적</S.Label>
        <S.Select
          name="travelType"
          value={userPlanInfo.travelType}
          onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
            const { value } = e.target;
            updateUserPlanInfoField("travelType", value);
          }}
        >
          <option value="관광">관광</option>
          <option value="문화시설">문화시설</option>
          <option value="축제 / 공연 / 행사">축제 / 공연 / 행사</option>
          <option value="쇼핑">쇼핑</option>
          <option value="음식점">음식점</option>
        </S.Select>
      </S.InputBox>

      <S.InputBox>
        <S.Label>이동 수단</S.Label>
        <S.Select
          name="transportation"
          value={userPlanInfo.transportation}
          onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
            const { value } = e.target;
            updateUserPlanInfoField("transportation", value);
          }}
        >
          <option value="대중교통">대중교통 이용</option>
          <option value="자가용">자가용 이용</option>
        </S.Select>
      </S.InputBox>
    </S.Form>
  );
};

export default UserInfoForm;
