import React, { useState } from "react";
import { InputArea } from "./InputArea";
import * as S from "../../styles/WelcomeScreen.style";

export interface UserFormType {
  startDate: string;
  endDate: string;
  region: string;
  travelType: string;
  transportation: string;
}

export const WelcomeScreen: React.FC = () => {
  const [formData, setFormData] = useState<UserFormType>({
    startDate: "",
    endDate: "",
    region: "",
    travelType: "관광",
    transportation: "",
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <S.WelcomeContainer>
      <S.WelcomeTitle>
        <p>쉼이 필요한 당신을 위한, 감성 힐링 여행</p>
        <h1>이너피스</h1>
      </S.WelcomeTitle>

      <S.Form>
        <S.InputBox>
          <S.Label>여행 기간</S.Label>

          <S.DateBox>
            <input
              name="startDate"
              type="date"
              value={formData.startDate}
              onChange={handleChange}
            />

            <input
              name="endDate"
              type="date"
              value={formData.endDate}
              onChange={handleChange}
            />
          </S.DateBox>
        </S.InputBox>

        <S.InputBox>
          <S.Label>여행 장소</S.Label>
          <S.Input
            type="text"
            name="region"
            value={formData.region}
            onChange={handleChange}
            placeholder="예) 서울, 대구, 부산"
          />
        </S.InputBox>

        <S.InputBox>
          <S.Label>여행 목적</S.Label>
          <S.Select
            name="travelType"
            value={formData.travelType}
            onChange={handleChange}
          >
            <option value="관광">관광</option>
            <option value="힐링">힐링</option>
            <option value="음식">음식 탐방</option>
            <option value="액티비티">액티비티</option>
            <option value="문화">문화 체험</option>
          </S.Select>
        </S.InputBox>

        <S.InputBox>
          <S.Label>이동 수단</S.Label>
          <S.Select
            name="transportation"
            value={formData.transportation}
            onChange={handleChange}
          >
            <option value="대중교통">대중교통 이용</option>
            <option value="자가용">자가용 이용</option>
          </S.Select>
        </S.InputBox>
      </S.Form>

      <div style={{ width: "500px" }}>
        <InputArea />
      </div>
    </S.WelcomeContainer>
  );
};
