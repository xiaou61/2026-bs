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
      { path: 'station', component: () => import('../views/ChargingStation.vue') },
      { path: 'pile', component: () => import('../views/ChargingPile.vue') },
      { path: 'vehicle', component: () => import('../views/UserVehicle.vue') },
      { path: 'appointment', component: () => import('../views/AppointmentOrder.vue') },
      { path: 'session', component: () => import('../views/ChargingSession.vue') },
      { path: 'fault', component: () => import('../views/FaultReport.vue') },
      { path: 'repair', component: () => import('../views/RepairWorkOrder.vue') },
      { path: 'plan', component: () => import('../views/MaintenancePlan.vue') },
      { path: 'price', component: () => import('../views/ElectricityPrice.vue') },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue') },
      { path: 'revenue', component: () => import('../views/RevenueStatistic.vue') },
      { path: 'energy', component: () => import('../views/EnergyMonitor.vue') },
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
