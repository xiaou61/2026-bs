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
      { path: 'client', component: () => import('../views/Client.vue') },
      { path: 'lawyer', component: () => import('../views/Lawyer.vue') },
      { path: 'case', component: () => import('../views/Case.vue') },
      { path: 'stage', component: () => import('../views/Stage.vue') },
      { path: 'consultation', component: () => import('../views/Consultation.vue') },
      { path: 'template', component: () => import('../views/Template.vue') },
      { path: 'document', component: () => import('../views/Document.vue') },
      { path: 'version', component: () => import('../views/Version.vue') },
      { path: 'appointment', component: () => import('../views/Appointment.vue') },
      { path: 'evidence', component: () => import('../views/Evidence.vue') },
      { path: 'fee', component: () => import('../views/Fee.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
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
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router
