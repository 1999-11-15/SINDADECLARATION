import React from "react";
import Navbar from "./Navbar";

export default function Layout({ children }) {
  return (
    <>
      <Navbar />
      <div style={{ padding: "20px" }}>
        {children}
      </div>
    </>
  );
}
