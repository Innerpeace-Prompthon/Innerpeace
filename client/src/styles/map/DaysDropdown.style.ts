import styled from "styled-components";

export const SelectWrapper = styled.div`
  position: relative;
`;

export const SelectViewValue = styled.div`
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  background-color: #f9f9f9;
`;

export const SelectOptionList = styled.ul`
  position: absolute;
  z-index: 10;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-top: 4px;
  width: 100%;
`;

export const SelectOptionItem = styled.li<{ $isSelected: boolean }>`
  padding: 8px;
  display: flex;
  flex-direction: column;
  cursor: pointer;
  background-color: ${({ $isSelected }) =>
    $isSelected ? "rgba(0, 0, 0, 0.05)" : "transparent"};
`;

export const SelectedColorBox = styled.div<{ $color: string }>`
  width: 16px;
  height: 16px;
  background-color: ${({ $color }) => $color};
  border-radius: 50%;
  border: 1px solid #ccc;
`;

export const MarkerColorBox = styled.div`
  display: flex;
  align-items: center;
  margin-top: 6px;

  button {
    margin-left: 8px;
    padding: 4px 8px;
    cursor: pointer;
    border: 1px solid #888;
    border-radius: 4px;
    background-color: #eee;
  }
`;
