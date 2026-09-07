import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '数据看板', roles: ['admin', 'manager', 'operator', 'inspector'] } },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { title: '用户管理', roles: ['admin'] } },
      { path: 'equipment/category', component: () => import('../views/equipment/Category.vue'), meta: { title: '设备分类', roles: ['admin', 'operator'] } },
      { path: 'equipment', component: () => import('../views/equipment/index.vue'), meta: { title: '设备管理', roles: ['admin', 'operator'] } },
      { path: 'iot/sensor', component: () => import('../views/iot/SensorData.vue'), meta: { title: '传感器数据', roles: ['admin', 'operator'] } },
      { path: 'iot/alert', component: () => import('../views/iot/AlertRecord.vue'), meta: { title: '告警记录', roles: ['admin', 'operator'] } },
      { path: 'production/product', component: () => import('../views/production/Product.vue'), meta: { title: '产品管理', roles: ['admin', 'manager'] } },
      { path: 'production/order', component: () => import('../views/production/Order.vue'), meta: { title: '生产工单', roles: ['admin', 'manager', 'inspector'] } },
      { path: 'material', component: () => import('../views/material/index.vue'), meta: { title: '物料管理', roles: ['admin', 'manager'] } },
      { path: 'material/stock', component: () => import('../views/material/StockRecord.vue'), meta: { title: '出入库记录', roles: ['admin', 'manager'] } },
      { path: 'quality', component: () => import('../views/quality/index.vue'), meta: { title: '质量检测', roles: ['admin', 'manager', 'inspector'] } },
      { path: 'maintenance/plan', component: () => import('../views/maintenance/Plan.vue'), meta: { title: '维保计划', roles: ['admin', 'operator'] } },
      { path: 'maintenance/record', component: () => import('../views/maintenance/Record.vue'), meta: { title: '维保记录', roles: ['admin', 'operator'] } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
