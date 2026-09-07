import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ORGANIZER: '/event',
  COACH: '/team',
  ATHLETE: '/registration',
  REFEREE: '/score',
  COMMITTEE: '/appeal'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'REFEREE', 'COMMITTEE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'event', component: () => import('../views/SportEvent.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COMMITTEE'] } },
      { path: 'team', component: () => import('../views/TeamProfile.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH'] } },
      { path: 'athlete', component: () => import('../views/AthleteProfile.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE'] } },
      { path: 'registration', component: () => import('../views/EventRegistration.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE'] } },
      { path: 'group', component: () => import('../views/GroupDraw.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE'] } },
      { path: 'schedule', component: () => import('../views/ScheduleMatch.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE', 'ATHLETE'] } },
      { path: 'venue', component: () => import('../views/VenueResource.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'REFEREE'] } },
      { path: 'referee', component: () => import('../views/RefereeAssignment.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE'] } },
      { path: 'score', component: () => import('../views/ScoreRecord.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE'] } },
      { path: 'result', component: () => import('../views/ResultPublish.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'REFEREE', 'COMMITTEE'] } },
      { path: 'appeal', component: () => import('../views/AppealReview.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'COMMITTEE'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'ORGANIZER'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
