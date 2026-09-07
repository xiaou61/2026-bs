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
      { path: 'user', component: () => import('../views/User.vue') },
      { path: 'space', component: () => import('../views/Space.vue') },
      { path: 'category', component: () => import('../views/Category.vue') },
      { path: 'document', component: () => import('../views/Document.vue') },
      { path: 'chunk', component: () => import('../views/Chunk.vue') },
      { path: 'group', component: () => import('../views/PermissionGroup.vue') },
      { path: 'member', component: () => import('../views/GroupMember.vue') },
      { path: 'permission', component: () => import('../views/DocumentPermission.vue') },
      { path: 'session', component: () => import('../views/QaSession.vue') },
      { path: 'record', component: () => import('../views/QaRecord.vue') },
      { path: 'feedback', component: () => import('../views/QaFeedback.vue') },
      { path: 'log', component: () => import('../views/AccessLog.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) {
    next('/login')
  } else if (to.path === '/login' && userStore.token) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
