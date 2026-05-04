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
      { path: 'farm', component: () => import('../views/FarmBase.vue') },
      { path: 'farmer', component: () => import('../views/FarmerProfile.vue') },
      { path: 'category', component: () => import('../views/ProductCategory.vue') },
      { path: 'batch', component: () => import('../views/ProductBatch.vue') },
      { path: 'planting', component: () => import('../views/PlantingRecord.vue') },
      { path: 'material', component: () => import('../views/InputMaterial.vue') },
      { path: 'inspection', component: () => import('../views/QualityInspection.vue') },
      { path: 'block', component: () => import('../views/ChainBlock.vue') },
      { path: 'node', component: () => import('../views/TraceNode.vue') },
      { path: 'logistics', component: () => import('../views/LogisticsRecord.vue') },
      { path: 'recall', component: () => import('../views/RecallEvent.vue') },
      { path: 'regulation', component: () => import('../views/RegulationCheck.vue') },
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
