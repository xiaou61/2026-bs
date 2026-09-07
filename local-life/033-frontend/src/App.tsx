import { Routes, Route, Navigate } from 'react-router-dom'
import AppLayout from './components/AppLayout'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Customers from './pages/Customers'
import Packages from './pages/Packages'
import Appointments from './pages/Appointments'
import Orders from './pages/Orders'
import Photographers from './pages/Photographers'
import Studios from './pages/Studios'
import Costumes from './pages/Costumes'
import Photos from './pages/Photos'
import Finance from './pages/Finance'
import Config from './pages/Config'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<AppLayout />}>
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="customers" element={<Customers />} />
        <Route path="packages" element={<Packages />} />
        <Route path="appointments" element={<Appointments />} />
        <Route path="orders" element={<Orders />} />
        <Route path="photographers" element={<Photographers />} />
        <Route path="studios" element={<Studios />} />
        <Route path="costumes" element={<Costumes />} />
        <Route path="photos" element={<Photos />} />
        <Route path="finance" element={<Finance />} />
        <Route path="config" element={<Config />} />
      </Route>
    </Routes>
  )
}

export default App
