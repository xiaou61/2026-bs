<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.trainCode" placeholder="车次编码" clearable style="width: 180px" />
        <el-input v-model="query.trainType" placeholder="列车类型" clearable style="width: 160px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option :value="1" label="启用" />
          <el-option :value="0" label="停用" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增列车</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="trainCode" label="车次编码" width="120" />
        <el-table-column prop="trainName" label="列车名称" min-width="160" />
        <el-table-column prop="trainType" label="列车类型" width="120" />
        <el-table-column prop="carriageCount" label="车厢数" width="90" />
        <el-table-column prop="seatCount" label="单车厢座席数" width="120" />
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

    <el-dialog v-model="dialogVisible" title="列车信息" width="560px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="车次编码"><el-input v-model="form.trainCode" /></el-form-item>
        <el-form-item label="列车名称"><el-input v-model="form.trainName" /></el-form-item>
        <el-form-item label="列车类型"><el-input v-model="form.trainType" placeholder="高铁/动车/城际" /></el-form-item>
        <el-form-item label="车厢数量"><el-input-number v-model="form.carriageCount" :min="1" /></el-form-item>
        <el-form-item label="单车厢座席数"><el-input-number v-model="form.seatCount" :min="1" /></el-form-item>
        <el-form-item label="封面地址"><el-input v-model="form.coverUrl" /></el-form-item>
        <el-form-item label="列车简介"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
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
import { deleteTrain, getTrainList, saveTrain } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, trainCode: '', trainType: '', status: null })
const form = reactive({ id: null, trainCode: '', trainName: '', trainType: '', carriageCount: 4, seatCount: 80, coverUrl: '', description: '', status: 1 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTrainList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, trainCode: '', trainName: '', trainType: '', carriageCount: 4, seatCount: 80, coverUrl: '', description: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveTrain(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteTrain(id)
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
