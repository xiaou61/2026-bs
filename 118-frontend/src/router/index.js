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
      { path: 'zone', component: () => import('../views/WarehouseZone.vue') },
      { path: 'location', component: () => import('../views/StorageLocation.vue') },
      { path: 'agv', component: () => import('../views/AgvVehicle.vue') },
      { path: 'station', component: () => import('../views/ChargingStation.vue') },
      { path: 'inventory', component: () => import('../views/InventoryItem.vue') },
      { path: 'inbound', component: () => import('../views/InboundOrder.vue') },
      { path: 'outbound', component: () => import('../views/OutboundOrder.vue') },
      { path: 'task', component: () => import('../views/AgvTask.vue') },
      { path: 'route', component: () => import('../views/TaskRoute.vue') },
      { path: 'recommendation', component: () => import('../views/LocationRecommendation.vue') },
      { path: 'maintenance', component: () => import('../views/DeviceMaintenance.vue') },
      { path: 'alert', component: () => import('../views/ExceptionAlert.vue') },
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
