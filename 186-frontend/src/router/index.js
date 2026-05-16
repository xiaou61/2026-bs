import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CANTEEN: '/canteen',
  CHEF: '/sample',
  INSPECTOR: '/inspection',
  WAREHOUSE: '/ingredient',
  SCHOOL: '/risk'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'WAREHOUSE', 'SCHOOL'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'canteen', component: () => import('../views/CanteenInfo.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'SCHOOL'] } },
      { path: 'area', component: () => import('../views/KitchenArea.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] } },
      { path: 'dish', component: () => import('../views/DishMenu.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'SCHOOL'] } },
      { path: 'sample', component: () => import('../views/SampleRetention.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL'] } },
      { path: 'storage', component: () => import('../views/SampleStorage.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] } },
      { path: 'ingredient', component: () => import('../views/IngredientAcceptance.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'WAREHOUSE', 'CHEF', 'SCHOOL'] } },
      { path: 'disinfection', component: () => import('../views/DisinfectionRecord.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] } },
      { path: 'inspection', component: () => import('../views/HygieneInspection.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'INSPECTOR', 'SCHOOL'] } },
      { path: 'rectification', component: () => import('../views/IssueRectification.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL'] } },
      { path: 'risk', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'INSPECTOR', 'SCHOOL'] } },
      { path: 'waste', component: () => import('../views/WasteDisposal.vue'), meta: { roles: ['ADMIN', 'CANTEEN', 'CHEF', 'WAREHOUSE', 'INSPECTOR'] } },
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
