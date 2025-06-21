"use client"

import type React from "react"
import styled from "styled-components"
import { useChatStore } from "../../store/chatStore"
import { CollapseIcon, SplitViewIcon } from "../Icons"

const ToggleButton = styled.button`
  position: fixed;
  top: 20px;
  right: 20px;
  width: 36px;
  height: 36px;
  border-radius: 8px;
  background-color: white;
  border: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  z-index: 1001;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  &:hover {
    background-color: #f9fafb;
    border-color: #d1d5db;
  }

  &:active {
    transform: scale(0.95);
  }
`

export const SplitViewToggle: React.FC = () => {
  const { showSplitView, toggleSplitView } = useChatStore()

  return (
    <ToggleButton onClick={toggleSplitView} title={showSplitView ? "스플릿 뷰 닫기" : "스플릿 뷰 열기"}>
      {showSplitView ? <CollapseIcon size={18} /> : <SplitViewIcon size={18} />}
    </ToggleButton>
  )
}