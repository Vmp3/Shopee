import React, { useState, useContext } from 'react';
import './Header.css';
import MenuButtonComponent from './Items/MenuButton';
import SearchInputComponent from './Items/SearchInput';
import LoginLink from './Items/LoginLink';
import RegisterLink from './Items/RegisterLink';
import MinhaContaLink from './Items/MinhaContaLink'; // Novo componente
import CartLink from './Items/CartLink';
import FavoritesLink from './Items/FavoritesLink';
import NavBar from '../NavBar/NavBar';
import { AuthContext } from '../../../../Util/Authentication';

const Header = ({ searchHidden = false }) => {
  const [activeLink, setActiveLink] = useState("");
  const { isAuthenticated } = useContext(AuthContext);

  const handleSetActive = (link) => {
    setActiveLink(link);
  };

  return (
    <>
      <header className="header">
        <div className="header-content">
          <div className="header-left">
            <MenuButtonComponent />
            <a href="/" className="logo-link" aria-label="Página inicial KaBuM!">
              <img alt="Logo Kabum" src="https://static.kabum.com.br/conteudo/icons/logo.svg" width="105" height="36" />
            </a>
          </div>
          <div className="search-container">
            <SearchInputComponent hidden={searchHidden} />
          </div>
          <div className="header-right">
            {isAuthenticated ? (
              <MinhaContaLink
                activeLink={activeLink}
                handleSetActive={handleSetActive}
              />
            ) : (
              <>
                <LoginLink
                  activeLink={activeLink}
                  handleSetActive={handleSetActive}
                />
                <span>|</span>
                <RegisterLink
                  activeLink={activeLink}
                  handleSetActive={handleSetActive}
                />
              </>
            )}
            <CartLink
              activeLink={activeLink}
              handleSetActive={handleSetActive}
            />
            <FavoritesLink
              activeLink={activeLink}
              handleSetActive={handleSetActive}
            />
          </div>
        </div>
      </header>
      <NavBar />
    </>
  );
};

export default Header;