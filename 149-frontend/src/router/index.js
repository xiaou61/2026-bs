import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TEACHER: '/reservation',
  STUDENT: '/reservation',
  MANAGER: '/asset'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'asset', component: () => import('../views/EquipmentAsset.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'lab', component: () => import('../views/LaboratoryRoom.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'category', component: () => import('../views/EquipmentCategory.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'borrow-user', component: () => import('../views/BorrowUser.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'reservation', component: () => import('../views/ReservationRequest.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] } },
      { path: 'borrow-record', component: () => import('../views/BorrowRecord.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'usage', component: () => import('../views/UsageRegistration.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'violation', component: () => import('../views/ViolationRecord.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceWorkOrder.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'return-confirm', component: () => import('../views/ReturnConfirmation.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'inspection', component: () => import('../views/InspectionPlan.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'MANAGER'] } },
      { path: 'notice', component: () => import('../views/SystemNotice.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] } },
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











