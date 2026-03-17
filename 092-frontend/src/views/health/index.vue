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
        <el-button v-if="canEdit" type="success" @click="openDialog()">新增晨检</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="childName" label="幼儿姓名" min-width="120" />
        <el-table-column prop="recordDate" label="晨检日期" min-width="120" />
        <el-table-column prop="temperature" label="体温" width="90" />
        <el-table-column prop="healthStatus" label="健康状态" min-width="120" />
        <el-table-column prop="emotionStatus" label="精神状态" min-width="120" />
        <el-table-column prop="attendanceAdvice" label="入园建议" min-width="140" />
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
        <el-table-column prop="teacherName" label="登记教师" min-width="120" />
        <el-table-column v-if="canEdit" label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑晨检记录' : '新增晨检记录'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="幼儿档案" prop="childId">
          <el-select v-model="form.childId" placeholder="请选择幼儿档案" style="width: 100%">
            <el-option v-for="item in childOptions" :key="item.id" :label="item.childName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="晨检日期" prop="recordDate">
          <el-date-picker v-model="form.recordDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="体温">
          <el-input-number v-model="form.temperature" :min="35" :max="42" :step="0.1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="健康状态">
          <el-input v-model="form.healthStatus" placeholder="如：正常、轻微咳嗽" />
        </el-form-item>
        <el-form-item label="精神状态">
          <el-input v-model="form.emotionStatus" placeholder="如：活跃、平稳" />
        </el-form-item>
        <el-form-item label="入园建议">
          <el-input v-model="form.attendanceAdvice" placeholder="如：可正常入园、建议观察" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getChildList, getHealthList, getMyChildren, saveHealth } from '../../api'
import { resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const canEdit = computed(() => userStore.role === 'teacher' || userStore.role === 'admin')
const childOptions = ref([])
const filters = reactive({
  childId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  childId: null,
  recordDate: '',
  temperature: 36.5,
  healthStatus: '正常',
  emotionStatus: '平稳',
  attendanceAdvice: '可正常入园',
  remark: ''
})
const rules = {
  childId: [{ required: true, message: '请选择幼儿档案', trigger: 'change' }],
  recordDate: [{ required: true, message: '请选择晨检日期', trigger: 'change' }]
}

const loadChildOptions = async () => {
  const res = userStore.role === 'parent'
    ? await getMyChildren()
    : await getChildList({ pageNum: 1, pageSize: 200 })
  childOptions.value = Array.isArray(res.data) ? res.data : (res.data?.list || [])
}

const loadData = async () => {
  const res = await getHealthList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    childId: null,
    recordDate: '',
    temperature: 36.5,
    healthStatus: '正常',
    emotionStatus: '平稳',
    attendanceAdvice: '可正常入园',
    remark: ''
  })
}

const openDialog = (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await saveHealth(form)
  ElMessage.success('晨检记录保存成功')
  dialogVisible.value = false
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
