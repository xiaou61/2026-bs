<template>
  <div>
    <h2 style="margin-bottom: 20px">系统配置</h2>

    <el-card style="margin-bottom: 20px">
      <template #header>
        <div style="font-weight: bold">选课时间配置</div>
      </template>
      <el-form :model="configForm" label-width="150px" v-loading="loading">
        <el-form-item label="选课开始时间">
          <el-date-picker
            v-model="startTime"
            type="datetime"
            placeholder="选择日期时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 300px"
          />
        </el-form-item>
        <el-form-item label="选课结束时间">
          <el-date-picker
            v-model="endTime"
            type="datetime"
            placeholder="选择日期时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 300px"
          />
        </el-form-item>
        <el-form-item label="是否开放选课">
          <el-switch 
            v-model="selectionEnabled"
            active-text="开放"
            inactive-text="关闭"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave">保存配置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table :data="configList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="configKey" label="配置键" width="250" />
      <el-table-column prop="configValue" label="配置值" width="300" />
      <el-table-column prop="description" label="说明" />
      <el-table-column prop="updatedTime" label="更新时间" width="180" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getConfigList, updateConfig } from '@/api/config'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const configList = ref([])
const configForm = ref({})
const startTime = ref('')
const endTime = ref('')
const selectionEnabled = ref(false)

const loadConfigs = async () => {
  loading.value = true
  try {
    const res = await getConfigList()
    configList.value = res.data
    
    const configs = {}
    res.data.forEach(item => {
      configs[item.configKey] = item
    })
    
    if (configs['selection_start_time']) {
      startTime.value = configs['selection_start_time'].configValue
    }
    if (configs['selection_end_time']) {
      endTime.value = configs['selection_end_time'].configValue
    }
    if (configs['selection_enabled']) {
      selectionEnabled.value = configs['selection_enabled'].configValue === 'true'
    }
  } finally {
    loading.value = false
  }
}

const handleSave = async () => {
  try {
    const configs = {}
    configList.value.forEach(item => {
      configs[item.configKey] = item
    })

    const updates = []
    
    if (configs['selection_start_time']) {
      updates.push(
        updateConfig(configs['selection_start_time'].id, {
          ...configs['selection_start_time'],
          configValue: startTime.value
        })
      )
    }
    
    if (configs['selection_end_time']) {
      updates.push(
        updateConfig(configs['selection_end_time'].id, {
          ...configs['selection_end_time'],
          configValue: endTime.value
        })
      )
    }
    
    if (configs['selection_enabled']) {
      updates.push(
        updateConfig(configs['selection_enabled'].id, {
          ...configs['selection_enabled'],
          configValue: selectionEnabled.value ? 'true' : 'false'
        })
      )
    }
    
    await Promise.all(updates)
    ElMessage.success('配置保存成功')
    await loadConfigs()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadConfigs()
})
</script>

