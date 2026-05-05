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
      { path: 'project', component: () => import('../views/ConstructionProject.vue') },
      { path: 'team', component: () => import('../views/WorkTeam.vue') },
      { path: 'inspector', component: () => import('../views/SafetyInspector.vue') },
      { path: 'plan', component: () => import('../views/InspectionPlan.vue') },
      { path: 'task', component: () => import('../views/InspectionTask.vue') },
      { path: 'hazard', component: () => import('../views/HazardReport.vue') },
      { path: 'rectify', component: () => import('../views/RectificationOrder.vue') },
      { path: 'acceptance', component: () => import('../views/AcceptanceRecord.vue') },
      { path: 'training', component: () => import('../views/SafetyTraining.vue') },
      { path: 'equipment', component: () => import('../views/EquipmentCheck.vue') },
      { path: 'supply', component: () => import('../views/ProtectiveSupply.vue') },
      { path: 'warning', component: () => import('../views/RiskWarning.vue') },
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
