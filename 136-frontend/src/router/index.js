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
      { path: 'topic', component: () => import('../views/TopicRelease.vue') },
      { path: 'teacher', component: () => import('../views/TeacherProfile.vue') },
      { path: 'student', component: () => import('../views/StudentProfile.vue') },
      { path: 'application', component: () => import('../views/TopicApplication.vue') },
      { path: 'review', component: () => import('../views/SelectionReview.vue') },
      { path: 'selection', component: () => import('../views/MutualSelection.vue') },
      { path: 'taskbook', component: () => import('../views/TaskBook.vue') },
      { path: 'proposal', component: () => import('../views/ProposalRecord.vue') },
      { path: 'defense', component: () => import('../views/OpeningDefense.vue') },
      { path: 'midterm', component: () => import('../views/MidtermCheck.vue') },
      { path: 'guidance', component: () => import('../views/GuidanceMeeting.vue') },
      { path: 'notice', component: () => import('../views/MilestoneNotice.vue') },
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


