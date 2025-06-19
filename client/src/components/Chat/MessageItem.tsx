import type React from "react"
import styled from "styled-components"
import type { Message } from "../../types/chat"
import { useChatStore } from "../../store/chatStore"

const MessageContainer = styled.div<{ $isUser: boolean }>`
  display: flex;
  margin-bottom: 24px;
  justify-content: center;
  width: 100%;
`

const MessageBubble = styled.div<{ $isUser: boolean; $hasSplitView?: boolean }>`
  max-width: ${(props) => (props.$hasSplitView ? "85%" : "70%")};
  padding: 12px 17px;
  border-radius: 18px;
  background-color: ${(props) => (props.$isUser ? "#47533B" : "#EEEAE4")};
  color: ${(props) => (props.$isUser ? "white" : "#111827")};
  font-size: 14px;
  line-height: 1.7;
  word-wrap: break-word;
  
  /* 줄바꿈 강제 적용 */
  white-space: pre-wrap !important;
  word-break: break-word;
  overflow-wrap: break-word;
`

const Avatar = styled.div<{ $isUser: boolean }>`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: ${(props) => (props.$isUser ? "#47533B" : "#10a37f")};
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  font-weight: 600;
  margin: ${(props) => (props.$isUser ? "0 0 0 12px" : "0 12px 0 0")};
  flex-shrink: 0;
  align-self: flex-start;
  margin-top: 4px;
`

const MessageContent = styled.div<{ $isUser: boolean; $hasSplitView?: boolean }>`
  display: flex;
  align-items: flex-start;
  flex-direction: ${(props) => (props.$isUser ? "row-reverse" : "row")};
  width: 100%;
  max-width: ${(props) => (props.$hasSplitView ? "500px" : "768px")};
  padding: 0 ${(props) => (props.$hasSplitView ? "15px" : "20px")};
`

interface MessageItemProps {
  message: Message
}

export const MessageItem: React.FC<MessageItemProps> = ({ message }) => {
  const isUser = message.role === "user"
  const { showSplitView } = useChatStore()

  return (
    <MessageContainer $isUser={isUser}>
      <MessageContent $isUser={isUser} $hasSplitView={showSplitView}>
        <Avatar $isUser={isUser}>{isUser ? "U" : "AI"}</Avatar>
        <MessageBubble $isUser={isUser} $hasSplitView={showSplitView}>
          {message.content}
        </MessageBubble>
      </MessageContent>
    </MessageContainer>
  )
}