import type React from "react";
import styled from "styled-components";

const SplitContainer = styled.div`
  position: fixed;
  right: 0;
  top: 0;
  width: 50%;
  height: 100vh;
  background-color: #fafafa;
  border-left: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  z-index: 999;
  animation: slideIn 0.3s ease-out;

  @keyframes slideIn {
    from {
      transform: translateX(100%);
    }
    to {
      transform: translateX(0);
    }
  }
`;

const SplitHeader = styled.div`
  padding: 16px 20px;
  border-bottom: 1px solid #e5e7eb;
  background-color: white;
  font-weight: 600;
  font-size: 14px;
  color: #374151;
`;

const SplitContent = styled.div`
  flex: 1;
  overflow-y: auto;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #9ca3af;
  font-size: 14px;
  text-align: center;
`;

interface SplitViewPropsType {
  title: string;
  children: React.ReactNode;
}

export const SplitView: React.FC<SplitViewPropsType> = ({
  children,
  title,
}) => {
  return (
    <SplitContainer>
      <SplitHeader>{title}</SplitHeader>
      <SplitContent>{children}</SplitContent>
    </SplitContainer>
  );
};
