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
      { path: 'patient', component: () => import('../views/PatientProfile.vue') },
      { path: 'drug', component: () => import('../views/DrugCatalog.vue') },
      { path: 'reporter', component: () => import('../views/ReporterProfile.vue') },
      { path: 'report', component: () => import('../views/AdverseReport.vue') },
      { path: 'symptom', component: () => import('../views/ReactionSymptom.vue') },
      { path: 'risk', component: () => import('../views/RiskAssessment.vue') },
      { path: 'plan', component: () => import('../views/FollowupPlan.vue') },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue') },
      { path: 'review', component: () => import('../views/CaseReview.vue') },
      { path: 'advice', component: () => import('../views/TreatmentAdvice.vue') },
      { path: 'department', component: () => import('../views/DepartmentInfo.vue') },
      { path: 'statistic', component: () => import('../views/StatisticsReport.vue') },
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
