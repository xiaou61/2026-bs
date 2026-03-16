<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="资源标题">
          <el-input v-model="filters.keyword" placeholder="请输入资源标题" clearable />
        </el-form-item>
        <el-form-item label="所属排期">
          <el-select v-model="filters.performanceId" placeholder="全部排期" clearable style="width: 260px">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="canEdit" type="success" @click="openDialog()">新增资源</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="title" label="资源标题" min-width="180" />
        <el-table-column prop="repertoireName" label="剧目名称" min-width="150" />
        <el-table-column prop="artistName" label="艺术家" min-width="110" />
        <el-table-column label="资源类型" width="100">
          <template #default="{ row }">
            {{ getOptionLabel(resourceTypeOptions, row.resourceType) }}
          </template>
        </el-table-column>
        <el-table-column label="资源链接" min-width="220">
          <template #default="{ row }">
            <a :href="row.resourceUrl" target="_blank" class="link">{{ row.resourceUrl }}</a>
          </template>
        </el-table-column>
        <el-table-column prop="contentDesc" label="资源说明" min-width="220" show-overflow-tooltip />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="canEdit" label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资源' : '新增资源'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="所属排期" prop="performanceId">
          <el-select v-model="form.performanceId" placeholder="请选择排期" style="width: 100%" @change="handleScheduleChange">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="资源标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入资源标题" />
        </el-form-item>
        <el-form-item label="资源类型">
          <el-select v-model="form.resourceType" placeholder="请选择资源类型" style="width: 100%">
            <el-option v-for="item in resourceTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="资源链接">
          <el-input v-model="form.resourceUrl" placeholder="请输入资源链接" />
        </el-form-item>
        <el-form-item label="资源说明">
          <el-input v-model="form.contentDesc" type="textarea" :rows="4" placeholder="请输入资源说明" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { addResource, deleteResource, getResourceList, getScheduleList, getMemberSchedule, getArtistSchedule, updateResource } from '../../api'
import { getOptionLabel, resourceTypeOptions, resolvePage, statusOptions } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const canEdit = computed(() => userStore.role === 'artist' || userStore.role === 'admin')

const filters = reactive({
  keyword: '',
  performanceId: undefined
})
const performanceOptions = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  performanceId: null,
  repertoireId: null,
  title: '',
  resourceType: 'ppt',
  resourceUrl: '',
  contentDesc: '',
  status: 1
})
const rules = {
  performanceId: [{ required: true, message: '请选择所属排期', trigger: 'change' }],
  title: [{ required: true, message: '请输入资源标题', trigger: 'blur' }]
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    performanceId: null,
    repertoireId: null,
    title: '',
    resourceType: 'ppt',
    resourceUrl: '',
    contentDesc: '',
    status: 1
  })
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'admin') {
    const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
    performanceOptions.value = res.data?.list || []
    return
  }
  const res = userStore.role === 'artist' ? await getArtistSchedule() : await getMemberSchedule()
  performanceOptions.value = res.data || []
}

const loadData = async () => {
  const res = await getResourceList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const handleScheduleChange = (performanceId) => {
  const current = performanceOptions.value.find((item) => item.id === performanceId)
  form.repertoireId = current?.repertoireId || null
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
  if (form.id) {
    await updateResource(form)
    ElMessage.success('资源更新成功')
  } else {
    await addResource(form)
    ElMessage.success('资源新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteResource(id)
  ElMessage.success('删除成功')
  loadData()
}

const resetFilters = () => {
  filters.keyword = ''
  filters.performanceId = undefined
  pageNum.value = 1
  loadData()
}

onMounted(async () => {
  await loadScheduleOptions()
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.toolbar-actions,
.pagination {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.pagination {
  margin-top: 16px;
}

.link {
  color: var(--accent);
  text-decoration: none;
}
</style>


