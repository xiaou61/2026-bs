import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  LIBRARY: '/book',
  CURATOR: '/donation',
  STUDENT: '/checkin',
  CLUB: '/activity',
  TEACHER: '/note'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB', 'TEACHER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'shelf', component: () => import('../views/DriftShelf.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'CLUB'] } },
      { path: 'book', component: () => import('../views/BookProfile.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB'] } },
      { path: 'reader', component: () => import('../views/ReaderProfile.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'TEACHER', 'STUDENT'] } },
      { path: 'donation', component: () => import('../views/BookDonation.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB'] } },
      { path: 'borrow', component: () => import('../views/BorrowRecord.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT'] } },
      { path: 'return', component: () => import('../views/ReturnExchange.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT'] } },
      { path: 'checkin', component: () => import('../views/ReadingCheckin.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] } },
      { path: 'note', component: () => import('../views/ReadingNote.vue'), meta: { roles: ['ADMIN', 'STUDENT', 'CLUB', 'TEACHER'] } },
      { path: 'medal', component: () => import('../views/PointsMedal.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] } },
      { path: 'activity', component: () => import('../views/ReadingActivity.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] } },
      { path: 'notice', component: () => import('../views/MessageNotice.vue'), meta: { roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB', 'TEACHER'] } },
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
