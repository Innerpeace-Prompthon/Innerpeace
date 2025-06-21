import styled from "styled-components";

export const AppContainer = styled.div`
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
`;

export const SidebarWrapper = styled.div<{ $isHidden?: boolean }>`
  position: fixed;
  left: 0;
  top: 0;
  width: 260px;
  height: 100vh;
  transform: translateX(${(props) => (props.$isHidden ? "-100%" : "0")});
  transition: transform 0.3s ease;
  z-index: 1000;
`;

export const ChatContainer = styled.div<{ $hasSplitView?: boolean }>`
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-left: ${(props) => (props.$hasSplitView ? "0" : "260px")};
  width: ${(props) => (props.$hasSplitView ? "50%" : "auto")};
  transition: all 0.3s ease;
`;

export const ChatContent = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  overflow-y: auto;

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
`;
