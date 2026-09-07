<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.alertNo" placeholder="预警号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已回复" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="alertNo" label="预警号" min-width="180" />
      <el-table-column prop="userName" label="申诉人" width="120" />
      <el-table-column prop="content" label="申诉内容" min-width="260" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待处理' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="replyAdminName" label="回复人" width="120" />
      <el-table-column prop="reply" label="回复内容" min-width="220" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button link @click="openReply(row)" :disabled="row.status === 1">回复</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="回复申诉" width="560px">
    <el-form :model="form" label-width="90px">
      <el-form-item label="回复内容"><el-input v-model="form.reply" type="textarea" :rows="4" maxlength="1000" show-word-limit /></el-form-item>
      <el-form-item label="预警状态"><el-select v-model="form.alertStatus" style="width: 100%"><el-option label="确认欺诈" :value="2" /><el-option label="误报放行" :value="3" /></el-select></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReply">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getAppealPage, replyAppeal } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, status: null, alertNo: '' })
const form = reactive({ id: null, reply: '', alertStatus: 3 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAppealPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openReply = (row) => {
  Object.assign(form, { id: row.id, reply: row.reply || '', alertStatus: 3 })
  dialogVisible.value = true
}

const submitReply = async () => {
  if (!form.reply) {
    ElMessage.warning('请输入回复内容')
    return
  }
  await replyAppeal(form)
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
