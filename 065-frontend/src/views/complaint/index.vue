<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="投诉单号" style="width: 200px" clearable />
      <el-select v-if="isStaffRole" v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已处理" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isOwner" type="success" @click="openAdd">新增投诉</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="投诉单号" min-width="190" />
      <el-table-column v-if="isStaffRole" prop="ownerName" label="业主" width="120" />
      <el-table-column prop="title" label="标题" min-width="160" />
      <el-table-column prop="content" label="内容" min-width="220" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已处理' : '待处理' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="reply" label="回复" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isStaffRole && row.status === 0" link type="primary" @click="openReply(row)">回复</el-button>
          <el-popconfirm v-if="isOwner && row.status === 0" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增投诉建议" width="580px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="120" /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="4" maxlength="500" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="replyVisible" title="回复投诉" width="520px">
    <el-form :model="replyForm" label-width="90px">
      <el-form-item label="回复内容"><el-input v-model="replyForm.reply" type="textarea" :rows="4" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="replyVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReply">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addComplaint, deleteComplaint, getComplaintPage, getMyComplaintPage, getOwnerList, replyComplaint } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStaff = computed(() => userStore.user?.role === 'STAFF')
const isOwner = computed(() => userStore.user?.role === 'OWNER')
const isStaffRole = computed(() => isAdmin.value || isStaff.value)
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const owners = ref([])
const dialogVisible = ref(false)
const replyVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', ownerId: null, status: null })
const form = reactive({})
const replyForm = reactive({ id: null, reply: '' })

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadBase = async () => {
  if (!isStaffRole.value) {
    return
  }
  const res = await getOwnerList()
  owners.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaffRole.value ? await getComplaintPage(query) : await getMyComplaintPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { title: '', content: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addComplaint(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const openReply = (row) => {
  Object.assign(replyForm, { id: row.id, reply: row.reply || '' })
  replyVisible.value = true
}

const submitReply = async () => {
  if (!String(replyForm.reply || '').trim()) {
    ElMessage.warning('请填写回复内容')
    return
  }
  await replyComplaint(replyForm)
  ElMessage.success('回复成功')
  replyVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteComplaint(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
