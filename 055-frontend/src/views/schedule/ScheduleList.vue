<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-date-picker v-model="query.date" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 150px;" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd">新增日程</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="title" label="日程标题" />
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">
            <el-tag :type="row.category === 'work' ? 'primary' : row.category === 'meeting' ? 'success' : 'info'">
              {{ row.category === 'work' ? '工作' : row.category === 'meeting' ? '会议' : '其他' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remind" label="提醒" width="80">
          <template #default="{ row }">{{ row.remind ? '是' : '否' }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'info' : 'success'">{{ row.status === 0 ? '进行中' : '已完成' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑日程' : '新增日程'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="form.endTime" type="datetime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.category" style="width: 100%;">
            <el-option label="工作" value="work" />
            <el-option label="会议" value="meeting" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="提醒">
          <el-switch v-model="form.remind" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="进行中" :value="0" />
            <el-option label="已完成" :value="1" />
          </el-select>
        </el-form-item>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { getScheduleList, addSchedule, updateSchedule, deleteSchedule } from '../../api'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  date: ''
})

const form = reactive({
  id: null,
  title: '',
  content: '',
  startTime: '',
  endTime: '',
  category: 'work',
  remind: 0,
  status: 0
})

const rules = {
  title: [{ required: true, message: '请输入日程标题', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getScheduleList(query)
    list.value = res.data
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, title: '', content: '', startTime: '', endTime: '', category: 'work', remind: 0, status: 0 })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  resetForm()
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSchedule(form)
    ElMessage.success('修改成功')
  } else {
    await addSchedule(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该日程吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteSchedule(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.search {
  display: flex;
  gap: 10px;
}
</style>
