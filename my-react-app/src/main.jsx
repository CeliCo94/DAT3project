import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from './App.jsx'
import Home from './pages/Home.jsx'
import Emails from './pages/Emails.jsx'
import Opret from './pages/Opret.jsx'
import Personale from './pages/Personale.jsx'
import Regler from './pages/Regler.jsx'
import Servicecentre from './pages/Servicecentre.jsx'
import './index.css'

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'emails', element: <Emails /> },
      { path: 'opret', element: <Opret /> },
      { path: 'personale', element: <Personale /> },
      { path: 'regler', element: <Regler /> },
      { path: 'servicecentre', element: <Servicecentre /> }
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)
