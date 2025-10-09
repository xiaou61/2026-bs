<template>
  <div class="activity-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>活动中心</span>
          <el-button
            v-if="userInfo?.role !== 'student'"
            type="primary"
            @click="showAddDialog = true"
          >
            <el-icon><Plus /></el-icon>
            发布活动
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option label="未开始" value="未开始" />
            <el-option label="进行中" value="进行中" />
            <el-option label="已结束" value="已结束" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="searchForm.title" placeholder="请输入标题" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchActivityList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="活动标题" min-width="200">
          <template #default="{ row }">
            <el-link type="primary" @click="toDetail(row.id)">{{ row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="地点" width="150" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column prop="maxParticipants" label="限制人数" width="100" />
        <el-table-column prop="currentParticipants" label="已报名" width="100">
          <template #default="{ row }">
            <el-text type="primary">{{ row.currentParticipants || 0 }}</el-text>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === '未开始'" type="info">{{ row.status }}</el-tag>
            <el-tag v-else-if="row.status === '进行中'" type="success">{{ row.status }}</el-tag>
            <el-tag v-else type="warning">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          v-if="userInfo?.role !== 'student'"
          label="操作"
          width="200"
          fixed="right"
        >
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="fetchActivityList"
        @current-change="fetchActivityList"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- 添加/编辑活动对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingActivity ? '编辑活动' : '发布活动'"
      width="700px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="activityForm"
        :rules="activityRules"
        label-width="100px"
      >
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="activityForm.title" placeholder="请输入活动标题" />
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="activityForm.location" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
          <el-input
            v-model="activityForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入活动描述"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="activityForm.startTime"
            type="datetime"
            placeholder="选择开始时间"
            style="width: 100%"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="activityForm.endTime"
            type="datetime"
            placeholder="选择结束时间"
            style="width: 100%"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="限制人数" prop="maxParticipants">
          <el-input-number
            v-model="activityForm.maxParticipants"
            :min="1"
            :max="10000"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="活动状态" prop="status">
          <el-select v-model="activityForm.status" placeholder="请选择" style="width: 100%">
            <el-option label="未开始" value="未开始" />
            <el-option label="进行中" value="进行中" />
            <el-option label="已结束" value="已结束" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          {{ editingActivity ? '更新' : '发布' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getActivityList, addActivity, updateActivity, deleteActivity } from '@/api/activity'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showAddDialog = ref(false)
const formRef = ref(null)
const editingActivity = ref(null)

const searchForm = reactive({
  status: '',
  title: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const activityForm = reactive({
  title: '',
  location: '',
  description: '',
  startTime: '',
  endTime: '',
  maxParticipants: 100,
  status: '未开始'
})

const activityRules = {
  title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
  location: [{ required: true, message: '请输入活动地点', trigger: 'blur' }],
  description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }],
  maxParticipants: [{ required: true, message: '请输入限制人数', trigger: 'blur' }],
  status: [{ required: true, message: '请选择活动状态', trigger: 'change' }]
}

// 获取活动列表
const fetchActivityList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getActivityList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取活动列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.status = ''
  searchForm.title = ''
  pagination.current = 1
  fetchActivityList()
}

// 提交活动
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        let res
        if (editingActivity.value) {
          res = await updateActivity({
            id: editingActivity.value.id,
            ...activityForm
          })
        } else {
          res = await addActivity(activityForm)
        }
        
        if (res.code === 200) {
          ElMessage.success(editingActivity.value ? '更新成功' : '发布成功')
          showAddDialog.value = false
          fetchActivityList()
        }
      } catch (error) {
        console.error('操作失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 编辑活动
const handleEdit = (row) => {
  editingActivity.value = row
  Object.assign(activityForm, {
    title: row.title,
    location: row.location,
    description: row.description,
    startTime: row.startTime,
    endTime: row.endTime,
    maxParticipants: row.maxParticipants,
    status: row.status
  })
  showAddDialog.value = true
}

// 删除活动
const handleDelete = async (id) => {
  ElMessageBox.confirm('确定删除该活动吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await deleteActivity(id)
      if (res.code === 200) {
        ElMessage.success('删除成功')
        fetchActivityList()
      }
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

// 查看详情
const toDetail = (id) => {
  router.push(`/activity/${id}`)
}

// 重置表单
const resetForm = () => {
  editingActivity.value = null
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

onMounted(() => {
  fetchActivityList()
})
</script>

<style scoped>
.activity-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>

