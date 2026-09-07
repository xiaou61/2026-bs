import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CAREER: '/destination',
  COLLEGE: '/graduate',
  COUNSELOR: '/assistance',
  STUDENT: '/destination',
  EMPLOYER: '/job'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'major', component: () => import('../views/CollegeMajor.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR'] } },
      { path: 'graduate', component: () => import('../views/GraduateProfile.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] } },
      { path: 'employer', component: () => import('../views/EmployerProfile.vue'), meta: { roles: ['ADMIN', 'CAREER', 'EMPLOYER'] } },
      { path: 'job', component: () => import('../views/JobPosition.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] } },
      { path: 'destination', component: () => import('../views/DestinationReport.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] } },
      { path: 'contract', component: () => import('../views/ContractArchive.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] } },
      { path: 'review', component: () => import('../views/MaterialReview.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] } },
      { path: 'assistance', component: () => import('../views/AssistanceRecord.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] } },
      { path: 'recommendation', component: () => import('../views/JobRecommendation.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] } },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] } },
      { path: 'statistics', component: () => import('../views/EmploymentStatistics.vue'), meta: { roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR'] } },
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
