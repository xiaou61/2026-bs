import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/layout/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/UserList.vue'),
        meta: { title: '用户管理', icon: 'User', roles: ['admin'] }
      },
      {
        path: 'crop',
        name: 'Crop',
        component: () => import('@/views/crop/CropList.vue'),
        meta: { title: '作物管理', icon: 'Grape' }
      },
      {
        path: 'crop/category',
        name: 'CropCategory',
        component: () => import('@/views/crop/CategoryList.vue'),
        meta: { title: '作物分类', icon: 'Files' }
      },
      {
        path: 'knowledge',
        name: 'Knowledge',
        component: () => import('@/views/knowledge/KnowledgeList.vue'),
        meta: { title: '知识库', icon: 'Collection' }
      },
      {
        path: 'knowledge/:id',
        name: 'KnowledgeDetail',
        component: () => import('@/views/knowledge/KnowledgeDetail.vue'),
        meta: { title: '知识详情' }
      },
      {
        path: 'plan',
        name: 'Plan',
        component: () => import('@/views/plan/PlanList.vue'),
        meta: { title: '生产计划', icon: 'Calendar' }
      },
      {
        path: 'task',
        name: 'Task',
        component: () => import('@/views/task/TaskList.vue'),
        meta: { title: '生产任务', icon: 'List' }
      },
      {
        path: 'pest',
        name: 'Pest',
        component: () => import('@/views/pest/PestList.vue'),
        meta: { title: '病虫害库', icon: 'Warning' }
      },
      {
        path: 'pest/warning',
        name: 'PestWarning',
        component: () => import('@/views/pest/WarningList.vue'),
        meta: { title: '预警信息', icon: 'Bell' }
      },
      {
        path: 'pest/treatment',
        name: 'Treatment',
        component: () => import('@/views/pest/TreatmentList.vue'),
        meta: { title: '防治记录', icon: 'DocumentChecked' }
      },
      {
        path: 'material',
        name: 'Material',
        component: () => import('@/views/material/MaterialList.vue'),
        meta: { title: '农资管理', icon: 'Box' }
      },
      {
        path: 'consultation',
        name: 'Consultation',
        component: () => import('@/views/consultation/ConsultationList.vue'),
        meta: { title: '技术咨询', icon: 'ChatDotRound' }
      },
      {
        path: 'consultation/:id',
        name: 'ConsultationDetail',
        component: () => import('@/views/consultation/ConsultationDetail.vue'),
        meta: { title: '咨询详情' }
      },
      {
        path: 'appointment',
        name: 'Appointment',
        component: () => import('@/views/appointment/AppointmentList.vue'),
        meta: { title: '专家预约', icon: 'Timer' }
      },
      {
        path: 'notice',
        name: 'Notice',
        component: () => import('@/views/notice/NoticeList.vue'),
        meta: { title: '公告管理', icon: 'Notification', roles: ['admin'] }
      }
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
