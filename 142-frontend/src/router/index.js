import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
      { path: 'policy', component: () => import('../views/InsurancePolicy.vue') },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue') },
      { path: 'customer', component: () => import('../views/CustomerProfile.vue') },
      { path: 'claim', component: () => import('../views/ClaimApplication.vue') },
      { path: 'accident', component: () => import('../views/AccidentReport.vue') },
      { path: 'material', component: () => import('../views/MaterialChecklist.vue') },
      { path: 'review', component: () => import('../views/MaterialReview.vue') },
      { path: 'assessment', component: () => import('../views/LossAssessment.vue') },
      { path: 'compensation', component: () => import('../views/CompensationRecord.vue') },
      { path: 'progress', component: () => import('../views/ProgressTrack.vue') },
      { path: 'followup', component: () => import('../views/FollowUpRecord.vue') },
      { path: 'notice', component: () => import('../views/ClaimNotice.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router







