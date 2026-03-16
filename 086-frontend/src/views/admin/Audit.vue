<template>
  <el-card>
    <div class="toolbar">
      <el-input v-model="query.title" placeholder="壁纸标题" style="width: 220px" clearable />
      <el-select v-model="query.auditStatus" placeholder="审核状态" style="width: 160px" clearable>
        <el-option label="待审核" :value="0" />
        <el-option label="已通过" :value="1" />
        <el-option label="已驳回" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column label="预览" width="120">
        <template #default="{ row }"><img :src="row.coverUrl || row.imageUrl" class="thumb"></template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="authorName" label="作者" width="140" />
      <el-table-column prop="auditStatus" label="审核状态" width="110">
        <template #default="{ row }">
          <el-tag :type="auditType(row.auditStatus)">{{ auditText(row.auditStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button text type="success" @click="openAudit(row, 1)">通过</el-button>
          <el-button text type="danger" @click="openAudit(row, 2)">驳回</el-button>
          <el-button text type="primary" @click="loadRecord(row.id)">记录</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pager">
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="loadData"
      />
    </div>

    <el-dialog v-model="dialogVisible" title="审核壁纸" width="520px">
      <el-form :model="auditForm" label-width="90px">
        <el-form-item label="壁纸标题">
          <el-input v-model="auditForm.title" disabled />
        </el-form-item>
        <el-form-item label="审核结果">
          <el-tag :type="auditForm.auditStatus === 1 ? 'success' : 'danger'">
            {{ auditForm.auditStatus === 1 ? '通过' : '驳回' }}
          </el-tag>
        </el-form-item>
        <el-form-item label="审核说明">
          <el-input v-model="auditForm.auditRemark" type="textarea" :rows="4" placeholder="请输入审核说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="recordVisible" title="审核记录" width="620px">
      <el-table :data="recordList">
        <el-table-column prop="auditStatus" label="结果" width="100">
          <template #default="{ row }">
            <el-tag :type="auditType(row.auditStatus)">{{ auditText(row.auditStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="auditRemark" label="说明" min-width="220" />
        <el-table-column prop="auditTime" label="时间" min-width="180" />
      </el-table>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getAuditList, getAuditRecord, submitAudit } from '../../api'

const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const recordVisible = ref(false)
const recordList = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, title: '', auditStatus: 0 })
const auditForm = reactive({ wallpaperId: null, title: '', auditStatus: 1, auditRemark: '' })

const auditText = (status) => (status === 1 ? '已通过' : status === 2 ? '已驳回' : '待审核')
const auditType = (status) => (status === 1 ? 'success' : status === 2 ? 'danger' : 'warning')

const loadData = async () => {
  const res = await getAuditList(query)
  tableData.value = res.data.records || []
  total.value = res.data.total || 0
}

const openAudit = (row, status) => {
  auditForm.wallpaperId = row.id
  auditForm.title = row.title
  auditForm.auditStatus = status
  auditForm.auditRemark = status === 1 ? '审核通过' : ''
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await submitAudit({
    wallpaperId: auditForm.wallpaperId,
    auditStatus: auditForm.auditStatus,
    auditRemark: auditForm.auditRemark
  })
  ElMessage.success('审核成功')
  dialogVisible.value = false
  loadData()
}

const loadRecord = async (id) => {
  const res = await getAuditRecord(id)
  recordList.value = res.data || []
  recordVisible.value = true
}

onMounted(loadData)
</script>

<style scoped>
.toolbar { display: flex; gap: 12px; margin-bottom: 16px; }
.pager { display: flex; justify-content: center; margin-top: 18px; }
.thumb { width: 80px; height: 48px; object-fit: cover; border-radius: 8px; }
</style>
