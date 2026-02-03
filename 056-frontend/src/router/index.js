import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'user',
        name: 'UserList',
        component: () => import('../views/user/UserList.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'category',
        name: 'CategoryList',
        component: () => import('../views/category/CategoryList.vue'),
        meta: { title: '分类管理' }
      },
      {
        path: 'competition',
        name: 'CompetitionList',
        component: () => import('../views/competition/CompetitionList.vue'),
        meta: { title: '竞赛管理' }
      },
      {
        path: 'competition/add',
        name: 'CompetitionAdd',
        component: () => import('../views/competition/CompetitionForm.vue'),
        meta: { title: '新增竞赛' }
      },
      {
        path: 'competition/edit/:id',
        name: 'CompetitionEdit',
        component: () => import('../views/competition/CompetitionForm.vue'),
        meta: { title: '编辑竞赛' }
      },
      {
        path: 'work',
        name: 'WorkList',
        component: () => import('../views/work/WorkList.vue'),
        meta: { title: '作品管理' }
      },
      {
        path: 'work/:id',
        name: 'WorkDetail',
        component: () => import('../views/work/WorkDetail.vue'),
        meta: { title: '作品详情' }
      },
      {
        path: 'score',
        name: 'ScoreList',
        component: () => import('../views/score/ScoreList.vue'),
        meta: { title: '评分管理' }
      },
      {
        path: 'score/:id',
        name: 'ScoreForm',
        component: () => import('../views/score/ScoreForm.vue'),
        meta: { title: '作品评分' }
      },
      {
        path: 'notice',
        name: 'NoticeList',
        component: () => import('../views/notice/NoticeList.vue'),
        meta: { title: '公告管理' }
      },
      {
        path: 'award',
        name: 'AwardList',
        component: () => import('../views/award/AwardList.vue'),
        meta: { title: '获奖管理' }
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
