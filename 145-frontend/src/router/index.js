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
      { path: 'complaint', component: () => import('../views/ComplaintTicket.vue') },
      { path: 'category', component: () => import('../views/MonitoringSite.vue') },
      { path: 'source', component: () => import('../views/NoiseSource.vue') },
      { path: 'officer', component: () => import('../views/OfficerProfile.vue') },
      { path: 'task', component: () => import('../views/HandlingTask.vue') },
      { path: 'patrol', component: () => import('../views/PatrolRecord.vue') },
      { path: 'rectify', component: () => import('../views/RectificationNotice.vue') },
      { path: 'retest', component: () => import('../views/RetestRecord.vue') },
      { path: 'penalty', component: () => import('../views/PenaltyDecision.vue') },
      { path: 'feedback', component: () => import('../views/PublicFeedback.vue') },
      { path: 'rule', component: () => import('../views/WarningRule.vue') },
      { path: 'notice', component: () => import('../views/PublicNotice.vue') },
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









