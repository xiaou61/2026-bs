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
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '文化看板', roles: ['admin', 'artist', 'member'] } },
      { path: 'category', component: () => import('../views/category/index.vue'), meta: { title: '剧种分类管理', roles: ['admin'] } },
      { path: 'artist', component: () => import('../views/artist/index.vue'), meta: { title: '名家档案管理', roles: ['admin'] } },
      { path: 'venue', component: () => import('../views/venue/index.vue'), meta: { title: '场馆管理', roles: ['admin'] } },
      { path: 'season', component: () => import('../views/season/index.vue'), meta: { title: '文化专题管理', roles: ['admin'] } },
      { path: 'repertoire', component: () => import('../views/repertoire/index.vue'), meta: { title: '剧目管理', roles: ['admin', 'artist', 'member'] } },
      { path: 'performance', component: () => import('../views/performance/index.vue'), meta: { title: '排期与行程', roles: ['admin', 'artist', 'member'] } },
      { path: 'booking', component: () => import('../views/booking/index.vue'), meta: { title: '预约中心', roles: ['admin', 'artist', 'member'] } },
      { path: 'resource', component: () => import('../views/resource/index.vue'), meta: { title: '数字资源', roles: ['admin', 'artist', 'member'] } },
      { path: 'checkin', component: () => import('../views/checkin/index.vue'), meta: { title: '签到管理', roles: ['admin', 'artist', 'member'] } },
      { path: 'review', component: () => import('../views/review/index.vue'), meta: { title: '研学评分管理', roles: ['admin', 'artist', 'member'] } },
      { path: 'comment', component: () => import('../views/comment/index.vue'), meta: { title: '剧目赏析互动', roles: ['admin', 'artist', 'member'] } },
      { path: 'notice', component: () => import('../views/notice/index.vue'), meta: { title: '公告中心', roles: ['admin', 'artist', 'member'] } }
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


