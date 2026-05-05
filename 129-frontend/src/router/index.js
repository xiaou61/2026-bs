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
      { path: 'pond', component: () => import('../views/PondProfile.vue') },
      { path: 'sensor', component: () => import('../views/SensorDevice.vue') },
      { path: 'reading', component: () => import('../views/WaterQualityReading.vue') },
      { path: 'plan', component: () => import('../views/FeedingPlan.vue') },
      { path: 'feeding', component: () => import('../views/FeedingRecord.vue') },
      { path: 'batch', component: () => import('../views/FishBatch.vue') },
      { path: 'sampling', component: () => import('../views/GrowthSampling.vue') },
      { path: 'disease', component: () => import('../views/DiseaseWarning.vue') },
      { path: 'medication', component: () => import('../views/MedicationRecord.vue') },
      { path: 'equipment', component: () => import('../views/EquipmentDevice.vue') },
      { path: 'rule', component: () => import('../views/WaterAlertRule.vue') },
      { path: 'statistic', component: () => import('../views/ProductionStatistic.vue') },
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
