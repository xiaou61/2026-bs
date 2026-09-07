<template>
  <div class="portal-container application-page">
    <el-row :gutter="18">
      <el-col :lg="10" :span="24">
        <el-card shadow="never">
          <template #header>
            <div class="page-title">提交收养申请</div>
          </template>
          <el-form :model="form" label-width="100px">
            <el-form-item label="选择儿童">
              <el-select v-model="form.childId" placeholder="请选择儿童">
                <el-option v-for="item in childOptions" :key="item.id" :label="`${item.name} / ${item.age}岁`" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="期望联系">
              <el-date-picker v-model="form.expectedContactDate" type="date" value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="申请理由">
              <el-input v-model="form.reason" type="textarea" :rows="5" />
            </el-form-item>
            <el-button type="primary" @click="handleSubmit">提交申请</el-button>
          </el-form>
        </el-card>
      </el-col>
      <el-col :lg="14" :span="24">
        <el-card shadow="never">
          <template #header>
            <div class="page-title">我的申请记录</div>
          </template>
          <el-table :data="list">
            <el-table-column prop="applicationNo" label="申请编号" min-width="160" />
            <el-table-column prop="childName" label="儿童" min-width="110" />
            <el-table-column prop="expectedContactDate" label="期望联系" min-width="120" />
            <el-table-column label="状态" min-width="120">
              <template #default="{ row }">{{ applicationStatusMap[row.status] }}</template>
            </el-table-column>
            <el-table-column label="材料">
              <template #default="{ row }">
                <el-button link @click="openMaterial(row)">材料管理</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="materialVisible" title="申请材料" width="640px">
      <div class="material-toolbar">
        <el-input v-model="materialForm.materialName" placeholder="材料名称" />
        <el-input v-model="materialForm.materialType" placeholder="材料类型" />
        <el-input v-model="materialForm.fileUrl" placeholder="材料链接" />
        <el-button type="primary" @click="handleAddMaterial">新增</el-button>
      </div>
      <el-table :data="materials">
        <el-table-column prop="materialName" label="材料名称" />
        <el-table-column prop="materialType" label="类型" />
        <el-table-column prop="fileUrl" label="链接" show-overflow-tooltip />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addApplication, addMaterial, getMaterialList, getMyApplicationList, getPublicChildList } from '../../api'
import { applicationStatusMap } from '../../utils'

const childOptions = ref([])
const list = ref([])
const materials = ref([])
const materialVisible = ref(false)
const currentApplicationId = ref(null)
const form = reactive({
  childId: null,
  expectedContactDate: '',
  reason: ''
})
const materialForm = reactive({
  materialName: '',
  materialType: '',
  fileUrl: ''
})

const loadChildren = async () => {
  const res = await getPublicChildList({ pageNum: 1, pageSize: 100 })
  childOptions.value = res.data?.list || []
}

const loadApplications = async () => {
  const res = await getMyApplicationList({ pageNum: 1, pageSize: 20 })
  list.value = res.data?.list || []
}

const handleSubmit = async () => {
  await addApplication(form)
  ElMessage.success('申请已提交')
  form.childId = null
  form.expectedContactDate = ''
  form.reason = ''
  loadApplications()
}

const openMaterial = async (row) => {
  currentApplicationId.value = row.id
  materialVisible.value = true
  const res = await getMaterialList({ applicationId: row.id })
  materials.value = res.data || []
}

const handleAddMaterial = async () => {
  await addMaterial({
    applicationId: currentApplicationId.value,
    ...materialForm
  })
  ElMessage.success('材料已新增')
  Object.assign(materialForm, { materialName: '', materialType: '', fileUrl: '' })
  openMaterial({ id: currentApplicationId.value })
}

onMounted(() => {
  loadChildren()
  loadApplications()
})
</script>

<style scoped>
.application-page {
  padding-top: 28px;
}

.page-title {
  font-size: 22px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.material-toolbar {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  margin-bottom: 16px;
}

@media (max-width: 900px) {
  .material-toolbar {
    grid-template-columns: 1fr;
  }
}
</style>
