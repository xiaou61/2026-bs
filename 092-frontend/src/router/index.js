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
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '园务看板', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'campus', component: () => import('../views/campus/index.vue'), meta: { title: '园区管理', roles: ['admin'] } },
      { path: 'grade', component: () => import('../views/grade/index.vue'), meta: { title: '年级管理', roles: ['admin'] } },
      { path: 'classinfo', component: () => import('../views/classinfo/index.vue'), meta: { title: '班级管理', roles: ['admin', 'teacher'] } },
      { path: 'term', component: () => import('../views/term/index.vue'), meta: { title: '学期管理', roles: ['admin'] } },
      { path: 'activity', component: () => import('../views/activity/index.vue'), meta: { title: '活动课程', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'schedule', component: () => import('../views/schedule/index.vue'), meta: { title: '活动安排', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'child', component: () => import('../views/child/index.vue'), meta: { title: '幼儿档案', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'recipe', component: () => import('../views/recipe/index.vue'), meta: { title: '每周食谱', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'attendance', component: () => import('../views/attendance/index.vue'), meta: { title: '入园考勤', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'health', component: () => import('../views/health/index.vue'), meta: { title: '健康晨检', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'feedback', component: () => import('../views/feedback/index.vue'), meta: { title: '家园反馈', roles: ['admin', 'teacher', 'parent'] } },
      { path: 'notice', component: () => import('../views/notice/index.vue'), meta: { title: '公告中心', roles: ['admin', 'teacher', 'parent'] } }
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
