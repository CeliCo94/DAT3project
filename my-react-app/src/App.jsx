import { Outlet } from 'react-router-dom'
import Header from "../src/components/Header.jsx";
import Menu from "../src/components/Menu.jsx";

export default function App() {
  return (
    <>
      <aside><Header /></aside>

          <div className="layout">
            <aside><Menu /></aside>
        
          <main className="content">
            <Outlet />
          </main>
          </div>
    </>
  )
}
