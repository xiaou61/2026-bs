import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CENTER: '/site',
  DIETITIAN: '/menu',
  DISPATCH: '/route',
  COURIER: '/delivery',
  ELDER: '/order'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'DISPATCH', 'COURIER', 'ELDER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'site', component: () => import('../views/MealSite.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DISPATCH'] } },
      { path: 'elder', component: () => import('../views/ElderProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ELDER', 'DIETITIAN'] } },
      { path: 'menu', component: () => import('../views/NutritionMenu.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] } },
      { path: 'order', component: () => import('../views/MealOrder.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ELDER', 'DISPATCH'] } },
      { path: 'route', component: () => import('../views/RoutePlan.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DISPATCH', 'COURIER'] } },
      { path: 'delivery', component: () => import('../views/DeliveryTask.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DISPATCH', 'COURIER', 'ELDER'] } },
      { path: 'receipt', component: () => import('../views/SignReceipt.vue'), meta: { roles: ['ADMIN', 'CENTER', 'COURIER', 'ELDER'] } },
      { path: 'restriction', component: () => import('../views/DietaryRestriction.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] } },
      { path: 'analysis', component: () => import('../views/NutritionAnalysis.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] } },
      { path: 'subsidy', component: () => import('../views/SubsidyRecord.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ELDER'] } },
      { path: 'followup', component: () => import('../views/VisitFollowup.vue'), meta: { roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'DISPATCH', 'ELDER'] } },
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
