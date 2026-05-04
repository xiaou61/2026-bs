import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },      { path: 'user', component: () => import('../views/SysUser.vue') },      { path: 'account', component: () => import('../views/CloudAccount.vue') },      { path: 'namespace', component: () => import('../views/ResourceNamespace.vue') },      { path: 'bill', component: () => import('../views/CostBill.vue') },      { path: 'cost-item', component: () => import('../views/CostItem.vue') },      { path: 'budget', component: () => import('../views/BudgetPolicy.vue') },      { path: 'allocation', component: () => import('../views/CostAllocation.vue') },      { path: 'idle-resource', component: () => import('../views/IdleResource.vue') },      { path: 'optimization-rule', component: () => import('../views/OptimizationRule.vue') },      { path: 'advice', component: () => import('../views/OptimizationAdvice.vue') },      { path: 'saving-plan', component: () => import('../views/SavingPlan.vue') },      { path: 'anomaly', component: () => import('../views/AnomalyEvent.vue') },      { path: 'report', component: () => import('../views/ReportSnapshot.vue') },      { path: 'log', component: () => import('../views/OperationLog.vue') }
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
