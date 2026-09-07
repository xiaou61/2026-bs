import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CONSULTANT: '/order',
  CAREGIVER: '/checkin',
  FAMILY: '/order'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'package', component: () => import('../views/ServicePackage.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'elder', component: () => import('../views/ElderProfile.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'caregiver', component: () => import('../views/CaregiverProfile.vue'), meta: { roles: ['ADMIN', 'CONSULTANT'] } },
      { path: 'order', component: () => import('../views/ServiceOrder.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'team', component: () => import('../views/CareTeam.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER'] } },
      { path: 'checkin', component: () => import('../views/VisitCheckin.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER'] } },
      { path: 'record', component: () => import('../views/ServiceRecord.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'assessment', component: () => import('../views/HealthAssessment.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'reminder', component: () => import('../views/MedicationReminder.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'family', component: () => import('../views/FamilyVisit.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'FAMILY'] } },
      { path: 'alert', component: () => import('../views/AlertEvent.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'notice', component: () => import('../views/CareNotice.vue'), meta: { roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'

  if (to.path !== '/login' && !userStore.token) {
    next('/login')
    return
  }
  if (to.path === '/login' && userStore.token) {
    next(home)
    return
  }
  if (to.path === '/' && userStore.token) {
    next(home)
    return
  }
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
