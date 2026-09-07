import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  NURSE: '/request',
  STERILIZER: '/batch',
  MANAGER: '/dashboard'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'device', component: () => import('../views/DeviceProfile.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'category', component: () => import('../views/DeviceCategory.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'department', component: () => import('../views/DepartmentInfo.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'request', component: () => import('../views/BorrowRequest.vue'), meta: { roles: ['ADMIN', 'NURSE', 'MANAGER'] } },
      { path: 'borrow', component: () => import('../views/BorrowRecord.vue'), meta: { roles: ['ADMIN', 'NURSE', 'MANAGER'] } },
      { path: 'return', component: () => import('../views/ReturnRecord.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'batch', component: () => import('../views/SterilizationBatch.vue'), meta: { roles: ['ADMIN', 'STERILIZER', 'MANAGER'] } },
      { path: 'sterilization', component: () => import('../views/SterilizationRecord.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'trace', component: () => import('../views/QrTrace.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'inspection', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'alert', component: () => import('../views/RiskAlert.vue'), meta: { roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] } },
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
