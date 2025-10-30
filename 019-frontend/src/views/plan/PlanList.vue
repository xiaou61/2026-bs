<template>
  <div class="plan-list">
    <el-card>
      <template #header>
        <div class="header">
          <span>健身计划</span>
          <el-button type="primary" icon="Plus" @click="$router.push('/plan/create')">
            创建计划
          </el-button>
        </div>
      </template>
      
      <el-row :gutter="20">
        <el-col :span="8" v-for="plan in plans" :key="plan.id">
          <el-card class="plan-card">
            <div class="plan-header">
              <h3>{{ plan.planName }}</h3>
              <el-tag :type="getStatusType(plan.status)">
                {{ getStatusName(plan.status) }}
              </el-tag>
            </div>
            
            <div class="plan-info">
              <div class="info-item">
                <span class="label">类型:</span>
                <span>{{ getPlanTypeName(plan.planType) }}</span>
              </div>
              <div class="info-item">
                <span class="label">目标:</span>
                <span>{{ plan.targetDesc }}</span>
              </div>
              <div class="info-item">
                <span class="label">时间:</span>
                <span>{{ plan.startDate }} 至 {{ plan.endDate }}</span>
              </div>
            </div>
            
            <el-progress
              :percentage="getProgress(plan)"
              :status="plan.status === 'completed' ? 'success' : ''"
            />
            
            <div class="plan-stats">
              <span>已完成: {{ plan.completedDays }} / {{ plan.durationDays }} 天</span>
            </div>
            
            <div class="plan-actions">
              <el-button
                size="small"
                type="primary"
                @click="handleUpdateProgress(plan)"
                :disabled="plan.status !== 'active'"
              >
                更新进度
              </el-button>
              <el-button
                size="small"
                type="danger"
                @click="handleDelete(plan.id)"
              >
                删除
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <el-empty v-if="!plans.length" description="暂无健身计划" />
      
      <div class="pagination" v-if="total > 0">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadPlans"
        />
      </div>
    </el-card>
    
    <el-dialog v-model="dialogVisible" title="更新进度" width="400px">
      <el-form>
        <el-form-item label="已完成天数">
          <el-input-number v-model="currentProgress" :min="0" :max="maxDays" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleConfirmUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPlanList, updateProgress, deletePlan } from '@/api/plan'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const plans = ref([])
const page = ref(1)
const size = ref(9)
const total = ref(0)

const dialogVisible = ref(false)
const currentPlan = ref(null)
const currentProgress = ref(0)
const maxDays = ref(0)

const getPlanTypeName = (type) => {
  const map = {
    strength: '力量训练',
    cardio: '有氧运动',
    lose_weight: '减脂减重',
    gain_muscle: '增肌增重'
  }
  return map[type] || type
}

const getStatusName = (status) => {
  const map = {
    active: '进行中',
    completed: '已完成',
    abandoned: '已放弃'
  }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = {
    active: '',
    completed: 'success',
    abandoned: 'info'
  }
  return map[status] || ''
}

const getProgress = (plan) => {
  return Math.round((plan.completedDays / plan.durationDays) * 100)
}

const loadPlans = async () => {
  loading.value = true
  try {
    const res = await getPlanList({ page: page.value, size: size.value })
    plans.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleUpdateProgress = (plan) => {
  currentPlan.value = plan
  currentProgress.value = plan.completedDays
  maxDays.value = plan.durationDays
  dialogVisible.value = true
}

const handleConfirmUpdate = async () => {
  try {
    await updateProgress(currentPlan.value.id, { completedDays: currentProgress.value })
    ElMessage.success('更新成功')
    dialogVisible.value = false
    loadPlans()
  } catch (error) {
    console.error(error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个计划吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deletePlan(id)
    ElMessage.success('删除成功')
    loadPlans()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadPlans()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.plan-card {
  margin-bottom: 20px;
}

.plan-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.plan-header h3 {
  margin: 0;
  font-size: 18px;
}

.plan-info {
  margin-bottom: 15px;
}

.info-item {
  margin-bottom: 8px;
  font-size: 14px;
  color: #666;
}

.info-item .label {
  font-weight: bold;
  margin-right: 5px;
}

.plan-stats {
  margin: 15px 0;
  font-size: 14px;
  color: #666;
}

.plan-actions {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>

