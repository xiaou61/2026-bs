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
      { path: 'project', component: () => import('../views/ResearchProject.vue') },
      { path: 'category', component: () => import('../views/BudgetCategory.vue') },
      { path: 'budget', component: () => import('../views/BudgetAllocation.vue') },
      { path: 'claim', component: () => import('../views/ExpenseClaim.vue') },
      { path: 'invoice', component: () => import('../views/InvoiceRecord.vue') },
      { path: 'approval', component: () => import('../views/ApprovalTask.vue') },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue') },
      { path: 'achievement', component: () => import('../views/ResearchAchievement.vue') },
      { path: 'paper', component: () => import('../views/PaperRecord.vue') },
      { path: 'patent', component: () => import('../views/PatentRecord.vue') },
      { path: 'statistic', component: () => import('../views/PerformanceStatistic.vue') },
      { path: 'risk', component: () => import('../views/RiskWarning.vue') },
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
