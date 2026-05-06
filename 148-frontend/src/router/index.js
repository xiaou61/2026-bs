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
      { path: 'package', component: () => import('../views/ServicePackage.vue') },
      { path: 'elder', component: () => import('../views/ElderProfile.vue') },
      { path: 'caregiver', component: () => import('../views/CaregiverProfile.vue') },
      { path: 'order', component: () => import('../views/ServiceOrder.vue') },
      { path: 'team', component: () => import('../views/CareTeam.vue') },
      { path: 'checkin', component: () => import('../views/VisitCheckin.vue') },
      { path: 'record', component: () => import('../views/ServiceRecord.vue') },
      { path: 'assessment', component: () => import('../views/HealthAssessment.vue') },
      { path: 'reminder', component: () => import('../views/MedicationReminder.vue') },
      { path: 'family', component: () => import('../views/FamilyVisit.vue') },
      { path: 'alert', component: () => import('../views/AlertEvent.vue') },
      { path: 'notice', component: () => import('../views/CareNotice.vue') },
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










