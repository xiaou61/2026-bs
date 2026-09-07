import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CENTER: '/patient',
  DOCTOR: '/disease',
  NURSE: '/followup',
  PHARMACIST: '/medication',
  RESIDENT: '/adherence'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'PHARMACIST', 'RESIDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'clinic', component: () => import('../views/CommunityClinic.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] } },
      { path: 'patient', component: () => import('../views/ChronicPatient.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] } },
      { path: 'team', component: () => import('../views/DoctorTeam.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] } },
      { path: 'disease', component: () => import('../views/DiseaseArchive.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] } },
      { path: 'plan', component: () => import('../views/FollowupPlan.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] } },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] } },
      { path: 'medication', component: () => import('../views/MedicationPlan.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'PHARMACIST', 'RESIDENT'] } },
      { path: 'adherence', component: () => import('../views/MedicationAdherence.vue'), meta: { roles: ['ADMIN', 'CENTER', 'NURSE', 'PHARMACIST', 'RESIDENT'] } },
      { path: 'indicator', component: () => import('../views/HealthIndicator.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] } },
      { path: 'risk', component: () => import('../views/RiskStratification.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] } },
      { path: 'notice', component: () => import('../views/ReminderNotice.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'PHARMACIST', 'RESIDENT'] } },
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
