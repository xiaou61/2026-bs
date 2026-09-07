import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPS: '/event',
  SRE: '/rule',
  MANAGER: '/dashboard'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  { path: '/', component: () => import('../views/Layout.vue'), redirect: '/dashboard', children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SRE', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'region', component: () => import('../views/CloudRegion.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'asset', component: () => import('../views/ServerAsset.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'group', component: () => import('../views/ResourceGroup.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'metric', component: () => import('../views/MetricDefinition.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'sample', component: () => import('../views/MetricSample.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'rule', component: () => import('../views/AlertRule.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'event', component: () => import('../views/AlertEvent.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'notify', component: () => import('../views/AlertNotify.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'ticket', component: () => import('../views/IncidentTicket.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceWindow.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'capacity', component: () => import('../views/CapacityPlan.vue'), meta: { roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] } },
      { path: 'widget', component: () => import('../views/DashboardWidget.vue'), meta: { roles: ['ADMIN', 'SRE', 'MANAGER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SRE', 'MANAGER'] } },
  ] }
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
