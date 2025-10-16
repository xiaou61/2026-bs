<template>
  <div class="skill-list">
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
              <el-radio label="学业辅导">学业辅导</el-radio>
              <el-radio label="设计服务">设计服务</el-radio>
              <el-radio label="摄影服务">摄影服务</el-radio>
              <el-radio label="音乐教学">音乐教学</el-radio>
              <el-radio label="健身指导">健身指导</el-radio>
              <el-radio label="代做服务">代做服务</el-radio>
            </el-radio-group>
          </div>

          <el-divider />

          <el-button type="primary" @click="$router.push('/skill/publish')" style="width: 100%">
            <el-icon><Plus /></el-icon>
            发布技能服务
          </el-button>
        </el-card>
      </el-col>

      <el-col :span="18">
        <el-card>
          <template #header>
            <el-input v-model="filters.keyword" placeholder="搜索服务" @keyup.enter="handleSearch">
              <template #append>
                <el-button :icon="Search" @click="handleSearch" />
              </template>
            </el-input>
          </template>

          <el-row :gutter="20">
            <el-col :span="8" v-for="skill in skills" :key="skill.id">
              <el-card shadow="hover" @click="$router.push(`/skill/${skill.id}`)">
                <div class="skill-card">
                  <div class="skill-image">
                    <el-icon :size="60"><User /></el-icon>
                  </div>
                  <h4>{{ skill.title }}</h4>
                  <div class="skill-price">
                    <span class="price">¥{{ skill.hourlyPrice }}</span>
                    <span class="unit">/小时</span>
                  </div>
                  <div class="skill-info">
                    <el-rate v-model="skill.rating" disabled show-score text-color="#ff9900" />
                    <span class="orders">{{ skill.orderCount }}次成交</span>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <el-empty v-if="skills.length === 0" description="暂无数据" />

          <el-pagination
            v-if="total > 0"
            v-model:current-page="page"
            v-model:page-size="size"
            :total="total"
            layout="total, prev, pager, next"
            @current-change="loadSkills"
            style="margin-top: 20px; justify-content: center;"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getSkillList } from '@/api/skill'
import { Search } from '@element-plus/icons-vue'

const page = ref(1)
const size = ref(9)
const total = ref(0)
const skills = ref([])

const filters = reactive({
  category: '',
  keyword: ''
})

const loadSkills = async () => {
  try {
    const res = await getSkillList({
      page: page.value,
      size: size.value,
      category: filters.category,
      keyword: filters.keyword
    })
    skills.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleSearch = () => {
  page.value = 1
  loadSkills()
}

onMounted(() => {
  loadSkills()
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

.skill-card {
  text-align: center;
  cursor: pointer;
}

.skill-image {
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  margin-bottom: 15px;
  color: #909399;
}

.skill-card h4 {
  margin-bottom: 10px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.skill-price {
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

.skill-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
  font-size: 12px;
}

.orders {
  color: #909399;
}
</style>


