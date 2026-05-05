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
      { path: 'lot', component: () => import('../views/ParkingLot.vue') },
      { path: 'area', component: () => import('../views/ParkingArea.vue') },
      { path: 'space', component: () => import('../views/ParkingSpace.vue') },
      { path: 'sensor', component: () => import('../views/SpaceSensor.vue') },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue') },
      { path: 'reservation', component: () => import('../views/ReservationOrder.vue') },
      { path: 'record', component: () => import('../views/ParkingRecord.vue') },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue') },
      { path: 'prediction', component: () => import('../views/VacancyPrediction.vue') },
      { path: 'screen', component: () => import('../views/GuidanceScreen.vue') },
      { path: 'route', component: () => import('../views/NavigationRoute.vue') },
      { path: 'fault', component: () => import('../views/FaultReport.vue') },
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
