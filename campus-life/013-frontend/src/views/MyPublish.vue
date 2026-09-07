<template>
  <div class="my-publish">
    <el-card>
      <template #header>
        <span>我的发布</span>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="闲置物品" name="idle">
          <el-row :gutter="20">
            <el-col :span="8" v-for="item in idleItems" :key="item.id">
              <el-card shadow="hover">
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
                    <el-tag :type="getIdleStatusType(item.status)">{{ getIdleStatusText(item.status) }}</el-tag>
                    <span>{{ item.orderCount }}次成交</span>
                  </div>
                  <div class="item-actions">
                    <el-button size="small" @click="$router.push(`/idle/${item.id}`)">查看</el-button>
                    <el-button size="small" type="danger" @click="handleDeleteIdle(item.id)">删除</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-if="idleItems.length === 0" description="暂无发布" />
        </el-tab-pane>

        <el-tab-pane label="技能服务" name="skill">
          <el-row :gutter="20">
            <el-col :span="8" v-for="skill in skillServices" :key="skill.id">
              <el-card shadow="hover">
                <div class="item-card">
                  <div class="item-image">
                    <el-icon :size="60"><User /></el-icon>
                  </div>
                  <h4>{{ skill.title }}</h4>
                  <div class="item-price">
                    <span class="price">¥{{ skill.hourlyPrice }}</span>
                    <span class="unit">/小时</span>
                  </div>
                  <div class="item-info">
                    <el-tag :type="skill.status === 1 ? 'success' : 'info'">
                      {{ skill.status === 1 ? '上架中' : '已下架' }}
                    </el-tag>
                    <span>{{ skill.orderCount }}次成交</span>
                  </div>
                  <div class="item-actions">
                    <el-button size="small" @click="$router.push(`/skill/${skill.id}`)">查看</el-button>
                    <el-button size="small" type="danger" @click="handleDeleteSkill(skill.id)">删除</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-if="skillServices.length === 0" description="暂无发布" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyIdle, deleteIdle } from '@/api/idle'
import { getMySkill, deleteSkill } from '@/api/skill'
import { ElMessage, ElMessageBox } from 'element-plus'

const activeTab = ref('idle')
const idleItems = ref([])
const skillServices = ref([])

const getIdleStatusText = (status) => {
  const map = {
    0: '待审核',
    1: '已上架',
    2: '已下架',
    3: '审核拒绝'
  }
  return map[status] || '未知'
}

const getIdleStatusType = (status) => {
  const map = {
    0: 'warning',
    1: 'success',
    2: 'info',
    3: 'danger'
  }
  return map[status] || ''
}

const loadIdleItems = async () => {
  try {
    const res = await getMyIdle({ page: 1, size: 100 })
    idleItems.value = res.data.records
  } catch (error) {
    console.error(error)
  }
}

const loadSkillServices = async () => {
  try {
    const res = await getMySkill({ page: 1, size: 100 })
    skillServices.value = res.data.records
  } catch (error) {
    console.error(error)
  }
}

const handleTabChange = () => {
  if (activeTab.value === 'idle') {
    loadIdleItems()
  } else {
    loadSkillServices()
  }
}

const handleDeleteIdle = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该物品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteIdle(id)
    ElMessage.success('删除成功')
    loadIdleItems()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleDeleteSkill = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该服务吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteSkill(id)
    ElMessage.success('删除成功')
    loadSkillServices()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadIdleItems()
})
</script>

<style scoped>
.item-card {
  text-align: center;
}

.item-image {
  height: 120px;
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
  font-size: 18px;
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
  margin-bottom: 15px;
  font-size: 12px;
  color: #909399;
}

.item-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
}
</style>

