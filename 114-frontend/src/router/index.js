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
      { path: 'warehouse', component: () => import('../views/WarehouseNode.vue') },
      { path: 'carrier', component: () => import('../views/CarrierCompany.vue') },
      { path: 'device', component: () => import('../views/ColdDevice.vue') },
      { path: 'cargo', component: () => import('../views/ProductCargo.vue') },
      { path: 'order', component: () => import('../views/TransportOrder.vue') },
      { path: 'temperature', component: () => import('../views/TemperatureRecord.vue') },
      { path: 'track', component: () => import('../views/GpsTrack.vue') },
      { path: 'rule', component: () => import('../views/AlertRule.vue') },
      { path: 'alert', component: () => import('../views/ExceptionAlert.vue') },
      { path: 'task', component: () => import('../views/DisposalTask.vue') },
      { path: 'responsibility', component: () => import('../views/ResponsibilityTrace.vue') },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue') },
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
