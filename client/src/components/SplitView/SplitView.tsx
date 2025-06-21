import type React from "react";
import styled from "styled-components";

export const SplitContainer = styled.div`
  width: 50%;
  height: 100vh;
  background-color: #fafafa;
  border-left: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  animation: slideIn 0.3s ease-out;

  /* 스크롤바 스타일링 */
  &::-webkit-scrollbar {
    width: 8px;
  }

  &::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  &::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 4px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
  }

  @keyframes slideIn {
    from {
      transform: translateX(100%);
    }
    to {
      transform: translateX(0);
    }
  }
`;

export const SplitHeader = styled.div`
  padding: 16px 20px;
  border-bottom: 1px solid #e5e7eb;
  background-color: white;
  font-weight: 600;
  font-size: 14px;
  color: #374151;
  flex-shrink: 0;
`;

export const SplitContent = styled.div`
  flex: 1;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #9ca3af;
  font-size: 14px;
  text-align: center;
`;

export const SplitView: React.FC = () => {
  return (
    <SplitContainer>
      <SplitHeader>추가 정보</SplitHeader>
      <SplitContent>여기에 추가 콘텐츠가 표시됩니다</SplitContent>
    </SplitContainer>
  );
};
