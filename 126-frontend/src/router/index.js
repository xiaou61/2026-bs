import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RESIDENT: '/home',
  ANALYST: '/suggestion',
  MAINTAINER: '/ticket'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'home', component: () => import('../views/HomeProfile.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'member', component: () => import('../views/FamilyMember.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'meter', component: () => import('../views/SmartMeter.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'device', component: () => import('../views/ElectricDevice.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'reading', component: () => import('../views/EnergyReading.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'bill', component: () => import('../views/ElectricityBill.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'usage', component: () => import('../views/DeviceUsage.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'budget', component: () => import('../views/EnergyBudget.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'suggestion', component: () => import('../views/SavingSuggestion.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
      { path: 'carbon', component: () => import('../views/CarbonStatistic.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST'] } },
      { path: 'ticket', component: () => import('../views/MaintenanceTicket.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] } },
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
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
