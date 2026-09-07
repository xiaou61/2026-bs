import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  COMMUNITY: '/center',
  AIDSTAFF: '/device',
  THERAPIST: '/plan',
  VOLUNTEER: '/followup',
  RESIDENT: '/borrow'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'THERAPIST', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'center', component: () => import('../views/ServiceCenter.vue'), meta: { roles: ['ADMIN', 'COMMUNITY'] } },
      { path: 'resident', component: () => import('../views/ResidentProfile.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'VOLUNTEER'] } },
      { path: 'device', component: () => import('../views/AssistiveDevice.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] } },
      { path: 'borrow', component: () => import('../views/BorrowApplication.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'RESIDENT'] } },
      { path: 'approval', component: () => import('../views/BorrowApproval.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] } },
      { path: 'delivery', component: () => import('../views/DeviceDelivery.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER'] } },
      { path: 'plan', component: () => import('../views/RehabPlan.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'RESIDENT'] } },
      { path: 'training', component: () => import('../views/RehabTraining.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'THERAPIST'] } },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'reminder', component: () => import('../views/RecoveryReminder.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER'] } },
      { path: 'maintenance', component: () => import('../views/DeviceMaintenance.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] } },
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
