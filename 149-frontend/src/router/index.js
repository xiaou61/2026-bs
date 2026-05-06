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
      { path: 'asset', component: () => import('../views/EquipmentAsset.vue') },
      { path: 'lab', component: () => import('../views/LaboratoryRoom.vue') },
      { path: 'category', component: () => import('../views/EquipmentCategory.vue') },
      { path: 'borrow-user', component: () => import('../views/BorrowUser.vue') },
      { path: 'reservation', component: () => import('../views/ReservationRequest.vue') },
      { path: 'borrow-record', component: () => import('../views/BorrowRecord.vue') },
      { path: 'usage', component: () => import('../views/UsageRegistration.vue') },
      { path: 'violation', component: () => import('../views/ViolationRecord.vue') },
      { path: 'maintenance', component: () => import('../views/MaintenanceWorkOrder.vue') },
      { path: 'return-confirm', component: () => import('../views/ReturnConfirmation.vue') },
      { path: 'inspection', component: () => import('../views/InspectionPlan.vue') },
      { path: 'notice', component: () => import('../views/SystemNotice.vue') },
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











