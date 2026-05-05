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
      { path: 'building', component: () => import('../views/ParkBuilding.vue') },
      { path: 'device', component: () => import('../views/TwinDevice.vue') },
      { path: 'route', component: () => import('../views/InspectionRoute.vue') },
      { path: 'point', component: () => import('../views/InspectionPoint.vue') },
      { path: 'task', component: () => import('../views/InspectionTask.vue') },
      { path: 'record', component: () => import('../views/InspectionRecord.vue') },
      { path: 'defect', component: () => import('../views/DefectReport.vue') },
      { path: 'workOrder', component: () => import('../views/WorkOrder.vue') },
      { path: 'sensor', component: () => import('../views/SensorData.vue') },
      { path: 'model', component: () => import('../views/TwinModel.vue') },
      { path: 'energy', component: () => import('../views/EnergyMonitor.vue') },
      { path: 'schedule', component: () => import('../views/MaintenanceSchedule.vue') },
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
