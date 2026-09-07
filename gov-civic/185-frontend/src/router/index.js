import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SANITATION: '/site',
  CLEANER: '/task',
  SUPPLY: '/dispatch',
  INSPECTOR: '/inspection',
  CITIZEN: '/complaint'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'CLEANER', 'SUPPLY', 'INSPECTOR', 'CITIZEN'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'site', component: () => import('../views/ToiletSite.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] } },
      { path: 'route', component: () => import('../views/CleaningRoute.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR'] } },
      { path: 'task', component: () => import('../views/CleaningTask.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'CLEANER'] } },
      { path: 'record', component: () => import('../views/CleaningRecord.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR'] } },
      { path: 'plan', component: () => import('../views/InspectionPlan.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] } },
      { path: 'inspection', component: () => import('../views/InspectionRecord.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] } },
      { path: 'stock', component: () => import('../views/ConsumableStock.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER'] } },
      { path: 'request', component: () => import('../views/SupplyRequest.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER'] } },
      { path: 'dispatch', component: () => import('../views/SupplyDispatch.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'SUPPLY'] } },
      { path: 'complaint', component: () => import('../views/ComplaintReport.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'INSPECTOR', 'CITIZEN'] } },
      { path: 'rectification', component: () => import('../views/IssueRectification.vue'), meta: { roles: ['ADMIN', 'SANITATION', 'CLEANER', 'SUPPLY', 'INSPECTOR'] } },
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
