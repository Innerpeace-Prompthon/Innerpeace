"use client"

import type React from "react"
import { useState } from "react"
import styled from "styled-components"
import { useChatStore } from "../../store/chatStore"
import { HomeIcon, ChevronRightIcon, ClockIcon, MapIcon, CalendarIcon } from "../Icons"

const MenuContainer = styled.div`
  padding: 16px 12px;
  border-bottom: 1px solid #e5e5e5;
`

const MenuItem = styled.button`
  width: 100%;
  padding: 12px 16px;
  margin-bottom: 4px;
  background-color: transparent;
  color: #374151;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  font-weight: 500;
  text-align: left;
  transition: background-color 0.2s;

  &:hover {
    background-color: #f3f4f6;
  }

  &.active {
    background-color: #e5e7eb;
  }
`

const SectionHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  margin: 8px 0 4px 0;
  color: #6b7280;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.2s;

  &:hover {
    background-color: #f9fafb;
  }
`

const SubMenuItem = styled.button`
  width: 100%;
  padding: 8px 16px 8px 44px;
  margin-bottom: 2px;
  background-color: transparent;
  color: #6b7280;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  text-align: left;
  transition: background-color 0.2s;

  &:hover {
    background-color: #f3f4f6;
    color: #374151;
  }
`

const ChevronIcon = styled.div<{ expanded: boolean }>`
  transform: rotate(${(props) => (props.expanded ? "90deg" : "0deg")});
  transition: transform 0.2s;
`

export const SidebarMenu: React.FC = () => {
  const { addChat, setCurrentChat } = useChatStore()
  const [myPageExpanded, setMyPageExpanded] = useState(true)

  const handleNewChat = () => {
    const newChat = {
      id: Date.now().toString(),
      title: "새로운 채팅",
      messages: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    }
    addChat(newChat)
  }

  const handleHome = () => {
    setCurrentChat(null)
  }

  const toggleMyPage = () => {
    setMyPageExpanded(!myPageExpanded)
  }

  return (
    <MenuContainer>
      <MenuItem onClick={handleNewChat}>새로운 채팅</MenuItem>

      <MenuItem onClick={handleHome}>
        <HomeIcon size={18} />홈
      </MenuItem>

      <SectionHeader onClick={toggleMyPage}>
        <span>내 페이지</span>
        <ChevronIcon expanded={myPageExpanded}>
          <ChevronRightIcon size={16} />
        </ChevronIcon>
      </SectionHeader>

      {myPageExpanded && (
        <>
          <SubMenuItem>
            <ClockIcon size={16} />
            지난 여행
          </SubMenuItem>
          <SubMenuItem>
            <MapIcon size={16} />내 여행
          </SubMenuItem>
          <SubMenuItem>
            <CalendarIcon size={16} />
            나의 일정
          </SubMenuItem>
        </>
      )}
    </MenuContainer>
  )
}
