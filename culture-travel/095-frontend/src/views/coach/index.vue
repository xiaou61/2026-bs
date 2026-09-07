<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.teamId" placeholder="球队" clearable>
          <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
        </el-select>
        <el-input v-model="query.name" placeholder="教练姓名" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增教练</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="coachNo" label="教练编号" min-width="150" />
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="teamName" label="所属球队" min-width="140" />
        <el-table-column prop="nationality" label="国籍" min-width="100" />
        <el-table-column prop="age" label="年龄" min-width="80" />
        <el-table-column prop="formation" label="常用阵型" min-width="120" />
        <el-table-column prop="tenureStart" label="执教开始" min-width="120" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑教练' : '新增教练'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="教练编号">
          <el-input v-model="form.coachNo" />
        </el-form-item>
        <el-form-item label="所属球队" prop="teamId">
          <el-select v-model="form.teamId" style="width: 100%">
            <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="国籍">
          <el-input v-model="form.nationality" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="form.age" :min="18" :max="80" style="width: 100%" />
        </el-form-item>
        <el-form-item label="阵型">
          <el-input v-model="form.formation" />
        </el-form-item>
        <el-form-item label="执教开始">
          <el-date-picker v-model="form.tenureStart" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteCoach, getCoachList, getTeamOptions, saveCoach } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const teamOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, teamId: null, name: '' })
const form = reactive({ id: null, coachNo: '', teamId: null, name: '', nationality: '', age: 40, formation: '', tenureStart: '', phone: '', status: 1 })
const rules = {
  teamId: [{ required: true, message: '请选择球队', trigger: 'change' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const loadOptions = async () => {
  const res = await getTeamOptions({})
  teamOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCoachList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, coachNo: '', teamId: null, name: '', nationality: '', age: 40, formation: '', tenureStart: '', phone: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveCoach(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteCoach(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
