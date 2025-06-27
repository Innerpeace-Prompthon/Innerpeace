import React from "react";
import { InputArea } from "./InputArea";
import * as S from "../../styles/WelcomeScreen.style";
import UserInfoForm from "./UserInfoForm";

export interface UserFormType {
  startDate: string;
  endDate: string;
  region: string;
  travelType: string;
  transportation: string;
}

export const WelcomeScreen: React.FC = () => {
  return (
    <S.WelcomeContainer>
      <S.WelcomeTitle>
        <p>쉼이 필요한 당신을 위한, 감성 힐링 여행</p>
        <span className="title">Innerpeace</span>
      </S.WelcomeTitle>

      <UserInfoForm />

      <div style={{ width: "500px" }}>
        <InputArea />
      </div>
    </S.WelcomeContainer>
  );
};
