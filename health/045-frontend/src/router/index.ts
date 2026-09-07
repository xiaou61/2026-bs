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
        { path: 'elder', component: () => import('@/views/admin/ElderManage.vue') },
        { path: 'bed', component: () => import('@/views/admin/BedManage.vue') },
        { path: 'user', component: () => import('@/views/admin/UserManage.vue') },
        { path: 'visit', component: () => import('@/views/admin/VisitManage.vue') },
        { path: 'bill', component: () => import('@/views/admin/BillManage.vue') },
        { path: 'notice', component: () => import('@/views/admin/NoticeManage.vue') }
      ]
    },
    {
      path: '/nurse',
      component: () => import('@/layouts/NurseLayout.vue'),
      meta: { role: 1 },
      children: [
        { path: '', redirect: '/nurse/elder' },
        { path: 'elder', component: () => import('@/views/nurse/ElderList.vue') },
        { path: 'elder/:id', component: () => import('@/views/nurse/ElderDetail.vue') },
        { path: 'care', component: () => import('@/views/nurse/CareRecord.vue') }
      ]
    },
    {
      path: '/family',
      component: () => import('@/layouts/FamilyLayout.vue'),
      meta: { role: 0 },
      children: [
        { path: '', redirect: '/family/elder' },
        { path: 'elder', component: () => import('@/views/family/ElderInfo.vue') },
        { path: 'bill', component: () => import('@/views/family/BillList.vue') },
        { path: 'visit', component: () => import('@/views/family/VisitApply.vue') },
        { path: 'notice', component: () => import('@/views/family/NoticeList.vue') }
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
    else if (userInfo.role === 1) next('/nurse')
    else next('/family')
  } else {
    next()
  }
})

export default router
