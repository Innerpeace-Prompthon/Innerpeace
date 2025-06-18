import type React from "react"
import styled from "styled-components"

const SplitContainer = styled.div`
  width: 400px;
  height: 100%;
  background-color: #fafafa;
  border-left: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow: hidden;
`

const SplitHeader = styled.div`
  padding: 16px 20px;
  border-bottom: 1px solid #e5e7eb;
  background-color: white;
  font-weight: 600;
  font-size: 14px;
  color: #374151;
`

const SplitContent = styled.div`
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #9ca3af;
  font-size: 14px;
`

interface SplitViewProps {
  title?: string
  children?: React.ReactNode
}

export const SplitView: React.FC<SplitViewProps> = ({ title = "추가 정보", children }) => {
  return (
    <SplitContainer>
      <SplitHeader>{title}</SplitHeader>
      <SplitContent>{children || "여기에 추가 콘텐츠가 표시됩니다"}</SplitContent>
    </SplitContainer>
  )
}
