import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RECEPTION: '/appointment',
  EMPLOYEE: '/approval',
  SECURITY: '/entry',
  VISITOR: '/appointment',
  MANAGER: '/alert'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'SECURITY', 'VISITOR', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'zone', component: () => import('../views/CompanyZone.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] } },
      { path: 'host', component: () => import('../views/HostEmployee.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'visitor', component: () => import('../views/VisitorProfile.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'EMPLOYEE', 'VISITOR'] } },
      { path: 'appointment', component: () => import('../views/VisitAppointment.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'VISITOR', 'MANAGER'] } },
      { path: 'approval', component: () => import('../views/ApprovalRecord.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'qrcode', component: () => import('../views/QrPass.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR'] } },
      { path: 'gate', component: () => import('../views/GateDevice.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'MANAGER'] } },
      { path: 'linkage', component: () => import('../views/AccessLinkage.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] } },
      { path: 'entry', component: () => import('../views/EntryRecord.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR', 'EMPLOYEE'] } },
      { path: 'trail', component: () => import('../views/TrailRecord.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'alert', component: () => import('../views/ExceptionAlert.vue'), meta: { roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } }
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
