<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-select v-model="query.status" placeholder="状态" style="width:130px" clearable v-if="!isUser"><el-option label="待回复" :value="0" /><el-option label="已回复" :value="1" /></el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isUser" type="success" @click="dialogVisible = true">提交反馈</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column v-if="!isUser" prop="username" label="用户" width="100" />
      <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag size="small">{{ { 1: '建议', 2: '投诉', 3: '其他' }[row.type] }}</el-tag></template></el-table-column>
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="reply" label="回复" />
      <el-table-column label="状态" width="90"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'" size="small">{{ row.status === 1 ? '已回复' : '待回复' }}</el-tag></template></el-table-column>
      <el-table-column prop="createTime" label="时间" width="170" />
      <el-table-column v-if="!isUser" label="操作" width="100"><template #default="{ row }"><el-button v-if="row.status === 0" link type="primary" @click="openReply(row)">回复</el-button></template></el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
    <el-dialog v-model="dialogVisible" title="提交反馈" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="类型"><el-select v-model="form.type" style="width:100%"><el-option label="建议" :value="1" /><el-option label="投诉" :value="2" /><el-option label="其他" :value="3" /></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">提交</el-button></template>
    </el-dialog>
    <el-dialog v-model="replyVisible" title="回复反馈" width="500px">
      <el-form label-width="80px">
        <el-form-item label="回复内容"><el-input v-model="replyContent" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="replyVisible = false">取消</el-button><el-button type="primary" @click="submitReply">提交</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { addFeedback, getFeedbackList, getMyFeedbacks, replyFeedback } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isUser = computed(() => userStore.user?.role === 'user')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const replyVisible = ref(false)
const replyId = ref(null)
const replyContent = ref('')
const query = reactive({ pageNum: 1, pageSize: 10, status: null })
const form = reactive({ type: 1, content: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = isUser.value ? await getMyFeedbacks(query) : await getFeedbackList(query)
    tableData.value = res.data.list; total.value = res.data.total
  } finally { loading.value = false }
}

const handleSubmit = async () => { await addFeedback(form); ElMessage.success('提交成功'); dialogVisible.value = false; form.content = ''; loadData() }
const openReply = (row) => { replyId.value = row.id; replyContent.value = ''; replyVisible.value = true }
const submitReply = async () => { await replyFeedback(replyId.value, { reply: replyContent.value }); ElMessage.success('回复成功'); replyVisible.value = false; loadData() }
onMounted(loadData)
</script>
