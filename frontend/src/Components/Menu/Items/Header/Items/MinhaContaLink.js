import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import '../Header.css';
import { faUser, faClipboardList, faSignOutAlt, faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AuthContext } from '../../../../../Util/Authentication';

const MinhaContaLink = ({ activeLink, handleSetActive }) => {
    const [isDropdownVisible, setDropdownVisible] = useState(false);
    const { logout } = useContext(AuthContext);
    let timer;

    const handleMouseEnter = () => {
        clearTimeout(timer);
        setDropdownVisible(true);
    };

    const handleMouseLeave = () => {
        timer = setTimeout(() => {
            setDropdownVisible(false);
        }, 100); 
    };

    const handleLogout = () => {
        logout();
        handleSetActive("");
    };

    return (
        <div className="dropdown" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <button className="auth-link">
                <FontAwesomeIcon icon={faUser} className="icon" />
                Minha Conta
                <FontAwesomeIcon icon={isDropdownVisible ? faCaretUp : faCaretDown} className="caret-icon" />
            </button>
            {isDropdownVisible && (
                <div className="dropdown-menu">
                    <Link to="/perfil" onClick={() => handleSetActive("perfil")}>
                        <FontAwesomeIcon icon={faUser} className="menu-icon" />
                        Perfil
                    </Link>
                    <Link to="/pedidos" onClick={() => handleSetActive("pedidos")}>
                        <FontAwesomeIcon icon={faClipboardList} className="menu-icon" />
                        Pedidos
                    </Link>
                    <Link as="button" onClick={handleLogout}>
                        <FontAwesomeIcon icon={faSignOutAlt} className="menu-icon" />
                        Logout
                    </Link>
                </div>
            )}
        </div>
    );
};

export default MinhaContaLink;