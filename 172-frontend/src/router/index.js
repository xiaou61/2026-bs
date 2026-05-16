import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RECEPTION: '/appointment',
  DENTIST: '/treatment-record',
  NURSE: '/triage',
  CASHIER: '/billing',
  PATIENT: '/appointment'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'CASHIER', 'PATIENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'room', component: () => import('../views/ClinicRoom.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE'] } },
      { path: 'dentist', component: () => import('../views/DentistProfile.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST'] } },
      { path: 'patient', component: () => import('../views/PatientProfile.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT'] } },
      { path: 'treatment', component: () => import('../views/TreatmentCatalog.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER'] } },
      { path: 'appointment', component: () => import('../views/AppointmentOrder.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT'] } },
      { path: 'triage', component: () => import('../views/CheckinTriage.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE'] } },
      { path: 'treatment-record', component: () => import('../views/TreatmentRecord.vue'), meta: { roles: ['ADMIN', 'DENTIST', 'NURSE', 'PATIENT', 'CASHIER'] } },
      { path: 'consumable', component: () => import('../views/ConsumableCatalog.vue'), meta: { roles: ['ADMIN', 'NURSE', 'DENTIST', 'CASHIER'] } },
      { path: 'stock', component: () => import('../views/ConsumableStock.vue'), meta: { roles: ['ADMIN', 'NURSE', 'DENTIST', 'CASHIER'] } },
      { path: 'usage', component: () => import('../views/ConsumableUsage.vue'), meta: { roles: ['ADMIN', 'DENTIST', 'NURSE', 'CASHIER'] } },
      { path: 'billing', component: () => import('../views/BillingRecord.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER', 'PATIENT'] } },
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
