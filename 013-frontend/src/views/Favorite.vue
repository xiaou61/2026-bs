<template>
  <div class="favorite-page">
    <el-card>
      <template #header>
        <span>我的收藏</span>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="闲置物品" name="idle">
          <el-empty description="收藏功能开发中" />
        </el-tab-pane>

        <el-tab-pane label="技能服务" name="skill">
          <el-empty description="收藏功能开发中" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('idle')
</script>

<style scoped>
</style>

