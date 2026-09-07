<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="场馆名称">
          <el-input v-model="filters.name" placeholder="请输入场馆名称" clearable />
        </el-form-item>
        <el-form-item label="所属名家档案">
          <el-select v-model="filters.artistId" placeholder="全部名家档案" clearable style="width: 180px">
            <el-option v-for="item in artistOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部状态" clearable style="width: 140px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">新增场馆</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="name" label="场馆名称" min-width="160" />
        <el-table-column label="所属名家档案" min-width="160">
          <template #default="{ row }">
            {{ artistMap[row.artistId] || row.artistId }}
          </template>
        </el-table-column>
        <el-table-column prop="counselorName" label="辅导员" min-width="120" />
        <el-table-column prop="memberCount" label="人数" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="170" />
        <el-table-column label="操作" width="180" fixed="right">
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑场馆' : '新增场馆'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="所属名家档案" prop="artistId">
          <el-select v-model="form.artistId" placeholder="请选择名家档案" style="width: 100%">
            <el-option v-for="item in artistOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="场馆名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入场馆名称" />
        </el-form-item>
        <el-form-item label="辅导员">
          <el-input v-model="form.counselorName" placeholder="请输入辅导员姓名" />
        </el-form-item>
        <el-form-item label="场馆人数">
          <el-input-number v-model="form.memberCount" :min="0" :max="999" style="width: 100%" />
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
import { addClass, deleteClass, getClassList, getMajorEnabled, updateClass } from '../../api'
import { buildNameMap, getOptionLabel, resolvePage, statusOptions } from '../../utils'

const artistOptions = ref([])
const filters = reactive({
  name: '',
  artistId: undefined,
  status: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  artistId: null,
  name: '',
  counselorName: '',
  memberCount: 0,
  status: 1
})
const rules = {
  artistId: [{ required: true, message: '请选择所属名家档案', trigger: 'change' }],
  name: [{ required: true, message: '请输入场馆名称', trigger: 'blur' }]
}
const artistMap = computed(() => buildNameMap(artistOptions.value))

const resetForm = () => {
  Object.assign(form, { id: null, artistId: null, name: '', counselorName: '', memberCount: 0, status: 1 })
}

const loadOptions = async () => {
  const res = await getMajorEnabled()
  artistOptions.value = res.data || []
}

const loadData = async () => {
  const res = await getClassList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const resetFilters = () => {
  filters.name = ''
  filters.artistId = undefined
  filters.status = undefined
  pageNum.value = 1
  loadData()
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
    await updateClass(form)
    ElMessage.success('场馆更新成功')
  } else {
    await addClass(form)
    ElMessage.success('场馆新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteClass(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
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
</style>


