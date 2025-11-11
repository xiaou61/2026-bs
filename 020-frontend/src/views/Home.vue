<template>
  <div class="home-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#409EFF"><Document /></el-icon>
            <div>
              <div class="stat-value">{{ stats.resources }}</div>
              <div class="stat-label">学习资源</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#67C23A"><UserFilled /></el-icon>
            <div>
              <div class="stat-value">{{ stats.groups }}</div>
              <div class="stat-label">学习小组</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#E6A23C"><EditPen /></el-icon>
            <div>
              <div class="stat-value">{{ stats.questions }}</div>
              <div class="stat-label">题库题目</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#F56C6C"><Coin /></el-icon>
            <div>
              <div class="stat-value">{{ userStore.userInfo?.points || 0 }}</div>
              <div class="stat-label">我的积分</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最新资源</span>
              <el-button text type="primary" @click="goToResource">查看更多</el-button>
            </div>
          </template>
          <el-empty v-if="!recentResources.length" description="暂无资源" />
          <div v-else>
            <div v-for="item in recentResources" :key="item.id" class="list-item" @click="goToResourceDetail(item.id)">
              <div class="item-title">{{ item.title }}</div>
              <div class="item-meta">
                <el-tag size="small">{{ item.category }}</el-tag>
                <span>下载：{{ item.downloadCount }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>热门问答</span>
              <el-button text type="primary" @click="goToQA">查看更多</el-button>
            </div>
          </template>
          <el-empty v-if="!recentQuestions.length" description="暂无问题" />
          <div v-else>
            <div v-for="item in recentQuestions" :key="item.id" class="list-item" @click="goToQADetail(item.id)">
              <div class="item-title">{{ item.title }}</div>
              <div class="item-meta">
                <el-tag v-if="item.bounty > 0" size="small" type="warning">悬赏 {{ item.bounty }}</el-tag>
                <el-tag size="small" :type="item.status === 'resolved' ? 'success' : 'info'">
                  {{ item.status === 'resolved' ? '已解决' : '待解决' }}
                </el-tag>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getResourceList } from '@/api/resource'
import { getQuestionList } from '@/api/qa'

const router = useRouter()
const userStore = useUserStore()

const stats = ref({
  resources: 0,
  groups: 0,
  questions: 0
})

const recentResources = ref([])
const recentQuestions = ref([])

const loadData = async () => {
  try {
    const resData = await getResourceList({ page: 1, size: 5 })
    recentResources.value = resData.data.records
    stats.value.resources = resData.data.total

    const qaData = await getQuestionList({ page: 1, size: 5 })
    recentQuestions.value = qaData.data.records
  } catch (error) {
    console.error(error)
  }
}

const goToResource = () => router.push('/resource')
const goToResourceDetail = (id) => router.push(`/resource/${id}`)
const goToQA = () => router.push('/qa')
const goToQADetail = (id) => router.push(`/qa/${id}`)

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.home-container {
  max-width: 1400px;
  margin: 0 auto;
}

.stat-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  font-size: 48px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  color: #909399;
  margin-top: 5px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.list-item {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.list-item:last-child {
  border-bottom: none;
}

.list-item:hover {
  background-color: #f5f7fa;
}

.item-title {
  font-size: 14px;
  color: #303133;
  margin-bottom: 8px;
}

.item-meta {
  display: flex;
  gap: 10px;
  font-size: 12px;
  color: #909399;
}
</style>

