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
      { path: 'greenhouse', component: () => import('../views/GreenhouseProfile.vue') },
      { path: 'crop', component: () => import('../views/CropBatch.vue') },
      { path: 'sensor', component: () => import('../views/EnvironmentSensor.vue') },
      { path: 'reading', component: () => import('../views/EnvironmentReading.vue') },
      { path: 'irrigation', component: () => import('../views/IrrigationTask.vue') },
      { path: 'fertilizer', component: () => import('../views/FertilizerPlan.vue') },
      { path: 'pest', component: () => import('../views/PestWarning.vue') },
      { path: 'diagnosis', component: () => import('../views/DiseaseDiagnosis.vue') },
      { path: 'device', component: () => import('../views/ControlDevice.vue') },
      { path: 'command', component: () => import('../views/RemoteCommand.vue') },
      { path: 'harvest', component: () => import('../views/HarvestRecord.vue') },
      { path: 'ticket', component: () => import('../views/MaintenanceTicket.vue') },
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
