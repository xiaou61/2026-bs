import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'department', component: () => import('../views/Department.vue') },
      { path: 'position', component: () => import('../views/Position.vue') },
      { path: 'employee', component: () => import('../views/Employee.vue') },
      { path: 'attendance', component: () => import('../views/Attendance.vue') },
      { path: 'leave', component: () => import('../views/Leave.vue') },
      { path: 'salary', component: () => import('../views/Salary.vue') },
      { path: 'recruitment', component: () => import('../views/Recruitment.vue') },
      { path: 'resume', component: () => import('../views/Resume.vue') },
      { path: 'training', component: () => import('../views/Training.vue') },
      { path: 'contract', component: () => import('../views/Contract.vue') },
      { path: 'announcement', component: () => import('../views/Announcement.vue') },
      { path: 'user', component: () => import('../views/User.vue') },
      { path: 'profile', component: () => import('../views/Profile.vue') }
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
