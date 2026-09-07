<template>
  <el-card>
    <div class="bar">
      <el-select v-if="isAdmin" v-model="query.userId" placeholder="用户" style="width: 150px" clearable>
        <el-option v-for="item in users" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="待回复" :value="0" />
        <el-option label="已回复" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isUser" type="success" @click="openAdd">新增记录</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column v-if="isAdmin" prop="userName" label="用户" width="120" />
      <el-table-column prop="constitutionType" label="体质类型" width="140" />
      <el-table-column prop="symptomDesc" label="症状描述" min-width="220" show-overflow-tooltip />
      <el-table-column prop="suggestion" label="建议" min-width="220" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待回复' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column v-if="isAdmin" label="操作" width="120">
        <template #default="{ row }">
          <el-button link type="primary" @click="openReply(row)">回复</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增体质记录" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
      <el-form-item label="体质类型" prop="constitutionType"><el-input v-model="form.constitutionType" maxlength="100" /></el-form-item>
      <el-form-item label="症状描述" prop="symptomDesc"><el-input v-model="form.symptomDesc" type="textarea" :rows="4" maxlength="500" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="replyVisible" title="回复建议" width="560px">
    <el-form :model="replyForm" label-width="90px">
      <el-form-item label="建议">
        <el-input v-model="replyForm.suggestion" type="textarea" :rows="4" maxlength="500" />
      </el-form-item>
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
import { addConstitution, getConstitutionPage, getMyConstitutionPage, getUserPage, replyConstitution } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const users = ref([])
const dialogVisible = ref(false)
const replyVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, userId: null, status: null })
const form = reactive({})
const replyForm = reactive({ id: null, suggestion: '' })

const rules = {
  constitutionType: [{ required: true, message: '请输入体质类型', trigger: 'blur' }],
  symptomDesc: [{ required: true, message: '请输入症状描述', trigger: 'blur' }]
}

const loadUsers = async () => {
  if (!isAdmin.value) {
    return
  }
  const res = await getUserPage({ pageNum: 1, pageSize: 999 })
  users.value = res.data.records || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getConstitutionPage(query) : await getMyConstitutionPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { constitutionType: '', symptomDesc: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addConstitution(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const openReply = (row) => {
  Object.assign(replyForm, { id: row.id, suggestion: row.suggestion || '' })
  replyVisible.value = true
}

const submitReply = async () => {
  await replyConstitution(replyForm)
  ElMessage.success('回复成功')
  replyVisible.value = false
  loadData()
}

onMounted(async () => {
  await loadUsers()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
