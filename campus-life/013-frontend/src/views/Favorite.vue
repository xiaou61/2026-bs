<template>
  <div class="favorite-page">
    <el-card>
      <template #header>
        <span>我的收藏</span>
      </template>

      <el-tabs v-model="activeTab" @tab-change="loadFavorites">
        <el-tab-pane label="闲置物品" name="idle">
          <el-row :gutter="20">
            <el-col :span="8" v-for="item in idleFavorites" :key="item.favoriteId">
              <el-card shadow="hover" @click="$router.push(`/idle/${item.targetId}`)">
                <div class="favorite-card">
                  <div class="favorite-image">
                    <el-icon :size="60"><Camera /></el-icon>
                  </div>
                  <h4>{{ item.title }}</h4>
                  <div class="favorite-meta">
                    <el-tag>{{ item.category }}</el-tag>
                    <span>{{ item.createTime }}</span>
                  </div>
                  <div class="favorite-price">
                    ¥{{ item.price }}{{ item.priceUnit }}
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-if="idleFavorites.length === 0" description="暂无收藏" />
        </el-tab-pane>

        <el-tab-pane label="技能服务" name="skill">
          <el-row :gutter="20">
            <el-col :span="8" v-for="item in skillFavorites" :key="item.favoriteId">
              <el-card shadow="hover" @click="$router.push(`/skill/${item.targetId}`)">
                <div class="favorite-card">
                  <div class="favorite-image">
                    <el-icon :size="60"><User /></el-icon>
                  </div>
                  <h4>{{ item.title }}</h4>
                  <div class="favorite-meta">
                    <el-tag>{{ item.category }}</el-tag>
                    <span>{{ item.createTime }}</span>
                  </div>
                  <div class="favorite-price">
                    ¥{{ item.price }}{{ item.priceUnit }}
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-if="skillFavorites.length === 0" description="暂无收藏" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getFavoriteList } from '@/api/favorite'

const activeTab = ref('idle')
const idleFavorites = ref([])
const skillFavorites = ref([])

const loadFavorites = async () => {
  try {
    if (activeTab.value === 'idle') {
      const res = await getFavoriteList({ targetType: 'IDLE' })
      idleFavorites.value = res.data
    } else {
      const res = await getFavoriteList({ targetType: 'SKILL' })
      skillFavorites.value = res.data
    }
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadFavorites()
})
</script>

<style scoped>
.favorite-card {
  cursor: pointer;
}

.favorite-image {
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  margin-bottom: 15px;
  color: #909399;
}

.favorite-card h4 {
  margin-bottom: 12px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.favorite-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
  font-size: 12px;
  color: #909399;
}

.favorite-price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
}
</style>

