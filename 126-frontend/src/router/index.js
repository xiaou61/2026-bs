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
      { path: 'home', component: () => import('../views/HomeProfile.vue') },
      { path: 'member', component: () => import('../views/FamilyMember.vue') },
      { path: 'meter', component: () => import('../views/SmartMeter.vue') },
      { path: 'device', component: () => import('../views/ElectricDevice.vue') },
      { path: 'reading', component: () => import('../views/EnergyReading.vue') },
      { path: 'bill', component: () => import('../views/ElectricityBill.vue') },
      { path: 'usage', component: () => import('../views/DeviceUsage.vue') },
      { path: 'budget', component: () => import('../views/EnergyBudget.vue') },
      { path: 'suggestion', component: () => import('../views/SavingSuggestion.vue') },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue') },
      { path: 'carbon', component: () => import('../views/CarbonStatistic.vue') },
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
