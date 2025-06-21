import type React from "react";
import styled from "styled-components";
import type { Message } from "../../types/chat";

const MessageContainer = styled.div<{ $isUser: boolean }>`
  display: flex;
  margin-bottom: 24px;
  justify-content: center;
  width: 100%;
`;

const MessageBubble = styled.div<{ $isUser: boolean }>`
  max-width: 70%;
  padding: 8px 15px;
  border-radius: 30px;
  background-color: ${(props) => (props.$isUser ? "#a3bd6c" : "#f2f2f2")};
  color: ${(props) => (props.$isUser ? "white" : "#111827")};
  font-size: 14px;
  line-height: 1.7;
  word-wrap: break-word;

  /* 줄바꿈 강제 적용 */
  white-space: pre-wrap !important;
  word-break: break-word;
  overflow-wrap: break-word;

  /* AI 응답의 경우 추가 스타일링 */
  ${(props) =>
    !props.$isUser &&
    `
  `}
`;

const Avatar = styled.div<{ $isUser: boolean }>`
  padding: 8px 12px;
  border-radius: 20px;
  /* background-color: ${(props) => (props.$isUser ? "#47533B" : "#10a37f")}; */
  display: flex;
  align-items: center;
  justify-content: center;
  color: #a3bd6c;
  font-size: 14px;
  font-weight: 600;
  margin: ${(props) => (props.$isUser ? "0 0 0 12px" : "0 12px 0 0")};
  flex-shrink: 0;
  align-self: flex-start;
`;

const MessageContent = styled.div<{ $isUser: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-direction: ${(props) => (props.$isUser ? "row-reverse" : "column")};
  width: 100%;
  max-width: 768px;
  padding: 0 20px;
`;

interface MessageItemProps {
  message: Message;
}

export const MessageItem: React.FC<MessageItemProps> = ({ message }) => {
  const isUser = message.role === "user";

  return (
    <MessageContainer $isUser={isUser}>
      <MessageContent $isUser={isUser}>
        {!isUser && <Avatar $isUser={isUser}>이너피스</Avatar>}

        <MessageBubble $isUser={isUser}>{message.content}</MessageBubble>
      </MessageContent>
    </MessageContainer>
  );
};
