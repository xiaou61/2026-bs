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
      { path: 'point', component: () => import('../views/WaterLevelPoint.vue') },
      { path: 'rainstation', component: () => import('../views/RainGaugeStation.vue') },
      { path: 'pump', component: () => import('../views/DrainagePump.vue') },
      { path: 'waterdata', component: () => import('../views/WaterLevelData.vue') },
      { path: 'raindata', component: () => import('../views/RainfallData.vue') },
      { path: 'rule', component: () => import('../views/WarningRule.vue') },
      { path: 'warning', component: () => import('../views/FloodWarning.vue') },
      { path: 'plan', component: () => import('../views/EmergencyPlan.vue') },
      { path: 'task', component: () => import('../views/DispatchTask.vue') },
      { path: 'team', component: () => import('../views/RescueTeam.vue') },
      { path: 'material', component: () => import('../views/MaterialReserve.vue') },
      { path: 'shelter', component: () => import('../views/ShelterSite.vue') },
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
