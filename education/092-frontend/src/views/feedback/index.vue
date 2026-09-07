<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="幼儿">
          <el-select v-model="filters.childId" placeholder="全部幼儿" clearable style="width: 220px">
            <el-option v-for="item in childOptions" :key="item.id" :label="item.childName" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isParent" type="success" @click="openAddDialog()">提交反馈</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="childName" label="幼儿姓名" min-width="120" />
        <el-table-column prop="parentName" label="家长" min-width="120" />
        <el-table-column prop="feedbackType" label="反馈类型" min-width="120" />
        <el-table-column prop="feedbackScore" label="满意度" width="90" />
        <el-table-column prop="feedbackContent" label="反馈内容" min-width="220" show-overflow-tooltip />
        <el-table-column prop="replyContent" label="教师回复" min-width="220" show-overflow-tooltip />
        <el-table-column prop="createTime" label="提交时间" min-width="170" />
        <el-table-column v-if="canReply" label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openReplyDialog(row)">回复</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="addDialogVisible" title="提交家园反馈" width="620px">
      <el-form ref="addFormRef" :model="addForm" :rules="addRules" label-width="92px">
        <el-form-item label="幼儿档案" prop="childId">
          <el-select v-model="addForm.childId" placeholder="请选择幼儿档案" style="width: 100%">
            <el-option v-for="item in childOptions" :key="item.id" :label="item.childName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="反馈类型">
          <el-select v-model="addForm.feedbackType" placeholder="请选择反馈类型" style="width: 100%">
            <el-option v-for="item in feedbackTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="满意度">
          <el-input-number v-model="addForm.feedbackScore" :min="1" :max="100" style="width: 100%" />
        </el-form-item>
        <el-form-item label="反馈内容" prop="feedbackContent">
          <el-input v-model="addForm.feedbackContent" type="textarea" :rows="5" placeholder="请输入反馈内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAdd">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="replyDialogVisible" title="回复家园反馈" width="560px">
      <el-form ref="replyFormRef" :model="replyForm" :rules="replyRules" label-width="92px">
        <el-form-item label="回复内容" prop="replyContent">
          <el-input v-model="replyForm.replyContent" type="textarea" :rows="5" placeholder="请输入回复内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReply">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addFeedback, getChildList, getFeedbackList, getMyChildren, replyFeedback } from '../../api'
import { feedbackTypeOptions, resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isParent = computed(() => userStore.role === 'parent')
const canReply = computed(() => userStore.role === 'teacher' || userStore.role === 'admin')
const childOptions = ref([])
const filters = reactive({
  childId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const addDialogVisible = ref(false)
const replyDialogVisible = ref(false)
const addFormRef = ref()
const replyFormRef = ref()
const addForm = reactive({
  childId: null,
  feedbackType: '成长反馈',
  feedbackScore: 90,
  feedbackContent: ''
})
const replyForm = reactive({
  id: null,
  replyContent: ''
})
const addRules = {
  childId: [{ required: true, message: '请选择幼儿档案', trigger: 'change' }],
  feedbackContent: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }]
}
const replyRules = {
  replyContent: [{ required: true, message: '请输入回复内容', trigger: 'blur' }]
}

const loadChildOptions = async () => {
  const res = isParent.value ? await getMyChildren() : await getChildList({ pageNum: 1, pageSize: 200 })
  childOptions.value = Array.isArray(res.data) ? res.data : (res.data?.list || [])
}

const loadData = async () => {
  const res = await getFeedbackList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const openAddDialog = () => {
  addForm.childId = null
  addForm.feedbackType = '成长反馈'
  addForm.feedbackScore = 90
  addForm.feedbackContent = ''
  addDialogVisible.value = true
}

const openReplyDialog = (row) => {
  replyForm.id = row.id
  replyForm.replyContent = row.replyContent || ''
  replyDialogVisible.value = true
}

const submitAdd = async () => {
  await addFormRef.value.validate()
  await addFeedback(addForm)
  ElMessage.success('家园反馈提交成功')
  addDialogVisible.value = false
  loadData()
}

const submitReply = async () => {
  await replyFormRef.value.validate()
  await replyFeedback(replyForm)
  ElMessage.success('反馈回复成功')
  replyDialogVisible.value = false
  loadData()
}

const resetFilters = () => {
  filters.childId = undefined
  pageNum.value = 1
  loadData()
}

onMounted(async () => {
  await loadChildOptions()
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar,
.toolbar-actions,
.pagination {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
