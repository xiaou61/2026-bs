<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.buildingId" placeholder="楼栋" style="width: 160px" clearable>
        <el-option v-for="item in buildings" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-input v-model="query.keyword" placeholder="单元/门牌号" style="width: 180px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增房屋</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="buildingName" label="楼栋" width="120" />
      <el-table-column prop="unitNo" label="单元" width="100" />
      <el-table-column prop="roomNo" label="门牌号" width="110" />
      <el-table-column prop="area" label="面积(㎡)" width="100" />
      <el-table-column prop="ownerName" label="业主" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑房屋' : '新增房屋'" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="楼栋" prop="buildingId">
        <el-select v-model="form.buildingId" style="width: 100%">
          <el-option v-for="item in buildings" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="单元" prop="unitNo"><el-input v-model="form.unitNo" maxlength="20" /></el-form-item>
      <el-form-item label="门牌号" prop="roomNo"><el-input v-model="form.roomNo" maxlength="20" /></el-form-item>
      <el-form-item label="面积"><el-input-number v-model="form.area" :min="0" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="业主">
        <el-select v-model="form.ownerId" clearable style="width: 100%">
          <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addHouse, deleteHouse, getBuildingList, getHousePage, getOwnerList, updateHouse } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const buildings = ref([])
const owners = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, buildingId: null, ownerId: null, status: null, keyword: '' })
const form = reactive({})

const rules = {
  buildingId: [{ required: true, message: '请选择楼栋', trigger: 'change' }],
  unitNo: [{ required: true, message: '请输入单元', trigger: 'blur' }],
  roomNo: [{ required: true, message: '请输入门牌号', trigger: 'blur' }]
}

const loadBase = async () => {
  const [bRes, oRes] = await Promise.all([getBuildingList(), getOwnerList()])
  buildings.value = bRes.data || []
  owners.value = oRes.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getHousePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, buildingId: null, unitNo: '', roomNo: '', area: 0, ownerId: null, status: 1 })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadBase()
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateHouse(form)
  } else {
    await addHouse(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteHouse(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
