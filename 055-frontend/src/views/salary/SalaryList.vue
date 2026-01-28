<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-date-picker v-model="query.yearMonth" type="month" placeholder="选择月份" format="YYYY-MM" value-format="YYYY-MM" style="width: 150px;" />
          <el-input v-model="query.keyword" placeholder="员工姓名" clearable style="width: 150px;" v-if="!isEmployee" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd" v-if="!isEmployee">新增薪资</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="realName" label="员工" width="100" v-if="!isEmployee" />
        <el-table-column prop="yearMonth" label="月份" width="100" />
        <el-table-column prop="basicSalary" label="基本工资" width="100" />
        <el-table-column prop="bonus" label="奖金" width="100" />
        <el-table-column prop="allowance" label="补贴" width="100" />
        <el-table-column prop="deduction" label="扣款" width="100" />
        <el-table-column prop="socialSecurity" label="社保" width="100" />
        <el-table-column prop="actualSalary" label="实发工资" width="120">
          <template #default="{ row }">
            <span style="color: #409EFF; font-weight: bold;">{{ row.actualSalary }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发放' : '未发放' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" v-if="!isEmployee">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑薪资' : '新增薪资'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="员工" prop="userId" v-if="!form.id">
          <el-select v-model="form.userId" placeholder="选择员工" style="width: 100%;">
            <el-option v-for="u in userList" :key="u.id" :label="u.realName" :value="u.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="月份" prop="yearMonth" v-if="!form.id">
          <el-date-picker v-model="form.yearMonth" type="month" format="YYYY-MM" value-format="YYYY-MM" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="基本工资" prop="basicSalary">
          <el-input-number v-model="form.basicSalary" :min="0" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="奖金">
          <el-input-number v-model="form.bonus" :min="0" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="补贴">
          <el-input-number v-model="form.allowance" :min="0" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="扣款">
          <el-input-number v-model="form.deduction" :min="0" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="社保">
          <el-input-number v-model="form.socialSecurity" :min="0" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="未发放" :value="0" />
            <el-option label="已发放" :value="1" />
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getSalaryList, getMySalary, addSalary, updateSalary, deleteSalary, getAllUsers } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isEmployee = computed(() => userStore.isEmployee)
const loading = ref(false)
const list = ref([])
const total = ref(0)
const userList = ref([])
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  yearMonth: '',
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  userId: null,
  yearMonth: '',
  basicSalary: 0,
  bonus: 0,
  allowance: 0,
  deduction: 0,
  socialSecurity: 0,
  status: 0
})

const rules = {
  userId: [{ required: true, message: '请选择员工', trigger: 'change' }],
  yearMonth: [{ required: true, message: '请选择月份', trigger: 'change' }],
  basicSalary: [{ required: true, message: '请输入基本工资', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const api = isEmployee.value ? getMySalary : getSalaryList
    const res = await api(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadUsers = async () => {
  const res = await getAllUsers()
  userList.value = res.data
}

const resetForm = () => {
  Object.assign(form, { id: null, userId: null, yearMonth: '', basicSalary: 0, bonus: 0, allowance: 0, deduction: 0, socialSecurity: 0, status: 0 })
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
    await updateSalary(form)
    ElMessage.success('修改成功')
  } else {
    await addSalary(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该薪资记录吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteSalary(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
  if (!isEmployee.value) {
    loadUsers()
  }
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
