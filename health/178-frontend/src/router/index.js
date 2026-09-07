import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ORNURSE: '/surgery',
  CSSD: '/pack',
  STERILE: '/sterilization',
  QA: '/release',
  SURGEON: '/trace'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA', 'SURGEON'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'room', component: () => import('../views/OperatingRoom.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'SURGEON'] } },
      { path: 'pack', component: () => import('../views/InstrumentPack.vue'), meta: { roles: ['ADMIN', 'CSSD', 'ORNURSE', 'SURGEON'] } },
      { path: 'item', component: () => import('../views/InstrumentItem.vue'), meta: { roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] } },
      { path: 'trace', component: () => import('../views/PackTrace.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA', 'SURGEON'] } },
      { path: 'batch', component: () => import('../views/SterilizationBatch.vue'), meta: { roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] } },
      { path: 'sterilization', component: () => import('../views/SterilizationRecord.vue'), meta: { roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] } },
      { path: 'release', component: () => import('../views/ReleaseApproval.vue'), meta: { roles: ['ADMIN', 'QA', 'CSSD', 'ORNURSE'] } },
      { path: 'surgery', component: () => import('../views/SurgeryUse.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'SURGEON', 'CSSD'] } },
      { path: 'return', component: () => import('../views/ReturnCheckin.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE'] } },
      { path: 'defect', component: () => import('../views/DefectReport.vue'), meta: { roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA'] } },
      { path: 'recall', component: () => import('../views/RecallEvent.vue'), meta: { roles: ['ADMIN', 'QA', 'CSSD', 'ORNURSE', 'SURGEON'] } },
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
