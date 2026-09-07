import { Navigate } from 'react-router-dom'
import useUserStore from '../store/useUserStore'

export const getDefaultRouteForRole = (role) => {
  if (role === 'DOCTOR') {
    return '/doctor/dashboard'
  }
  if (role === 'ADMIN') {
    return '/admin/dashboard'
  }
  return '/patient/dashboard'
}

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { token, user } = useUserStore(state => ({
    token: state.token,
    user: state.user
  }))
  
  if (!token) {
    return <Navigate to="/login" replace />
  }

  if (allowedRoles?.length && !allowedRoles.includes(user?.role)) {
    return <Navigate to={getDefaultRouteForRole(user?.role)} replace />
  }
  
  return children
}

export default ProtectedRoute
