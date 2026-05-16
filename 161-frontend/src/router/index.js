import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SERVICE: '/lost',
  SECURITY: '/found',
  SCENIC: '/area',
  BROADCAST: '/notice',
  VISITOR: '/claim'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC', 'BROADCAST', 'VISITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/ScenicArea.vue'), meta: { roles: ['ADMIN', 'SCENIC', 'SERVICE'] } },
      { path: 'lost', component: () => import('../views/LostItem.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR'] } },
      { path: 'found', component: () => import('../views/FoundReport.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR'] } },
      { path: 'claim', component: () => import('../views/ClaimApplication.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'VISITOR'] } },
      { path: 'verify', component: () => import('../views/IdentityVerify.vue'), meta: { roles: ['ADMIN', 'SERVICE'] } },
      { path: 'trace', component: () => import('../views/LocationTrace.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'SCENIC', 'SERVICE'] } },
      { path: 'custody', component: () => import('../views/CustodyRecord.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'SECURITY'] } },
      { path: 'notice', component: () => import('../views/NoticeBroadcast.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'BROADCAST', 'SCENIC'] } },
      { path: 'handover', component: () => import('../views/HandoverRecord.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'VISITOR'] } },
      { path: 'search', component: () => import('../views/SearchTask.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC'] } },
      { path: 'feedback', component: () => import('../views/FeedbackRecord.vue'), meta: { roles: ['ADMIN', 'SERVICE', 'VISITOR', 'BROADCAST'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SCENIC'] } }
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
