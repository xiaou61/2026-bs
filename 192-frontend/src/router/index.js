import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  HOSPITAL: '/ward',
  COORDINATOR: '/appointment',
  CAREGIVER: '/schedule',
  FINANCE: '/settlement',
  FAMILY: '/appointment'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER', 'FINANCE', 'FAMILY'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'ward', component: () => import('../views/HospitalWard.vue'), meta: { roles: ['ADMIN', 'HOSPITAL'] } },
      { path: 'patient', component: () => import('../views/PatientProfile.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] } },
      { path: 'caregiver', component: () => import('../views/CaregiverProfile.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER'] } },
      { path: 'appointment', component: () => import('../views/CareAppointment.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] } },
      { path: 'review', component: () => import('../views/AppointmentReview.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR'] } },
      { path: 'schedule', component: () => import('../views/CaregiverSchedule.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER'] } },
      { path: 'assignment', component: () => import('../views/ServiceAssignment.vue'), meta: { roles: ['ADMIN', 'COORDINATOR', 'CAREGIVER'] } },
      { path: 'service', component: () => import('../views/CareServiceRecord.vue'), meta: { roles: ['ADMIN', 'COORDINATOR', 'CAREGIVER', 'FAMILY'] } },
      { path: 'communication', component: () => import('../views/FamilyCommunication.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] } },
      { path: 'evaluation', component: () => import('../views/CareEvaluation.vue'), meta: { roles: ['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY'] } },
      { path: 'settlement', component: () => import('../views/SettlementRecord.vue'), meta: { roles: ['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
