<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="处理单号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已回复" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="orderNo" label="处理单号" min-width="180" />
      <el-table-column prop="userName" label="申诉人" width="120" />
      <el-table-column prop="targetUserName" label="被申诉方" width="120" />
      <el-table-column prop="serviceTitle" label="企业信息" min-width="160" />
      <el-table-column prop="type" label="类型" width="100" />
      <el-table-column prop="content" label="申诉内容" min-width="240" />
      <el-table-column label="图片" width="120">
        <template #default="{ row }">
          <el-image v-if="row.images" :src="row.images" style="width: 50px; height: 50px; border-radius: 4px" :preview-src-list="[row.images]" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待处理' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="reply" label="回复" min-width="220" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button link type="primary" @click="openReply(row)" :disabled="row.status === 1">回复</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="回复申诉" width="520px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="70px">
      <el-form-item label="回复" prop="reply"><el-input v-model="form.reply" type="textarea" :rows="4" maxlength="1000" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReply">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getComplaintPage, replyComplaint } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({ id: null, reply: '' })
const query = reactive({ pageNum: 1, pageSize: 10, status: null, orderNo: '' })
const rules = {
  reply: [
    { required: true, message: '请输入回复内容', trigger: 'blur' },
    { max: 1000, message: '回复内容不能超过1000字符', trigger: 'blur' }
  ]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getComplaintPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openReply = (row) => {
  Object.assign(form, { id: row.id, reply: row.reply || '' })
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

const submitReply = async () => {
  await formRef.value.validate()
  await replyComplaint({ id: form.id, reply: form.reply })
  ElMessage.success('回复成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>


