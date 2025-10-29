import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/',
      redirect: '/student/jobs'
    },
    {
      path: '/student',
      component: () => import('@/layout/StudentLayout.vue'),
      children: [
        {
          path: 'jobs',
          name: 'StudentJobs',
          component: () => import('@/views/student/Jobs.vue')
        },
        {
          path: 'job/:id',
          name: 'JobDetail',
          component: () => import('@/views/student/JobDetail.vue')
        },
        {
          path: 'resume',
          name: 'Resume',
          component: () => import('@/views/student/Resume.vue')
        },
        {
          path: 'applications',
          name: 'Applications',
          component: () => import('@/views/student/Applications.vue')
        },
        {
          path: 'interviews',
          name: 'Interviews',
          component: () => import('@/views/student/Interviews.vue')
        },
        {
          path: 'experiences',
          name: 'Experiences',
          component: () => import('@/views/student/Experiences.vue')
        },
        {
          path: 'experience/:id',
          name: 'ExperienceDetail',
          component: () => import('@/views/student/ExperienceDetail.vue')
        },
        {
          path: 'referrals',
          name: 'Referrals',
          component: () => import('@/views/student/Referrals.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/Profile.vue')
        }
      ]
    },
    {
      path: '/company',
      component: () => import('@/layout/CompanyLayout.vue'),
      children: [
        {
          path: 'jobs',
          name: 'CompanyJobs',
          component: () => import('@/views/company/Jobs.vue')
        },
        {
          path: 'applications',
          name: 'CompanyApplications',
          component: () => import('@/views/company/Applications.vue')
        },
        {
          path: 'interviews',
          name: 'CompanyInterviews',
          component: () => import('@/views/company/Interviews.vue')
        },
        {
          path: 'company-info',
          name: 'CompanyInfo',
          component: () => import('@/views/company/CompanyInfo.vue')
        },
        {
          path: 'profile',
          name: 'CompanyProfile',
          component: () => import('@/views/Profile.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (!token && to.path !== '/login' && to.path !== '/register') {
    next('/login')
  } else {
    next()
  }
})

export default router

