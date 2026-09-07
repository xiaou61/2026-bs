import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RESEARCHER: '/project',
  FINANCE: '/budget',
  LEADER: '/approval'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'project', component: () => import('../views/ResearchProject.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
      { path: 'category', component: () => import('../views/BudgetCategory.vue'), meta: { roles: ['ADMIN', 'FINANCE', 'RESEARCHER', 'LEADER'] } },
      { path: 'budget', component: () => import('../views/BudgetAllocation.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
      { path: 'claim', component: () => import('../views/ExpenseClaim.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'LEADER'] } },
      { path: 'invoice', component: () => import('../views/InvoiceRecord.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE'] } },
      { path: 'approval', component: () => import('../views/ApprovalTask.vue'), meta: { roles: ['ADMIN', 'LEADER'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
      { path: 'achievement', component: () => import('../views/ResearchAchievement.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
      { path: 'paper', component: () => import('../views/PaperRecord.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
      { path: 'patent', component: () => import('../views/PatentRecord.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
      { path: 'statistic', component: () => import('../views/PerformanceStatistic.vue'), meta: { roles: ['ADMIN', 'FINANCE', 'LEADER'] } },
      { path: 'risk', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] } },
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
