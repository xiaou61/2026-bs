<template>
  <div id="app">
    <el-container>
      <el-header v-if="showHeader" height="60px">
        <nav-bar />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
      <el-footer v-if="showFooter" height="100px">
        <app-footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import NavBar from './components/NavBar.vue'
import AppFooter from './components/AppFooter.vue'

const route = useRoute()

const showHeader = computed(() => {
  return !['login', 'register'].includes(route.name)
})

const showFooter = computed(() => {
  return !['login', 'register'].includes(route.name)
})
</script>

<style scoped>
#app {
  width: 100%;
  height: 100vh;
}

:deep(.el-header) {
  padding: 0;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

:deep(.el-footer) {
  background-color: #f5f5f5;
  padding: 20px;
  text-align: center;
  border-top: 1px solid #eee;
}

:deep(.el-main) {
  padding: 20px;
  background-color: #ffffff;
}
</style>
