// src/components/Menu.jsx
import { NavLink } from "react-router-dom";

export default function Menu() {
  return (
    <nav className="sidebar" aria-label="Hovedmenu">
      <p className="menu-title">Menu</p>

      <ul className="menu">
        <li>
          <NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>
            Notifikationer
          </NavLink>
        </li>
        <li>
          <NavLink to="/emails" className={({ isActive }) => (isActive ? "active" : "")}>
            E-mails
          </NavLink>
        </li>
        <li>
          <NavLink to="/regler" className={({ isActive }) => (isActive ? "active" : "")}>
            Regler
          </NavLink>
        </li>
      </ul>

      <div className="submenu">
        <p className="submenu-title">Administrer:</p>
        <ul>
          <li>
            <NavLink to="/servicecentre" className={({ isActive }) => (isActive ? "active" : "")}>
              Servicecentre
            </NavLink>
          </li>
          <li>
            <NavLink to="/personale" className={({ isActive }) => (isActive ? "active" : "")}>
              Personale
            </NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
}
