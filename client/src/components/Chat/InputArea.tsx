"use client"

import type React from "react"
import { useState } from "react"
import styled from "styled-components"
import { useChatStore } from "../../store/chatStore"
import { SendIcon, MicIcon } from "../Icons"
import type { Message } from "../../types/chat"

const InputContainer = styled.div`
  padding: 20px;
  background-color: transparent;
  display: flex;
  justify-content: center;
  width: 100%;
`

const InputWrapper = styled.div`
  width: 100%;
  max-width: 768px;
  position: relative;
`

const TextArea = styled.textarea`
  width: 100%;
  min-height: 52px;
  max-height: 200px;
  padding: 14px 60px 14px 16px;
  border: 1px solid #d1d5db;
  border-radius: 26px;
  font-size: 16px;
  line-height: 1.5;
  resize: none;
  background-color: white;
  color: #111827;
  
  &::placeholder {
    color: #9ca3af;
  }

  &:focus {
    border-color: #007aff;
    box-shadow: 0 0 0 3px rgba(0, 122, 255, 0.1);
  }
`

const ButtonGroup = styled.div`
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  gap: 4px;
`

const ActionButton = styled.button<{ variant?: "primary" | "secondary" }>`
  width: 36px;
  height: 36px;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  background-color: ${(props) => (props.variant === "primary" ? "#007aff" : "transparent")};
  color: ${(props) => (props.variant === "primary" ? "white" : "#6b7280")};

  &:hover {
    background-color: ${(props) => (props.variant === "primary" ? "#0056cc" : "#f3f4f6")};
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }

  svg {
    display: block;
    margin: auto;
  }
`

export const InputArea: React.FC = () => {
  const [input, setInput] = useState("")
  const { currentChatId, chats, updateChat, addChat, isLoading, setLoading } = useChatStore()

  const handleSubmit = async () => {
    if (!input.trim() || isLoading) return

    const userMessage: Message = {
      id: Date.now().toString(),
      content: input.trim(),
      role: "user",
      timestamp: new Date(),
    }

    let chatId = currentChatId

    if (!chatId) {
      const newChat = {
        id: Date.now().toString(),
        title: input.trim().slice(0, 30) + (input.length > 30 ? "..." : ""),
        messages: [userMessage],
        createdAt: new Date(),
        updatedAt: new Date(),
      }
      addChat(newChat)
      chatId = newChat.id
    } else {
      const currentChat = chats.find((chat) => chat.id === chatId)
      if (currentChat) {
        updateChat(chatId, {
          messages: [...currentChat.messages, userMessage],
          updatedAt: new Date(),
        })
      }
    }

    setInput("")
    setLoading(true)

    setTimeout(() => {
      const aiMessage: Message = {
        id: (Date.now() + 1).toString(),
        content: `안녕하세요! "${userMessage.content}"에 대한 답변입니다. 이것은 시뮬레이션된 AI 응답입니다.`,
        role: "assistant",
        timestamp: new Date(),
      }

      const currentChat = chats.find((chat) => chat.id === chatId)
      if (currentChat) {
        updateChat(chatId!, {
          messages: [...currentChat.messages, userMessage, aiMessage],
          updatedAt: new Date(),
        })
      }

      setLoading(false)
    }, 1000)
  }

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault()
      handleSubmit()
    }
  }

  return (
    <InputContainer>
      <InputWrapper>
        <TextArea
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="무엇이든 물어보세요"
          disabled={isLoading}
        />
        <ButtonGroup>
          <ActionButton variant="secondary">
            <MicIcon size={18} />
          </ActionButton>
          <ActionButton variant="primary" onClick={handleSubmit} disabled={!input.trim() || isLoading}>
            <SendIcon size={18} />
          </ActionButton>
        </ButtonGroup>
      </InputWrapper>
    </InputContainer>
  )
}
