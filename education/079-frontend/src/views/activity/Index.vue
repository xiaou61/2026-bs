<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="活动名称" style="width: 200px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="报名中" :value="0" />
          <el-option label="进行中" :value="1" />
          <el-option label="已结束" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()" v-if="userStore.isAdmin()">发布活动</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="活动名称" />
        <el-table-column prop="address" label="地点" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column label="人数" width="100">
          <template #default="{ row }">{{ row.currentCount }}/{{ row.maxCount }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : row.status === 1 ? 'warning' : 'info'">
              {{ row.status === 0 ? '报名中' : row.status === 1 ? '进行中' : '已结束' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/activity/${row.id}`)">详情</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)" v-if="userStore.isAdmin()">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="发布活动" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动名称"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="活动地点"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" /></el-form-item>
        <el-form-item label="报名截止"><el-date-picker v-model="form.signDeadline" type="datetime" /></el-form-item>
        <el-form-item label="最大人数"><el-input-number v-model="form.maxCount" :min="1" /></el-form-item>
        <el-form-item label="活动内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getActivityList, addActivity, deleteActivity } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const form = reactive({ title: '', content: '', address: '', startTime: '', endTime: '', signDeadline: '', maxCount: 100 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getActivityList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openDialog = () => {
  Object.assign(form, { title: '', content: '', address: '', startTime: '', endTime: '', signDeadline: '', maxCount: 100 })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addActivity(form)
  ElMessage.success('发布成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteActivity(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
