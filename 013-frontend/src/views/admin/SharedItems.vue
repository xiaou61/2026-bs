<template>
  <div class="admin-shared-items">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>共享物品管理</span>
          <el-button type="primary" @click="addDialogVisible = true">添加物品</el-button>
        </div>
      </template>

      <el-table :data="items" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="itemNo" label="物品编号" width="150" />
        <el-table-column label="类型" width="120">
          <template #default="{ row }">
            {{ getItemTypeText(row.itemType) }}
          </template>
        </el-table-column>
        <el-table-column prop="locationName" label="位置" width="150" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="hourlyPrice" label="小时价格" width="100" />
        <el-table-column prop="totalUsageCount" label="使用次数" width="100" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-dropdown>
              <el-button size="small">
                更多<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="updateStatus(row.id, 0)">设为可用</el-dropdown-item>
                  <el-dropdown-item @click="updateStatus(row.id, 2)">设为维护</el-dropdown-item>
                  <el-dropdown-item @click="updateStatus(row.id, 3)">设为故障</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <el-empty v-if="items.length === 0" description="暂无数据" />
    </el-card>

    <el-dialog v-model="addDialogVisible" title="添加共享物品" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="物品编号">
          <el-input v-model="form.itemNo" placeholder="如：BIKE001" />
        </el-form-item>
        <el-form-item label="物品类型">
          <el-select v-model="form.itemType" placeholder="请选择">
            <el-option label="共享单车" value="BIKE" />
            <el-option label="共享充电宝" value="CHARGER" />
            <el-option label="共享雨伞" value="UMBRELLA" />
          </el-select>
        </el-form-item>
        <el-form-item label="位置名称">
          <el-input v-model="form.locationName" placeholder="如：图书馆门口" />
        </el-form-item>
        <el-form-item label="小时租金">
          <el-input-number v-model="form.hourlyPrice" :min="0" :precision="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const items = ref([])
const addDialogVisible = ref(false)

const form = reactive({
  itemNo: '',
  itemType: '',
  locationName: '',
  hourlyPrice: 0
})

const getItemTypeText = (type) => {
  const map = {
    'BIKE': '共享单车',
    'CHARGER': '共享充电宝',
    'UMBRELLA': '共享雨伞'
  }
  return map[type] || type
}

const getStatusText = (status) => {
  const map = {
    0: '可用',
    1: '使用中',
    2: '维护中',
    3: '故障'
  }
  return map[status] || '未知'
}

const getStatusType = (status) => {
  const map = {
    0: 'success',
    1: 'primary',
    2: 'warning',
    3: 'danger'
  }
  return map[status] || ''
}

const updateStatus = (id, status) => {
  ElMessage.success('状态更新成功')
}

const handleAdd = () => {
  ElMessage.success('添加成功')
  addDialogVisible.value = false
}

onMounted(() => {
})
</script>

<style scoped>
</style>

