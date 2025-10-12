<template>
  <div class="config-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">系统配置</span>
      </template>
    </el-page-header>

    <el-card class="content-card" v-loading="loading">
      <el-form :model="configForm" label-width="150px" style="max-width: 600px">
        <el-divider content-position="left">
          <el-text type="primary" size="large">超期规则配置</el-text>
        </el-divider>
        
        <el-form-item label="免费保管天数">
          <el-input-number v-model="configForm.free_days" :min="1" :max="7" />
          <span style="margin-left: 10px; color: #909399">天</span>
        </el-form-item>

        <el-form-item label="超期4-7天收费">
          <el-input-number v-model="configForm.overdue_fee_level1" :min="0" :step="0.5" :precision="1" />
          <span style="margin-left: 10px; color: #909399">元/天</span>
        </el-form-item>

        <el-form-item label="超期8天及以上收费">
          <el-input-number v-model="configForm.overdue_fee_level2" :min="0" :step="0.5" :precision="1" />
          <span style="margin-left: 10px; color: #909399">元/天</span>
        </el-form-item>

        <el-form-item label="超期提醒天数">
          <el-select v-model="configForm.overdue_notify_days_arr" multiple placeholder="选择提醒天数">
            <el-option v-for="i in 7" :key="i" :label="`第${i}天`" :value="i" />
          </el-select>
        </el-form-item>

        <el-form-item label="长期滞留天数">
          <el-input-number v-model="configForm.long_overdue_days" :min="7" :max="30" />
          <span style="margin-left: 10px; color: #909399">天</span>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSave" :loading="saveLoading">
            保存配置
          </el-button>
          <el-button @click="loadConfig">
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <el-divider />

      <el-alert
        title="配置说明"
        type="info"
        :closable="false"
      >
        <template #default>
          <ul style="margin: 10px 0; padding-left: 20px">
            <li>免费保管天数：快递入库后的免费保管期限</li>
            <li>超期收费标准：分两个阶梯收费，超期越久收费越高</li>
            <li>超期提醒：在指定天数自动发送提醒通知</li>
            <li>长期滞留：超过指定天数将标记为长期滞留件</li>
          </ul>
        </template>
      </el-alert>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getConfigList, updateConfig } from '@/api/config'

const loading = ref(false)
const saveLoading = ref(false)

const configForm = reactive({
  free_days: 3,
  overdue_fee_level1: 1.0,
  overdue_fee_level2: 2.0,
  overdue_notify_days_arr: [3, 5],
  long_overdue_days: 15
})

const configMap = ref({})

const loadConfig = async () => {
  loading.value = true
  try {
    const res = await getConfigList()
    const configs = res.data
    
    configMap.value = {}
    configs.forEach(config => {
      configMap.value[config.configKey] = config
      
      if (config.configKey === 'free_days') {
        configForm.free_days = parseInt(config.configValue)
      } else if (config.configKey === 'overdue_fee_level1') {
        configForm.overdue_fee_level1 = parseFloat(config.configValue)
      } else if (config.configKey === 'overdue_fee_level2') {
        configForm.overdue_fee_level2 = parseFloat(config.configValue)
      } else if (config.configKey === 'overdue_notify_days') {
        configForm.overdue_notify_days_arr = config.configValue.split(',').map(Number)
      } else if (config.configKey === 'long_overdue_days') {
        configForm.long_overdue_days = parseInt(config.configValue)
      }
    })
  } catch (error) {
    console.error(error)
    ElMessage.error('加载配置失败')
  } finally {
    loading.value = false
  }
}

const handleSave = async () => {
  saveLoading.value = true
  try {
    const updates = [
      { configKey: 'free_days', configValue: String(configForm.free_days) },
      { configKey: 'overdue_fee_level1', configValue: String(configForm.overdue_fee_level1) },
      { configKey: 'overdue_fee_level2', configValue: String(configForm.overdue_fee_level2) },
      { configKey: 'overdue_notify_days', configValue: configForm.overdue_notify_days_arr.join(',') },
      { configKey: 'long_overdue_days', configValue: String(configForm.long_overdue_days) }
    ]

    for (const config of updates) {
      await updateConfig(config)
    }

    ElMessage.success('保存成功')
    loadConfig()
  } catch (error) {
    console.error(error)
    ElMessage.error('保存失败')
  } finally {
    saveLoading.value = false
  }
}

onMounted(() => {
  loadConfig()
})
</script>

<style scoped>
.config-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

