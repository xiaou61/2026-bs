import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Layout from './components/Layout'
import PatientDashboard from './pages/patient/Dashboard'
import HealthData from './pages/patient/HealthData'
import HealthRecord from './pages/patient/HealthRecord'
import Consultation from './pages/patient/Consultation'
import PatientProfile from './pages/patient/Profile'
import DoctorDashboard from './pages/doctor/Dashboard'
import DoctorConsultation from './pages/doctor/Consultation'
import DoctorProfile from './pages/doctor/Profile'
import AdminDashboard from './pages/admin/Dashboard'
import AdminUsers from './pages/admin/Users'
import AdminDoctors from './pages/admin/Doctors'
import KnowledgeList from './pages/Knowledge'
import ProtectedRoute from './components/ProtectedRoute'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        
        <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
          <Route index element={<Navigate to="/patient/dashboard" replace />} />
          
          <Route path="patient/dashboard" element={<PatientDashboard />} />
          <Route path="patient/health-data" element={<HealthData />} />
          <Route path="patient/health-record" element={<HealthRecord />} />
          <Route path="patient/consultation" element={<Consultation />} />
          <Route path="patient/profile" element={<PatientProfile />} />
          
          <Route path="doctor/dashboard" element={<DoctorDashboard />} />
          <Route path="doctor/consultation" element={<DoctorConsultation />} />
          <Route path="doctor/profile" element={<DoctorProfile />} />
          
          <Route path="admin/dashboard" element={<AdminDashboard />} />
          <Route path="admin/users" element={<AdminUsers />} />
          <Route path="admin/doctors" element={<AdminDoctors />} />
          
          <Route path="knowledge" element={<KnowledgeList />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
