import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TEACHER: '/topic',
  STUDENT: '/application',
  AFFAIRS: '/review'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'topic', component: () => import('../views/TopicRelease.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'teacher', component: () => import('../views/TeacherProfile.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'STUDENT', 'AFFAIRS'] } },
      { path: 'application', component: () => import('../views/TopicApplication.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] } },
      { path: 'review', component: () => import('../views/SelectionReview.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'selection', component: () => import('../views/MutualSelection.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'taskbook', component: () => import('../views/TaskBook.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'proposal', component: () => import('../views/ProposalRecord.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] } },
      { path: 'defense', component: () => import('../views/OpeningDefense.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'midterm', component: () => import('../views/MidtermCheck.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'guidance', component: () => import('../views/GuidanceMeeting.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] } },
      { path: 'notice', component: () => import('../views/MilestoneNotice.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) {
    next('/login')
    return
  }
  if (to.path === '/login' && userStore.token) {
    next(home)
    return
  }
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router


