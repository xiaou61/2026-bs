import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  REGULATOR: '/regulation',
  FARMER: '/farm',
  INSPECTOR: '/inspection'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'farm', component: () => import('../views/FarmBase.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'farmer', component: () => import('../views/FarmerProfile.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'category', component: () => import('../views/ProductCategory.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'batch', component: () => import('../views/ProductBatch.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'planting', component: () => import('../views/PlantingRecord.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'material', component: () => import('../views/InputMaterial.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'inspection', component: () => import('../views/QualityInspection.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'INSPECTOR'] } },
      { path: 'block', component: () => import('../views/ChainBlock.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'INSPECTOR'] } },
      { path: 'node', component: () => import('../views/TraceNode.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'logistics', component: () => import('../views/LogisticsRecord.vue'), meta: { roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] } },
      { path: 'recall', component: () => import('../views/RecallEvent.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } },
      { path: 'regulation', component: () => import('../views/RegulationCheck.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } }
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
