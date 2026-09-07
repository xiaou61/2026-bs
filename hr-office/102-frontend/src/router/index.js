import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const getHomePath = (role) => {
  if (role === 'ADMIN') return '/dashboard'
  if (role === 'LAWYER') return '/case'
  if (role === 'ASSISTANT') return '/client'
  if (role === 'CLIENT') return '/case'
  return '/login'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'user', component: () => import('../views/User.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'client', component: () => import('../views/Client.vue'), meta: { roles: ['ASSISTANT'] } },
      { path: 'lawyer', component: () => import('../views/Lawyer.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'case', component: () => import('../views/Case.vue'), meta: { roles: ['LAWYER', 'ASSISTANT', 'CLIENT'] } },
      { path: 'stage', component: () => import('../views/Stage.vue'), meta: { roles: ['LAWYER', 'ASSISTANT'] } },
      { path: 'consultation', component: () => import('../views/Consultation.vue'), meta: { roles: ['LAWYER'] } },
      { path: 'template', component: () => import('../views/Template.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'document', component: () => import('../views/Document.vue'), meta: { roles: ['LAWYER'] } },
      { path: 'version', component: () => import('../views/Version.vue'), meta: { roles: ['LAWYER'] } },
      { path: 'appointment', component: () => import('../views/Appointment.vue'), meta: { roles: ['LAWYER', 'ASSISTANT', 'CLIENT'] } },
      { path: 'evidence', component: () => import('../views/Evidence.vue'), meta: { roles: ['ASSISTANT', 'CLIENT'] } },
      { path: 'fee', component: () => import('../views/Fee.vue'), meta: { roles: ['ASSISTANT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next(getHomePath(userStore.user?.role))
  else if (to.meta?.roles?.length && !to.meta.roles.includes(userStore.user?.role)) next(getHomePath(userStore.user?.role))
  else next()
})

export default router
