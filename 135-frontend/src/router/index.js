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
      { path: 'conference', component: () => import('../views/ConferenceInfo.vue') },
      { path: 'call', component: () => import('../views/CallForPaper.vue') },
      { path: 'author', component: () => import('../views/AuthorProfile.vue') },
      { path: 'paper', component: () => import('../views/PaperSubmission.vue') },
      { path: 'reviewer', component: () => import('../views/ReviewerProfile.vue') },
      { path: 'assignment', component: () => import('../views/ReviewAssignment.vue') },
      { path: 'review', component: () => import('../views/BlindReview.vue') },
      { path: 'notice', component: () => import('../views/AcceptanceNotice.vue') },
      { path: 'guest', component: () => import('../views/GuestRegistration.vue') },
      { path: 'venue', component: () => import('../views/VenueRoom.vue') },
      { path: 'agenda', component: () => import('../views/AgendaSchedule.vue') },
      { path: 'checkin', component: () => import('../views/CheckinRecord.vue') },
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


