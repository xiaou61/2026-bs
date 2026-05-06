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
      { path: 'project', component: () => import('../views/IncubationProject.vue') },
      { path: 'mentor', component: () => import('../views/MentorProfile.vue') },
      { path: 'team', component: () => import('../views/TeamProfile.vue') },
      { path: 'application', component: () => import('../views/ProjectApplication.vue') },
      { path: 'plan', component: () => import('../views/IncubationPlan.vue') },
      { path: 'coaching', component: () => import('../views/MentorCoaching.vue') },
      { path: 'roadshow', component: () => import('../views/RoadshowEvent.vue') },
      { path: 'score', component: () => import('../views/RoadshowScore.vue') },
      { path: 'funding', component: () => import('../views/FundingRecord.vue') },
      { path: 'milestone', component: () => import('../views/MilestoneTask.vue') },
      { path: 'achievement', component: () => import('../views/AchievementShowcase.vue') },
      { path: 'notice', component: () => import('../views/IncubationNotice.vue') },
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




