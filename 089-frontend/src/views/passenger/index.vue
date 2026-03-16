<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.passengerName" placeholder="乘车人姓名" clearable style="width: 180px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增乘车人</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="passengerName" label="姓名" width="120" />
        <el-table-column prop="idCard" label="证件号" min-width="220" />
        <el-table-column prop="phone" label="手机号" width="140" />
        <el-table-column prop="passengerType" label="类型" width="100" />
        <el-table-column label="默认" width="80">
          <template #default="{ row }">{{ row.isDefault === 1 ? '是' : '否' }}</template>
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

    <el-dialog v-model="dialogVisible" title="乘车人信息" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名"><el-input v-model="form.passengerName" /></el-form-item>
        <el-form-item label="证件号"><el-input v-model="form.idCard" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="乘客类型">
          <el-select v-model="form.passengerType" style="width: 100%">
            <el-option label="成人" value="ADULT" />
            <el-option label="学生" value="STUDENT" />
            <el-option label="儿童" value="CHILD" />
          </el-select>
        </el-form-item>
        <el-form-item label="设为默认">
          <el-radio-group v-model="form.isDefault">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
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
import { deletePassenger, getPassengerList, savePassenger } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, passengerName: '' })
const form = reactive({ id: null, passengerName: '', idCard: '', phone: '', passengerType: 'ADULT', isDefault: 0 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPassengerList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, passengerName: '', idCard: '', phone: '', passengerType: 'ADULT', isDefault: 0 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await savePassenger(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deletePassenger(id)
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
