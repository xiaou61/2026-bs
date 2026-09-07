import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/auth/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/auth/Register.vue')
  },
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/recipes',
    name: 'recipes',
    component: () => import('../views/recipes/RecipeList.vue')
  },
  {
    path: '/recipes/:id',
    name: 'recipe-detail',
    component: () => import('../views/recipes/RecipeDetail.vue')
  },
  {
    path: '/ingredients',
    name: 'ingredients',
    component: () => import('../views/ingredients/IngredientList.vue')
  },
  {
    path: '/ingredients/:id',
    name: 'ingredient-detail',
    component: () => import('../views/ingredients/IngredientDetail.vue')
  },
  {
    path: '/topics',
    name: 'topics',
    component: () => import('../views/topics/TopicList.vue')
  },
  {
    path: '/topics/:id',
    name: 'topic-detail',
    component: () => import('../views/topics/TopicDetail.vue')
  },
  {
    path: '/create-recipe',
    name: 'create-recipe',
    component: () => import('../views/recipes/RecipeForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/create-topic',
    name: 'create-topic',
    component: () => import('../views/topics/TopicForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/my-recipes',
    name: 'my-recipes',
    component: () => import('../views/user/MyRecipes.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/my-topics',
    name: 'my-topics',
    component: () => import('../views/user/MyTopics.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('../views/user/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/collections',
    name: 'collections',
    component: () => import('../views/user/Collections.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: () => import('../views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth) {
    if (userStore.token) {
      next()
    } else {
      next({ name: 'login', query: { redirect: to.fullPath } })
    }
  } else {
    next()
  }
})

export default router
