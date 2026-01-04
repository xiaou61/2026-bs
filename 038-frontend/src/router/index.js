import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue')
      },
      {
        path: 'diet-record',
        name: 'DietRecord',
        component: () => import('@/views/DietRecord.vue')
      },
      {
        path: 'food-library',
        name: 'FoodLibrary',
        component: () => import('@/views/FoodLibrary.vue')
      },
      {
        path: 'nutrition',
        name: 'Nutrition',
        component: () => import('@/views/Nutrition.vue')
      },
      {
        path: 'health',
        name: 'Health',
        component: () => import('@/views/Health.vue')
      },
      {
        path: 'goal',
        name: 'Goal',
        component: () => import('@/views/Goal.vue')
      },
      {
        path: 'recipe',
        name: 'Recipe',
        component: () => import('@/views/Recipe.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
