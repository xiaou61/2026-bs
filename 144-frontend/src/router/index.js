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
      { path: 'route', component: () => import('../views/AccessibleRoute.vue') },
      { path: 'facility', component: () => import('../views/FacilityPoint.vue') },
      { path: 'traveler', component: () => import('../views/TravelerProfile.vue') },
      { path: 'request', component: () => import('../views/AssistRequest.vue') },
      { path: 'volunteer', component: () => import('../views/VolunteerProfile.vue') },
      { path: 'plan', component: () => import('../views/RoutePlan.vue') },
      { path: 'task', component: () => import('../views/AssistTask.vue') },
      { path: 'checkin', component: () => import('../views/ServiceCheckin.vue') },
      { path: 'feedback', component: () => import('../views/FeedbackReview.vue') },
      { path: 'contact', component: () => import('../views/EmergencyContact.vue') },
      { path: 'trip', component: () => import('../views/TripRecord.vue') },
      { path: 'notice', component: () => import('../views/MessageNotice.vue') },
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








