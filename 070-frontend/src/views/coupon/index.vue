<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.name" placeholder="优惠券名称" clearable style="width: 220px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增优惠券</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" min-width="180" />
        <el-table-column prop="discountType" label="类型" width="120" />
        <el-table-column prop="discountValue" label="面额" width="100" />
        <el-table-column prop="minAmount" label="门槛" width="100" />
        <el-table-column prop="totalCount" label="总量" width="90" />
        <el-table-column prop="usedCount" label="已用" width="90" />
        <el-table-column prop="status" label="状态" width="90" />
        <el-table-column label="操作" width="170">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="优惠券信息" width="560px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.type" placeholder="GENERAL" /></el-form-item>
        <el-form-item label="折扣类型">
          <el-select v-model="form.discountType" style="width: 100%">
            <el-option value="AMOUNT" label="满减" />
            <el-option value="RATE" label="折扣" />
          </el-select>
        </el-form-item>
        <el-form-item label="折扣值"><el-input-number v-model="form.discountValue" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="最低金额"><el-input-number v-model="form.minAmount" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="发放总量"><el-input-number v-model="form.totalCount" :min="0" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :label="1">启用</el-radio><el-radio :label="0">停用</el-radio></el-radio-group></el-form-item>
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
import { deleteCoupon, getCouponList, saveCoupon } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, name: '', status: null })
const form = reactive({ id: null, name: '', type: 'GENERAL', discountType: 'AMOUNT', discountValue: 10, minAmount: 0, totalCount: 100, startTime: '', endTime: '', status: 1 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCouponList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, name: '', type: 'GENERAL', discountType: 'AMOUNT', discountValue: 10, minAmount: 0, totalCount: 100, startTime: '', endTime: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveCoupon(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteCoupon(id)
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
