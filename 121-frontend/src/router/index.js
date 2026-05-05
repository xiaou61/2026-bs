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
      { path: 'drone', component: () => import('../views/DroneDevice.vue') },
      { path: 'pilot', component: () => import('../views/PilotProfile.vue') },
      { path: 'zone', component: () => import('../views/InspectionZone.vue') },
      { path: 'route', component: () => import('../views/FlightRoute.vue') },
      { path: 'task', component: () => import('../views/InspectionTask.vue') },
      { path: 'flight', component: () => import('../views/FlightRecord.vue') },
      { path: 'defect', component: () => import('../views/DefectReport.vue') },
      { path: 'image', component: () => import('../views/DefectImage.vue') },
      { path: 'rectify', component: () => import('../views/RectificationOrder.vue') },
      { path: 'station', component: () => import('../views/BatteryStation.vue') },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue') },
      { path: 'warning', component: () => import('../views/RiskWarning.vue') },
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
