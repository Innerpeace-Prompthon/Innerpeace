import React from "react";

const Footer: React.FC = () => {
  return (
    <footer
      style={{
        backgroundColor: "#f8f9fa",
        textAlign: "center",
        padding: "20px",
        fontSize: "14px",
        color: "#555",
        borderTop: "1px solid #ccc",
      }}
    >
      <p>© 2025 Innerpeace. All rights reserved.</p>
      <p>
        데이터 제공: <strong>한국관광공사 TourAPI</strong> / 출처:{" "}
        <strong>한국관광공사</strong>
      </p>
    </footer>
  );
};

export default Footer;
