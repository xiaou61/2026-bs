<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-input v-model="query.name" placeholder="儿童姓名" clearable />
        <el-select v-model="query.adoptionStatus" placeholder="收养状态" clearable>
          <el-option :value="0" label="可申请" />
          <el-option :value="1" label="匹配中" />
          <el-option :value="2" label="已收养" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增儿童</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="childNo" label="编号" min-width="120" />
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column label="性别" min-width="80">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" min-width="80" />
        <el-table-column label="状态" min-width="100">
          <template #default="{ row }">{{ adoptionStatusMap[row.adoptionStatus] }}</template>
        </el-table-column>
        <el-table-column prop="guardianInfo" label="监护机构" min-width="150" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="visible" :title="form.id ? '编辑儿童档案' : '新增儿童档案'" width="760px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="性别"><el-select v-model="form.gender"><el-option :value="1" label="男" /><el-option :value="2" label="女" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="年龄"><el-input-number v-model="form.age" :min="1" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="收养状态"><el-select v-model="form.adoptionStatus"><el-option :value="0" label="可申请" /><el-option :value="1" label="匹配中" /><el-option :value="2" label="已收养" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="出生日期"><el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="入院日期"><el-date-picker v-model="form.admissionDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="头像链接"><el-input v-model="form.avatarUrl" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="监护机构"><el-input v-model="form.guardianInfo" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="简介"><el-input v-model="form.summary" type="textarea" :rows="3" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="健康情况"><el-input v-model="form.healthRecord.healthStatus" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="血型"><el-input v-model="form.healthRecord.bloodType" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="疫苗情况"><el-input v-model="form.healthRecord.vaccinationInfo" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="病史说明"><el-input v-model="form.healthRecord.medicalHistory" type="textarea" :rows="3" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addChild, deleteChild, getChildList, updateChild } from '../../../api'
import { adoptionStatusMap } from '../../../utils'

const list = ref([])
const total = ref(0)
const visible = ref(false)
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  adoptionStatus: null
})
const form = reactive({
  healthRecord: {}
})

const emptyForm = () => ({
  id: null,
  name: '',
  gender: 1,
  age: 1,
  adoptionStatus: 0,
  birthDate: '',
  admissionDate: '',
  avatarUrl: '',
  guardianInfo: '',
  summary: '',
  healthRecord: {
    healthStatus: '',
    bloodType: '',
    vaccinationInfo: '',
    medicalHistory: ''
  }
})

const loadData = async () => {
  const res = await getChildList(query)
  list.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const handleAdd = () => {
  Object.assign(form, emptyForm())
  visible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, JSON.parse(JSON.stringify({ ...row, healthRecord: row.healthRecord || {} })))
  visible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateChild(form)
  } else {
    await addChild(form)
  }
  ElMessage.success('保存成功')
  visible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteChild(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => {
  Object.assign(form, emptyForm())
  loadData()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
