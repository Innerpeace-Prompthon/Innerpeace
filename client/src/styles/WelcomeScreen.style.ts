import styled from "styled-components";

export const WelcomeContainer = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  text-align: center;
  background-color: white;
  gap: 24px;
`;

export const WelcomeTitle = styled.h2`
  color: #b7d37a;
  margin-bottom: 30px;

  p {
    font-size: 20px;
    color: #c6c6c6;
    font-weight: 400;
  }

  h1 {
    font-size: 50px;
    margin-top: 5px;
  }
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 16px;
  width: 100%;
  max-width: 400px;
  padding: 24px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
`;

export const Label = styled.label`
  font-weight: 500;
  color: #333;
  text-align: left;
  margin-right: 7px;
  font-weight: bold;
`;

export const Input = styled.input`
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
`;

export const Select = styled.select`
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
`;
