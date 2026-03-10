<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="资料标题" clearable style="width: 220px" />
        <el-select v-model="query.auditStatus" placeholder="审核状态" clearable style="width: 140px">
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="资料标题" min-width="220" />
        <el-table-column prop="auditStatus" label="当前状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.auditStatus === 1 ? 'success' : row.auditStatus === 2 ? 'danger' : 'warning'">
              {{ row.auditStatus === 1 ? '已通过' : row.auditStatus === 2 ? '已驳回' : '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="success" @click="openAudit(row, 1)">通过</el-button>
            <el-button link type="danger" @click="openAudit(row, 2)">驳回</el-button>
            <el-button link type="info" @click="loadLogs(row)">记录</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="auditDialog" title="提交审核" width="520px">
      <el-form :model="auditForm" label-width="90px">
        <el-form-item label="资料ID"><el-input v-model="auditForm.materialId" disabled /></el-form-item>
        <el-form-item label="审核结果">
          <el-tag :type="auditForm.auditStatus === 1 ? 'success' : 'danger'">
            {{ auditForm.auditStatus === 1 ? '通过' : '驳回' }}
          </el-tag>
        </el-form-item>
        <el-form-item label="审核意见"><el-input v-model="auditForm.auditRemark" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="auditDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAuditSubmit">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="logDialog" title="审核记录" width="700px">
      <el-table :data="auditLogs">
        <el-table-column prop="materialId" label="资料ID" width="90" />
        <el-table-column prop="auditStatus" label="状态" width="90">
          <template #default="{ row }">{{ row.auditStatus === 1 ? '通过' : row.auditStatus === 2 ? '驳回' : '待审' }}</template>
        </el-table-column>
        <el-table-column prop="auditRemark" label="意见" min-width="220" />
        <el-table-column prop="auditorId" label="审核人ID" width="100" />
        <el-table-column prop="auditTime" label="时间" width="180" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getAuditList, getMaterialList, submitAudit } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const auditDialog = ref(false)
const logDialog = ref(false)
const auditLogs = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, title: '', auditStatus: null })
const auditForm = reactive({ materialId: null, auditStatus: 1, auditRemark: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMaterialList(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAudit = (row, status) => {
  auditForm.materialId = row.id
  auditForm.auditStatus = status
  auditForm.auditRemark = ''
  auditDialog.value = true
}

const handleAuditSubmit = async () => {
  await submitAudit(auditForm)
  ElMessage.success('审核完成')
  auditDialog.value = false
  loadData()
}

const loadLogs = async (row) => {
  const res = await getAuditList({ materialId: row.id })
  auditLogs.value = res.data || []
  logDialog.value = true
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
</style>
