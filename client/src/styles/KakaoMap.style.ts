import styled from "styled-components";

export const KakaoContainer = styled.div`
  width: 100%;
  height: 100%;
  flex: 1;

  .map {
    width: 100%;
    height: 100%;
  }
`;

export const MarkerTooltip = styled.div`
  min-width: 300px;
  max-width: 500px;
  position: absolute;
  bottom: 25px;
  left: 50%;
  transform: translateX(-50%);
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 8px 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  z-index: 1000;
  white-space: normal;
  text-align: left;

  /* 말풍선 꼬리 */
  &::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    transform: translateX(-50%);
    border: 6px solid transparent;
    border-top-color: white;
  }

  /* 호버 애니메이션 */
  animation: fadeIn 0.2s ease-in-out;

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateX(-50%) translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateX(-50%) translateY(0);
    }
  }

  div {
    margin: 2px 0;

    &:first-child {
      font-weight: bold;
      color: #333;
    }

    &:last-child {
      color: #666;
      font-size: 12px;
    }
  }

  img {
    min-width: 100%;
    min-height: 100px;
    border: 1px solid black;
  }
`;

export const CustomMarker = styled.div<{ $color: string }>`
  --marker-size: 30px;
  display: flex;
  align-items: center;
  z-index: 100;
  justify-content: center;
  width: var(--marker-size);
  height: var(--marker-size);
  border: 1px solid white;
  border-radius: 50%;
  background: ${({ $color }) => $color};
  backdrop-filter: blur(5px);
  color: white;
  box-shadow: 0 0 3px 1px gray;
`;
