import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: () => import('@/views/Login.vue') },
    { path: '/register', component: () => import('@/views/Register.vue') },
    {
      path: '/admin',
      component: () => import('@/layouts/AdminLayout.vue'),
      meta: { role: 2 },
      children: [
        { path: '', redirect: '/admin/dashboard' },
        { path: 'dashboard', component: () => import('@/views/admin/Dashboard.vue') },
        { path: 'order', component: () => import('@/views/admin/OrderManage.vue') },
        { path: 'user', component: () => import('@/views/admin/UserManage.vue') },
        { path: 'community', component: () => import('@/views/admin/CommunityManage.vue') },
        { path: 'category', component: () => import('@/views/admin/CategoryManage.vue') },
        { path: 'product', component: () => import('@/views/admin/ProductManage.vue') },
        { path: 'exchange', component: () => import('@/views/admin/ExchangeManage.vue') },
        { path: 'knowledge', component: () => import('@/views/admin/KnowledgeManage.vue') },
        { path: 'notice', component: () => import('@/views/admin/NoticeManage.vue') }
      ]
    },
    {
      path: '/collector',
      component: () => import('@/layouts/CollectorLayout.vue'),
      meta: { role: 1 },
      children: [
        { path: '', redirect: '/collector/order' },
        { path: 'order', component: () => import('@/views/collector/OrderList.vue') },
        { path: 'complete/:id', component: () => import('@/views/collector/CompleteOrder.vue') }
      ]
    },
    {
      path: '/user',
      component: () => import('@/layouts/UserLayout.vue'),
      meta: { role: 0 },
      children: [
        { path: '', redirect: '/user/order' },
        { path: 'order', component: () => import('@/views/user/OrderList.vue') },
        { path: 'create', component: () => import('@/views/user/CreateOrder.vue') },
        { path: 'points', component: () => import('@/views/user/Points.vue') },
        { path: 'exchange', component: () => import('@/views/user/Exchange.vue') },
        { path: 'knowledge', component: () => import('@/views/user/Knowledge.vue') },
        { path: 'notice', component: () => import('@/views/user/Notice.vue') }
      ]
    },
    { path: '/', redirect: '/login' }
  ]
})

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
  
  if (to.path === '/login' || to.path === '/register') {
    next()
  } else if (!token) {
    next('/login')
  } else if (to.meta.role !== undefined && to.meta.role !== userInfo.role) {
    // 角色不匹配，跳转到对应首页
    if (userInfo.role === 2) next('/admin')
    else if (userInfo.role === 1) next('/collector')
    else next('/user')
  } else {
    next()
  }
})

export default router
