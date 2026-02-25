import React from 'react';
import './Navbar.css'; // Le CSS devra aussi être ajusté
import logo from '../assets/logo.png';

// Import des composants PrimeReact
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import { Avatar } from 'primereact/avatar';

export default function Navbar() {
  // Contenu de gauche de la barre d'outils
  const leftContents = (
    <React.Fragment>
      <Button icon="pi pi-bars" className="p-button-text p-button-lg" />
      <img src={logo} alt="SINDAI Logo" className="logo" />
      <span className="nav-title"></span>
    </React.Fragment>
  );

  // Contenu de droite de la barre d'outils
  const rightContents = (
    <React.Fragment>
      <Button
        label="FRANÇAIS"
        icon="pi pi-angle-down"
        iconPos="right"
        className="p-button-text language-selector" />
      <Avatar label="G" size="large" shape="circle" className="user-profile" />
    </React.Fragment>
  );

  return (
    <header className="header">

      {/* TOP BAR */}
      <div className="top-bar">
        <div className="container">
          <div className="top-bar-content">
            <span className="icon">■</span> GESTION DES DECLARATION ARCHIVEES SINDA2000 ▼
          </div>
        </div>
      </div>

      {/* NAVBAR */}
      <div className="nav-container">
        <div className="container toolbar-inner">
          <Toolbar
            start={leftContents}
            end={rightContents}
            className="main-nav-toolbar"
          />
        </div>
      </div>

    </header>
  );


}
