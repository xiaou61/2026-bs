import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  FINOPS: '/bill',
  DEVOPS: '/idle-resource',
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
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'account', component: () => import('../views/CloudAccount.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'DEVOPS', 'MANAGER'] } },
      { path: 'namespace', component: () => import('../views/ResourceNamespace.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'DEVOPS', 'MANAGER'] } },
      { path: 'bill', component: () => import('../views/CostBill.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'cost-item', component: () => import('../views/CostItem.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'budget', component: () => import('../views/BudgetPolicy.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'allocation', component: () => import('../views/CostAllocation.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'idle-resource', component: () => import('../views/IdleResource.vue'), meta: { roles: ['ADMIN', 'DEVOPS', 'MANAGER'] } },
      { path: 'optimization-rule', component: () => import('../views/OptimizationRule.vue'), meta: { roles: ['ADMIN', 'DEVOPS', 'MANAGER'] } },
      { path: 'advice', component: () => import('../views/OptimizationAdvice.vue'), meta: { roles: ['ADMIN', 'DEVOPS', 'MANAGER'] } },
      { path: 'saving-plan', component: () => import('../views/SavingPlan.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
      { path: 'anomaly', component: () => import('../views/AnomalyEvent.vue'), meta: { roles: ['ADMIN', 'DEVOPS', 'MANAGER'] } },
      { path: 'report', component: () => import('../views/ReportSnapshot.vue'), meta: { roles: ['ADMIN', 'FINOPS', 'MANAGER'] } },
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
