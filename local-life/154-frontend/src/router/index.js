import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RECEPTION: '/appointment',
  DOCTOR: '/visit',
  NURSE: '/vaccine-plan',
  OWNER: '/follow-up'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'NURSE', 'OWNER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'owner', component: () => import('../views/PetOwner.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'OWNER'] } },
      { path: 'pet', component: () => import('../views/PetProfile.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER'] } },
      { path: 'doctor', component: () => import('../views/DoctorProfile.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DOCTOR'] } },
      { path: 'schedule', component: () => import('../views/ClinicSchedule.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DOCTOR'] } },
      { path: 'appointment', component: () => import('../views/VisitAppointment.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER'] } },
      { path: 'visit', component: () => import('../views/VisitRecord.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'NURSE'] } },
      { path: 'vaccine-plan', component: () => import('../views/VaccinePlan.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'NURSE', 'OWNER'] } },
      { path: 'vaccine-record', component: () => import('../views/VaccineRecord.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'NURSE'] } },
      { path: 'follow-up', component: () => import('../views/FollowUpRecord.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'NURSE', 'OWNER'] } },
      { path: 'medicine', component: () => import('../views/MedicineStock.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'NURSE'] } },
      { path: 'billing', component: () => import('../views/BillingRecord.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'OWNER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'RECEPTION'] } }
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
