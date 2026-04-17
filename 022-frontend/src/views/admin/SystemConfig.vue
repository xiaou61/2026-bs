<template>
  <div class="system-config">
    <div class="page-header">
      <h2>系统配置</h2>
      <el-button type="primary" @click="loadConfigs">刷新</el-button>
    </div>

    <el-table :data="configs" stripe v-loading="loading">
      <el-table-column prop="configKey" label="配置键" width="220" />
      <el-table-column prop="configValue" label="配置值" width="180" />
      <el-table-column prop="description" label="说明" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="编辑配置" width="460px">
      <el-form :model="formData" label-width="90px">
        <el-form-item label="配置键">
          <el-input v-model="formData.configKey" disabled />
        </el-form-item>
        <el-form-item label="配置值">
          <el-input v-model="formData.configValue" />
        </el-form-item>
        <el-form-item label="说明">
          <el-input v-model="formData.description" type="textarea" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getSystemConfigs, updateSystemConfig } from '@/api/systemConfig'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const configs = ref([])
const dialogVisible = ref(false)
const formData = reactive({
  configKey: '',
  configValue: '',
  description: ''
})

const loadConfigs = async () => {
  loading.value = true
  try {
    const res = await getSystemConfigs()
    configs.value = res.data || []
  } finally {
    loading.value = false
  }
}

const openEditDialog = (row) => {
  formData.configKey = row.configKey
  formData.configValue = row.configValue
  formData.description = row.description
  dialogVisible.value = true
}

const handleSave = async () => {
  await updateSystemConfig({ ...formData })
  ElMessage.success('系统配置已更新')
  dialogVisible.value = false
  loadConfigs()
}

onMounted(() => {
  loadConfigs()
})
</script>

<style lang="scss" scoped>
.system-config {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
}
</style>
