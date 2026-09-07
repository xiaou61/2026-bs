<template>
  <div class="idle-list">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <template #header>
            <span>筛选</span>
          </template>

          <div class="filter-section">
            <h4>分类</h4>
            <el-radio-group v-model="filters.category" @change="handleSearch">
              <el-radio label="">全部</el-radio>
              <el-radio label="数码设备">数码设备</el-radio>
              <el-radio label="乐器">乐器</el-radio>
              <el-radio label="投影设备">投影设备</el-radio>
              <el-radio label="运动器材">运动器材</el-radio>
              <el-radio label="服装道具">服装道具</el-radio>
              <el-radio label="图书教材">图书教材</el-radio>
            </el-radio-group>
          </div>

          <el-divider />

          <el-button type="primary" @click="$router.push('/idle/publish')" style="width: 100%">
            <el-icon><Plus /></el-icon>
            发布闲置物品
          </el-button>
        </el-card>
      </el-col>

      <el-col :span="18">
        <el-card>
          <template #header>
            <el-input v-model="filters.keyword" placeholder="搜索物品" @keyup.enter="handleSearch">
              <template #append>
                <el-button :icon="Search" @click="handleSearch" />
              </template>
            </el-input>
          </template>

          <el-row :gutter="20">
            <el-col :span="8" v-for="item in items" :key="item.id">
              <el-card shadow="hover" @click="$router.push(`/idle/${item.id}`)">
                <div class="item-card">
                  <div class="item-image">
                    <el-icon :size="60"><Camera /></el-icon>
                  </div>
                  <h4>{{ item.title }}</h4>
                  <div class="item-price">
                    <span class="price">¥{{ item.dailyPrice }}</span>
                    <span class="unit">/天</span>
                  </div>
                  <div class="item-info">
                    <el-tag size="small">{{ item.category }}</el-tag>
                    <span class="views">{{ item.viewCount }}次浏览</span>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <el-empty v-if="items.length === 0" description="暂无数据" />

          <el-pagination
            v-if="total > 0"
            v-model:current-page="page"
            v-model:page-size="size"
            :total="total"
            layout="total, prev, pager, next"
            @current-change="loadItems"
            style="margin-top: 20px; justify-content: center;"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getIdleList } from '@/api/idle'
import { Search } from '@element-plus/icons-vue'

const page = ref(1)
const size = ref(9)
const total = ref(0)
const items = ref([])

const filters = reactive({
  category: '',
  keyword: ''
})

const loadItems = async () => {
  try {
    const res = await getIdleList({
      page: page.value,
      size: size.value,
      category: filters.category,
      keyword: filters.keyword
    })
    items.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleSearch = () => {
  page.value = 1
  loadItems()
}

onMounted(() => {
  loadItems()
})
</script>

<style scoped>
.filter-section h4 {
  margin-bottom: 15px;
  color: #303133;
}

.el-radio-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.item-card {
  text-align: center;
  cursor: pointer;
}

.item-image {
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  margin-bottom: 15px;
  color: #909399;
}

.item-card h4 {
  margin-bottom: 10px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  margin-bottom: 10px;
}

.price {
  color: #f56c6c;
  font-size: 20px;
  font-weight: bold;
}

.unit {
  color: #909399;
  font-size: 14px;
}

.item-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  color: #909399;
}
</style>


