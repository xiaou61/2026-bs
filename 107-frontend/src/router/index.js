import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'
const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  { path: '/', component: () => import('../views/Layout.vue'), redirect: '/dashboard', children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
  { path: 'region', component: () => import('../views/CloudRegion.vue') },
  { path: 'asset', component: () => import('../views/ServerAsset.vue') },
  { path: 'group', component: () => import('../views/ResourceGroup.vue') },
  { path: 'metric', component: () => import('../views/MetricDefinition.vue') },
  { path: 'sample', component: () => import('../views/MetricSample.vue') },
  { path: 'rule', component: () => import('../views/AlertRule.vue') },
  { path: 'event', component: () => import('../views/AlertEvent.vue') },
  { path: 'notify', component: () => import('../views/AlertNotify.vue') },
  { path: 'ticket', component: () => import('../views/IncidentTicket.vue') },
  { path: 'maintenance', component: () => import('../views/MaintenanceWindow.vue') },
  { path: 'capacity', component: () => import('../views/CapacityPlan.vue') },
  { path: 'widget', component: () => import('../views/DashboardWidget.vue') },
  { path: 'log', component: () => import('../views/OperationLog.vue') },
  ] }
]
const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})
export default router
