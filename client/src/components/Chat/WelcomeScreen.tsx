import type React from "react"
import styled from "styled-components"
import { InputArea } from "./InputArea"

const WelcomeContainer = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  text-align: center;
  background-color: white;
  gap: 24px;
  transform: translateY(-50px);
`

const WelcomeTitle = styled.h2`
  font-size: 32px;
  font-weight: 600;
  color: #111827;
  margin-bottom: 0;
`

// const WelcomeSubtitle = styled.p`
//   font-size: 16px;
//   color: #6b7280;
//   max-width: 600px;
//   line-height: 1.5;
// `

export const WelcomeScreen: React.FC = () => {
  return (
    <WelcomeContainer>
      <div>
        <WelcomeTitle>무슨 작업을 하고 계세요?</WelcomeTitle>
        {/* <WelcomeSubtitle>
          AI 어시스턴트와 대화를 시작해보세요. 질문하거나 도움이 필요한 작업에 대해 말씀해 주세요.
        </WelcomeSubtitle> */}
      </div>
      <InputArea />
    </WelcomeContainer>
  )
}
