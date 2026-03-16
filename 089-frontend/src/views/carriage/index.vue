<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.templateName" placeholder="模板名称" clearable style="width: 180px" />
        <el-input v-model="query.seatType" placeholder="座席类型" clearable style="width: 160px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option :value="1" label="启用" />
          <el-option :value="0" label="停用" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增模板</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="templateName" label="模板名称" min-width="140" />
        <el-table-column prop="seatType" label="座席类型" width="120" />
        <el-table-column prop="coachCount" label="车厢数" width="90" />
        <el-table-column prop="seatRows" label="排数" width="90" />
        <el-table-column prop="seatCols" label="列数" width="90" />
        <el-table-column prop="priceFactor" label="价格系数" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">{{ row.status === 1 ? '启用' : '停用' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="车厢模板" width="560px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="模板名称"><el-input v-model="form.templateName" /></el-form-item>
        <el-form-item label="座席类型"><el-input v-model="form.seatType" /></el-form-item>
        <el-form-item label="车厢数量"><el-input-number v-model="form.coachCount" :min="1" /></el-form-item>
        <el-form-item label="座席排数"><el-input-number v-model="form.seatRows" :min="1" /></el-form-item>
        <el-form-item label="座席列数"><el-input-number v-model="form.seatCols" :min="1" /></el-form-item>
        <el-form-item label="价格系数"><el-input-number v-model="form.priceFactor" :min="0.1" :step="0.1" :precision="2" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
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
import { deleteCarriage, getCarriageList, saveCarriage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, templateName: '', seatType: '', status: null })
const form = reactive({ id: null, templateName: '', seatType: '二等座', coachCount: 4, seatRows: 10, seatCols: 5, priceFactor: 1, status: 1 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCarriageList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, templateName: '', seatType: '二等座', coachCount: 4, seatRows: 10, seatCols: 5, priceFactor: 1, status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveCarriage(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteCarriage(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
