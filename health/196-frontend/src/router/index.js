import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PHARMACY: '/store',
  PHARMACIST: '/review',
  CLERK: '/purchase',
  FOLLOWUP: '/reminder',
  CUSTOMER: '/prescription'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'FOLLOWUP', 'CUSTOMER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'store', component: () => import('../views/PharmacyStore.vue'), meta: { roles: ['ADMIN', 'PHARMACY'] } },
      { path: 'customer', component: () => import('../views/CustomerProfile.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] } },
      { path: 'medicine', component: () => import('../views/MedicineCatalog.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK'] } },
      { path: 'prescription', component: () => import('../views/PrescriptionRecord.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] } },
      { path: 'review', component: () => import('../views/PrescriptionReview.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] } },
      { path: 'risk', component: () => import('../views/RiskCheck.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] } },
      { path: 'purchase', component: () => import('../views/PurchaseRecord.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'CLERK', 'CUSTOMER'] } },
      { path: 'guide', component: () => import('../views/MedicationGuide.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] } },
      { path: 'plan', component: () => import('../views/ChronicPlan.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] } },
      { path: 'reminder', component: () => import('../views/RenewalReminder.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] } },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue'), meta: { roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] } },
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
