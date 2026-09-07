import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  DISPATCH: '/task',
  PATROL: '/record',
  REPAIR: '/receipt',
  WAREHOUSE: '/spare',
  SUPERVISOR: '/warning'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'WAREHOUSE', 'SUPERVISOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'station', component: () => import('../views/WaterStation.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'SUPERVISOR'] } },
      { path: 'section', component: () => import('../views/PipelineSection.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] } },
      { path: 'route', component: () => import('../views/PatrolRoute.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] } },
      { path: 'valve', component: () => import('../views/ValveLedger.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] } },
      { path: 'task', component: () => import('../views/PatrolTask.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] } },
      { path: 'record', component: () => import('../views/PatrolRecord.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] } },
      { path: 'fault', component: () => import('../views/FaultReport.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] } },
      { path: 'dispatch', component: () => import('../views/DispatchOrder.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR'] } },
      { path: 'receipt', component: () => import('../views/MaintenanceReceipt.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR'] } },
      { path: 'spare', component: () => import('../views/SparePartLedger.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'WAREHOUSE'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] } },
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
