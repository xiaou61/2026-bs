<template>
  <el-container style="height: 100vh">
    <el-aside width="200px" style="background: #304156">
      <div class="logo">安全科普管理</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>数据统计</span></el-menu-item>
        <el-menu-item index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item index="/category"><el-icon><Folder /></el-icon><span>分类管理</span></el-menu-item>
        <el-menu-item index="/article"><el-icon><Document /></el-icon><span>文章管理</span></el-menu-item>
        <el-menu-item index="/question"><el-icon><QuestionFilled /></el-icon><span>题库管理</span></el-menu-item>
        <el-menu-item index="/news"><el-icon><Notification /></el-icon><span>资讯管理</span></el-menu-item>
        <el-menu-item index="/qa"><el-icon><ChatDotRound /></el-icon><span>问答管理</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background: #fff; display: flex; justify-content: flex-end; align-items: center; border-bottom: 1px solid #eee;">
        <span style="margin-right: 20px">{{ adminInfo?.nickname }}</span>
        <el-button type="danger" link @click="logout">退出登录</el-button>
      </el-header>
      <el-main style="background: #f5f5f5"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const adminInfo = computed(() => JSON.parse(localStorage.getItem('admin_info') || '{}'))

const logout = () => {
  localStorage.removeItem('admin_token')
  localStorage.removeItem('admin_info')
  router.push('/login')
}
</script>

<style scoped>
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; }
</style>
