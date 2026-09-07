import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'admin', name: 'Admin', component: () => import('../views/admin/index.vue') },
      { path: 'department', name: 'Department', component: () => import('../views/department/index.vue') },
      { path: 'major', name: 'Major', component: () => import('../views/major/index.vue') },
      { path: 'plan', name: 'Plan', component: () => import('../views/plan/index.vue') },
      { path: 'student', name: 'Student', component: () => import('../views/student/index.vue') },
      { path: 'application', name: 'Application', component: () => import('../views/application/index.vue') },
      { path: 'score', name: 'Score', component: () => import('../views/score/index.vue') },
      { path: 'admission', name: 'Admission', component: () => import('../views/admission/index.vue') },
      { path: 'scoreline', name: 'ScoreLine', component: () => import('../views/scoreline/index.vue') },
      { path: 'notice', name: 'Notice', component: () => import('../views/notice/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
