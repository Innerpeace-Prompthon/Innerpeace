import type React from "react"
import styled from "styled-components"
import type { Message } from "../../types/chat"

const MessageContainer = styled.div<{ isUser: boolean }>`
  display: flex;
  margin-bottom: 24px;
  justify-content: center;
  width: 100%;
`

const MessageBubble = styled.div<{ isUser: boolean }>`
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 18px;
  background-color: ${(props) => (props.isUser ? "#007aff" : "#f1f3f4")};
  color: ${(props) => (props.isUser ? "white" : "#111827")};
  font-size: 14px;
  line-height: 1.4;
  word-wrap: break-word;
`

const Avatar = styled.div<{ isUser: boolean }>`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: ${(props) => (props.isUser ? "#007aff" : "#10a37f")};
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  font-weight: 600;
  margin: ${(props) => (props.isUser ? "0 0 0 12px" : "0 12px 0 0")};
  flex-shrink: 0;
`

const MessageContent = styled.div<{ isUser: boolean }>`
  display: flex;
  align-items: flex-start;
  flex-direction: ${(props) => (props.isUser ? "row-reverse" : "row")};
  width: 100%;
  max-width: 768px;
  padding: 0 20px;
`

interface MessageItemProps {
  message: Message
}

export const MessageItem: React.FC<MessageItemProps> = ({ message }) => {
  const isUser = message.role === "user"

  return (
    <MessageContainer isUser={isUser}>
      <MessageContent isUser={isUser}>
        <Avatar isUser={isUser}>{isUser ? "U" : "AI"}</Avatar>
        <MessageBubble isUser={isUser}>{message.content}</MessageBubble>
      </MessageContent>
    </MessageContainer>
  )
}
