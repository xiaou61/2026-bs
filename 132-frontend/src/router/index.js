import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
      { path: 'device', component: () => import('../views/DeviceProfile.vue') },
      { path: 'category', component: () => import('../views/DeviceCategory.vue') },
      { path: 'department', component: () => import('../views/DepartmentInfo.vue') },
      { path: 'request', component: () => import('../views/BorrowRequest.vue') },
      { path: 'borrow', component: () => import('../views/BorrowRecord.vue') },
      { path: 'return', component: () => import('../views/ReturnRecord.vue') },
      { path: 'batch', component: () => import('../views/SterilizationBatch.vue') },
      { path: 'sterilization', component: () => import('../views/SterilizationRecord.vue') },
      { path: 'trace', component: () => import('../views/QrTrace.vue') },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue') },
      { path: 'inspection', component: () => import('../views/InspectionTask.vue') },
      { path: 'alert', component: () => import('../views/RiskAlert.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router
