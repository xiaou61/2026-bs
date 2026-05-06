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
      { path: 'project', component: () => import('../views/ServiceProject.vue') },
      { path: 'category', component: () => import('../views/ServiceCategory.vue') },
      { path: 'resident', component: () => import('../views/ResidentProfile.vue') },
      { path: 'volunteer', component: () => import('../views/VolunteerProfile.vue') },
      { path: 'booking', component: () => import('../views/ServiceBooking.vue') },
      { path: 'checkin', component: () => import('../views/ServiceCheckin.vue') },
      { path: 'account', component: () => import('../views/TimeAccount.vue') },
      { path: 'exchange', component: () => import('../views/TimeExchange.vue') },
      { path: 'feedback', component: () => import('../views/FeedbackReview.vue') },
      { path: 'activity', component: () => import('../views/CommunityActivity.vue') },
      { path: 'rule', component: () => import('../views/PointRule.vue') },
      { path: 'notice', component: () => import('../views/SiteNotice.vue') },
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








