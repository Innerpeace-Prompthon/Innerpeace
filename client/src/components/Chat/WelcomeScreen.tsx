import React, { useState } from "react";
import { InputArea } from "./InputArea";
import * as S from "../../styles/WelcomeScreen.style";

export const WelcomeScreen: React.FC = () => {
  const [formData, setFormData] = useState({
    date: "",
    location: "",
    purpose: "관광",
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
        <div>
          <S.Label>여행 기간</S.Label>
          <S.Input
            name="date"
            type="date"
            value={formData.date}
            onChange={handleChange}
            placeholder="예) 2일, 일주일, 1개월"
          />
        </div>

        <div>
          <S.Label>여행 장소</S.Label>
          <S.Input
            type="text"
            name="location"
            value={formData.location}
            onChange={handleChange}
            placeholder="예) 서울, 대구, 부산"
          />
        </div>

        <div>
          <S.Label>여행 목적</S.Label>
          <S.Select
            name="purpose"
            value={formData.purpose}
            onChange={handleChange}
          >
            <option value="관광">관광</option>
            <option value="힐링">힐링</option>
            <option value="음식">음식 탐방</option>
            <option value="액티비티">액티비티</option>
            <option value="문화">문화 체험</option>
          </S.Select>
        </div>
      </S.Form>

      <div style={{ width: "500px" }}>
        <InputArea />
      </div>
    </S.WelcomeContainer>
  );
};
