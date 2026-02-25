import React from 'react';

const PageHeader = ({ subtitle, title }) => {
  return (
    <header className="page-header">
      <div>
        <p className="header-subtitle">{subtitle}</p>
        <h1 className="header-title">{title}</h1>
      </div>
    </header>
  );
};

export default PageHeader;
