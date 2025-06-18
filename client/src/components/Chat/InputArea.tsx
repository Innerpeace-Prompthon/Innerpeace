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
  letter-spacing: normal;
  word-spacing: normal;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
  
  &::placeholder {
    color: #9ca3af;
    letter-spacing: normal;
    word-spacing: normal;
  }

  &:focus {
    border-color: #47533B;
    box-shadow: 0 0 0 3px rgba(0, 122, 255, 0.1);
    outline: none;
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

const ActionButton = styled.button<{ $variant?: "primary" | "secondary" }>`
  width: 36px;
  height: 36px;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  background-color: ${(props) => (props.$variant === "primary" ? "#47533B" : "transparent")};
  color: ${(props) => (props.$variant === "primary" ? "white" : "#6b7280")};

  &:hover {
    background-color: ${(props) => (props.$variant === "primary" ? "#798E65" : "#f3f4f6")};
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

const ErrorMessage = styled.div`
  position: absolute;
  bottom: -24px;
  left: 16px;
  color: #ef4444;
  font-size: 12px;
`

export const InputArea: React.FC = () => {
  const [input, setInput] = useState("")
  const [error, setError] = useState("")
  const { currentChatId, chats, updateChat, addChat, isLoading, setLoading } = useChatStore()

  const handleSubmit = async () => {
    if (!input.trim() || isLoading) return
  
    console.log('=== 메시지 전송 시작 ===')
    console.log('입력 메시지:', input.trim())
  
    const userMessage: Message = {
      id: Date.now().toString(),
      content: input.trim(),
      role: "user",
      timestamp: new Date(),
    }
  
    let chatId = currentChatId
    let currentMessages: Message[] = []
  
    // 새 채팅 생성 또는 기존 채팅에 사용자 메시지 추가
    if (!chatId) {
      const newChat = {
        id: Date.now().toString(),
        title: input.trim().slice(0, 30) + (input.length > 30 ? "..." : ""),
        messages: [userMessage],
        createdAt: new Date(),
        updatedAt: new Date(),
      }
      console.log('새 채팅 생성:', newChat.id)
      addChat(newChat)
      chatId = newChat.id
      currentMessages = [userMessage]
    } else {
      const currentChat = chats.find((chat) => chat.id === chatId)
      if (currentChat) {
        currentMessages = [...currentChat.messages, userMessage]
        updateChat(chatId, {
          messages: currentMessages,
          updatedAt: new Date(),
        })
      }
    }
  
    setInput("")
    setError("")
    setLoading(true)

    try {
      console.log('=== API 요청 시작 ===')
      console.log('요청 URL: http://localhost:8080/api/chat/message')
      console.log('요청 데이터:', { message: userMessage.content })
      
      const response = await fetch('http://localhost:8080/api/chat/message', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: userMessage.content }),
      })

      console.log('=== 응답 받음 ===')
      console.log('응답 상태:', response.status)
      console.log('응답 OK:', response.ok)
      console.log('응답 헤더:', Object.fromEntries(response.headers.entries()))

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status} - ${response.statusText}`)
      }

      const responseText = await response.text()
      console.log('=== 응답 텍스트 ===')
      console.log('원본 텍스트:', responseText)

      let data
      try {
        data = JSON.parse(responseText)
        console.log('=== 파싱된 JSON ===')
        console.log('파싱된 데이터:', data)
      } catch (parseError) {
        console.error('JSON 파싱 실패:', parseError)
        throw new Error('서버 응답을 파싱할 수 없습니다.')
      }

      // AI 응답 메시지 생성
      const aiMessage: Message = {
        id: (Date.now() + 1).toString(),
        content: data.message || "응답을 받지 못했습니다.",
        role: "assistant",
        timestamp: new Date(),
      }

      console.log('=== AI 메시지 생성 ===')
      console.log('AI 응답:', aiMessage.content)

      // 메시지 업데이트
      const finalMessages = [...currentMessages, aiMessage]
      updateChat(chatId!, {
        messages: finalMessages,
        updatedAt: new Date(),
      })

      console.log('=== 메시지 업데이트 완료 ===')

    } catch (error) {
      console.error('=== API 호출 실패 ===')
      console.error('에러 객체:', error)
      console.error('에러 메시지:', error instanceof Error ? error.message : String(error))
      
      setError(`API 호출 실패: ${error instanceof Error ? error.message : String(error)}`)
      
      // 에러 메시지 표시
      const errorMessage: Message = {
        id: (Date.now() + 1).toString(),
        content: `죄송합니다. 오류가 발생했습니다: ${error instanceof Error ? error.message : String(error)}`,
        role: "assistant",
        timestamp: new Date(),
      }
      
      const finalMessages = [...currentMessages, errorMessage]
      updateChat(chatId!, {
        messages: finalMessages,
        updatedAt: new Date(),
      })
    } finally {
      setLoading(false)
      console.log('=== 처리 완료 ===')
    }
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
          <ActionButton $variant="secondary">
            <MicIcon size={18} />
          </ActionButton>
          <ActionButton 
            $variant="primary" 
            onClick={handleSubmit} 
            disabled={!input.trim() || isLoading}
          >
            <SendIcon size={18} />
          </ActionButton>
        </ButtonGroup>
        {error && <ErrorMessage>{error}</ErrorMessage>}
      </InputWrapper>
    </InputContainer>
  )
}