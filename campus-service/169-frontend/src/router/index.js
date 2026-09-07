import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SCHOOL: '/route',
  DISPATCH: '/reservation',
  DRIVER: '/boarding',
  TEACHER: '/dropoff',
  GUARDIAN: '/notice'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'route', component: () => import('../views/BusRoute.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN'] } },
      { path: 'stop', component: () => import('../views/BusStop.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN'] } },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER'] } },
      { path: 'driver', component: () => import('../views/DriverProfile.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN'] } },
      { path: 'guardian', component: () => import('../views/GuardianProfile.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN'] } },
      { path: 'reservation', component: () => import('../views/RideReservation.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN'] } },
      { path: 'boarding', component: () => import('../views/BoardingCheckin.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] } },
      { path: 'dropoff', component: () => import('../views/DropoffCheckin.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] } },
      { path: 'exception', component: () => import('../views/ExceptionAlert.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] } },
      { path: 'notice', component: () => import('../views/ParentNotice.vue'), meta: { roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SCHOOL'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
