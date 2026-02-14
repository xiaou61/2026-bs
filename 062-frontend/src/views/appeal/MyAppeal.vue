<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.alertNo" placeholder="预警号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已回复" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增申诉</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="alertNo" label="预警号" min-width="180" />
      <el-table-column prop="content" label="申诉内容" min-width="280" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待处理' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="reply" label="平台回复" min-width="240" />
      <el-table-column prop="replyTime" label="回复时间" width="180" />
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增申诉" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="关联预警" prop="alertId">
        <el-select v-model="form.alertId" style="width: 100%" filterable placeholder="请选择预警">
          <el-option v-for="alert in alerts" :key="alert.id" :label="`${alert.alertNo} / ${alert.eventNo} / ${alert.riskLevel}`" :value="alert.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="申诉内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="4" maxlength="1000" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addAppeal, getMyAlertPage, getMyAppealPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const alerts = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, status: null, alertNo: '' })
const form = reactive({ alertId: null, content: '' })

const rules = {
  alertId: [{ required: true, message: '请选择关联预警', trigger: 'change' }],
  content: [{ required: true, message: '请输入申诉内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyAppealPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadAlerts = async () => {
  const res = await getMyAlertPage({ pageNum: 1, pageSize: 200 })
  alerts.value = (res.data.records || []).filter(i => [2, 3].includes(i.status))
}

const openAdd = async () => {
  await loadAlerts()
  if (!alerts.value.length) {
    ElMessage.warning('暂无可申诉预警')
    return
  }
  Object.assign(form, { alertId: null, content: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addAppeal(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

onMounted(async () => {
  await loadData()
  await loadAlerts()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
