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
import ProtectedRoute, { getDefaultRouteForRole } from './components/ProtectedRoute'
import useUserStore from './store/useUserStore'

function RoleHomeRedirect() {
  const role = useUserStore(state => state.user?.role)
  return <Navigate to={getDefaultRouteForRole(role)} replace />
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        
        <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
          <Route index element={<RoleHomeRedirect />} />
          
          <Route path="patient/dashboard" element={<ProtectedRoute allowedRoles={['PATIENT']}><PatientDashboard /></ProtectedRoute>} />
          <Route path="patient/health-data" element={<ProtectedRoute allowedRoles={['PATIENT']}><HealthData /></ProtectedRoute>} />
          <Route path="patient/health-record" element={<ProtectedRoute allowedRoles={['PATIENT']}><HealthRecord /></ProtectedRoute>} />
          <Route path="patient/consultation" element={<ProtectedRoute allowedRoles={['PATIENT']}><Consultation /></ProtectedRoute>} />
          <Route path="patient/profile" element={<ProtectedRoute allowedRoles={['PATIENT']}><PatientProfile /></ProtectedRoute>} />
          
          <Route path="doctor/dashboard" element={<ProtectedRoute allowedRoles={['DOCTOR']}><DoctorDashboard /></ProtectedRoute>} />
          <Route path="doctor/consultation" element={<ProtectedRoute allowedRoles={['DOCTOR']}><DoctorConsultation /></ProtectedRoute>} />
          <Route path="doctor/profile" element={<ProtectedRoute allowedRoles={['DOCTOR']}><DoctorProfile /></ProtectedRoute>} />
          
          <Route path="admin/dashboard" element={<ProtectedRoute allowedRoles={['ADMIN']}><AdminDashboard /></ProtectedRoute>} />
          <Route path="admin/users" element={<ProtectedRoute allowedRoles={['ADMIN']}><AdminUsers /></ProtectedRoute>} />
          <Route path="admin/doctors" element={<ProtectedRoute allowedRoles={['ADMIN']}><AdminDoctors /></ProtectedRoute>} />
          
          <Route path="knowledge" element={<KnowledgeList />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
