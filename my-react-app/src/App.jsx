import { NavLink, Outlet } from 'react-router-dom'
import Header from "../src/components/Header.jsx";

export default function App() {
  return (
    <>
      <aside><Header /></aside>
      <Outlet />
    </>
  )
}
