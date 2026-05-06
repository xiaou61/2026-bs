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
      { path: 'plan', component: () => import('../views/InspectionPlan.vue') },
      { path: 'food', component: () => import('../views/FoodItem.vue') },
      { path: 'merchant', component: () => import('../views/MerchantProfile.vue') },
      { path: 'task', component: () => import('../views/SamplingTask.vue') },
      { path: 'agency', component: () => import('../views/AgencyProfile.vue') },
      { path: 'sample', component: () => import('../views/SampleRecord.vue') },
      { path: 'result', component: () => import('../views/TestResult.vue') },
      { path: 'recheck', component: () => import('../views/RecheckApplication.vue') },
      { path: 'disposal', component: () => import('../views/DisposalRecord.vue') },
      { path: 'report', component: () => import('../views/PublicReport.vue') },
      { path: 'warning', component: () => import('../views/RiskWarning.vue') },
      { path: 'notice', component: () => import('../views/InspectionNotice.vue') },
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









