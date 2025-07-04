import React, { useState } from "react";
import { GoInfo } from "react-icons/go";
import styled from "styled-components";

const Container = styled.div`
  position: relative;
  display: inline-block;
  margin: 20px;
`;

const InfoButton = styled.button`
  display: flex;
  align-items: center;
  gap: 4px;
  color: #555;
  cursor: pointer;
  background: none;
  border: none;
  padding: 0;

  &:hover {
    color: #000;
  }
`;

const Popover = styled.div`
  position: absolute;
  bottom: 100%;
  margin-bottom: 8px;
  min-width: 250px;
  width: max-content;
  padding: 12px;
  background: white;
  border: 1px solid #ddd;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 6px;
  font-size: 13px;
  z-index: 50;
`;

const SourceInfo: React.FC = () => {
  const [showInfo, setShowInfo] = useState(false);

  return (
    <Container
      onMouseEnter={() => setShowInfo(true)}
      onMouseLeave={() => setShowInfo(false)}
    >
      <InfoButton>
        <GoInfo size={16} />
        <span>출처</span>
      </InfoButton>

      {showInfo && (
        <Popover>
          <p>© 2025 Innerpeace. All rights reserved.</p>
          <p>
            데이터 제공: <strong>한국관광공사 TourAPI</strong>
            <br />
            출처: <strong>한국관광공사</strong>
          </p>
        </Popover>
      )}
    </Container>
  );
};

export default SourceInfo;
