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
      { path: 'company', component: () => import('../views/CompanyProfile.vue') },
      { path: 'factor', component: () => import('../views/EmissionFactor.vue') },
      { path: 'period', component: () => import('../views/AccountingPeriod.vue') },
      { path: 'consumption', component: () => import('../views/EnergyConsumption.vue') },
      { path: 'emission', component: () => import('../views/EmissionRecord.vue') },
      { path: 'task', component: () => import('../views/ReductionTask.vue') },
      { path: 'measure', component: () => import('../views/ReductionMeasure.vue') },
      { path: 'quota', component: () => import('../views/CarbonQuota.vue') },
      { path: 'report', component: () => import('../views/VerificationReport.vue') },
      { path: 'attachment', component: () => import('../views/DataAttachment.vue') },
      { path: 'rule', component: () => import('../views/AlertRule.vue') },
      { path: 'warning', component: () => import('../views/CarbonWarning.vue') },
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
