import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '数据看板', roles: ['admin', 'teacher', 'student'] } },
      { path: 'department', component: () => import('../views/department/index.vue'), meta: { title: '院系管理', roles: ['admin'] } },
      { path: 'major', component: () => import('../views/major/index.vue'), meta: { title: '专业管理', roles: ['admin'] } },
      { path: 'classinfo', component: () => import('../views/classinfo/index.vue'), meta: { title: '班级管理', roles: ['admin'] } },
      { path: 'term', component: () => import('../views/term/index.vue'), meta: { title: '学期管理', roles: ['admin'] } },
      { path: 'course', component: () => import('../views/course/index.vue'), meta: { title: '课程管理', roles: ['admin', 'teacher', 'student'] } },
      { path: 'schedule', component: () => import('../views/schedule/index.vue'), meta: { title: '排课与课表', roles: ['admin', 'teacher', 'student'] } },
      { path: 'selection', component: () => import('../views/selection/index.vue'), meta: { title: '选课中心', roles: ['admin', 'teacher', 'student'] } },
      { path: 'resource', component: () => import('../views/resource/index.vue'), meta: { title: '课程资源', roles: ['admin', 'teacher', 'student'] } },
      { path: 'attendance', component: () => import('../views/attendance/index.vue'), meta: { title: '考勤管理', roles: ['admin', 'teacher', 'student'] } },
      { path: 'score', component: () => import('../views/score/index.vue'), meta: { title: '成绩管理', roles: ['admin', 'teacher', 'student'] } },
      { path: 'evaluation', component: () => import('../views/evaluation/index.vue'), meta: { title: '课程评教', roles: ['admin', 'teacher', 'student'] } },
      { path: 'notice', component: () => import('../views/notice/index.vue'), meta: { title: '公告中心', roles: ['admin', 'teacher', 'student'] } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
  if (to.path !== '/login' && !token) {
    next('/login')
    return
  }
  if (to.path === '/login' && token) {
    next('/dashboard')
    return
  }
  if (to.meta.roles && userInfo && !to.meta.roles.includes(userInfo.role)) {
    next('/dashboard')
    return
  }
  next()
})

export default router
