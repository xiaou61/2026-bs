import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MENTOR: '/project',
  STUDENT: '/application',
  JUDGE: '/score'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'project', component: () => import('../views/IncubationProject.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'mentor', component: () => import('../views/MentorProfile.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'team', component: () => import('../views/TeamProfile.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'application', component: () => import('../views/ProjectApplication.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'plan', component: () => import('../views/IncubationPlan.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'coaching', component: () => import('../views/MentorCoaching.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'roadshow', component: () => import('../views/RoadshowEvent.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'score', component: () => import('../views/RoadshowScore.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'funding', component: () => import('../views/FundingRecord.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'milestone', component: () => import('../views/MilestoneTask.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'achievement', component: () => import('../views/AchievementShowcase.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
      { path: 'notice', component: () => import('../views/IncubationNotice.vue'), meta: { roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] } },
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
  if (to.path === '/' && userStore.token) {
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




