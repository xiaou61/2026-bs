import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  HOSPITAL: '/waste',
  COLLECTOR: '/order',
  TRANSPORTER: '/manifest',
  DISPOSAL: '/disposal',
  REGULATOR: '/audit'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'waste', component: () => import('../views/WasteSource.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'REGULATOR'] } },
      { path: 'category', component: () => import('../views/WasteCategory.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR'] } },
      { path: 'package', component: () => import('../views/WastePackage.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR'] } },
      { path: 'order', component: () => import('../views/CollectionOrder.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER'] } },
      { path: 'weighing', component: () => import('../views/WeighingRecord.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER'] } },
      { path: 'storage', component: () => import('../views/StorageHandover.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR'] } },
      { path: 'manifest', component: () => import('../views/TransferManifest.vue'), meta: { roles: ['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR'] } },
      { path: 'track', component: () => import('../views/TransportTrack.vue'), meta: { roles: ['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR'] } },
      { path: 'disposal', component: () => import('../views/DisposalConfirm.vue'), meta: { roles: ['ADMIN', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] } },
      { path: 'exception', component: () => import('../views/ExceptionTrace.vue'), meta: { roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] } },
      { path: 'audit', component: () => import('../views/ComplianceAudit.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } }
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
