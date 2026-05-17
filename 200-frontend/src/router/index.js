import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  WORKSHOP: '/workshop',
  TEACHER: '/course',
  CURATOR: '/showcase',
  SALES: '/settlement',
  VISITOR: '/booking'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'CURATOR', 'SALES', 'VISITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'workshop', component: () => import('../views/WorkshopProfile.vue'), meta: { roles: ['ADMIN', 'WORKSHOP'] } },
      { path: 'inheritor', component: () => import('../views/InheritorProfile.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] } },
      { path: 'course', component: () => import('../views/CourseCatalog.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] } },
      { path: 'schedule', component: () => import('../views/CourseSchedule.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] } },
      { path: 'booking', component: () => import('../views/CourseBooking.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] } },
      { path: 'review', component: () => import('../views/BookingReview.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] } },
      { path: 'checkin', component: () => import('../views/ClassCheckin.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] } },
      { path: 'artwork', component: () => import('../views/ArtworkCatalog.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES'] } },
      { path: 'showcase', component: () => import('../views/ExhibitionShowcase.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES', 'VISITOR'] } },
      { path: 'order', component: () => import('../views/ProductOrder.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'SALES', 'VISITOR'] } },
      { path: 'settlement', component: () => import('../views/SalesSettlement.vue'), meta: { roles: ['ADMIN', 'WORKSHOP', 'SALES'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
