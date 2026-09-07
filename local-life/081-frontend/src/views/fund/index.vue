<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.recordType" placeholder="记录类型" style="width: 150px" clearable>
          <el-option label="收入" value="income" />
          <el-option label="支出" value="expense" />
        </el-select>
        <el-date-picker v-model="query.startDate" type="date" placeholder="开始日期" value-format="YYYY-MM-DD" />
        <el-date-picker v-model="query.endDate" type="date" placeholder="结束日期" value-format="YYYY-MM-DD" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="recordType" label="类型">
          <template #default="{ row }">
            <el-tag :type="row.recordType === 'income' ? 'success' : 'danger'">
              {{ row.recordType === 'income' ? '收入' : '支出' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" />
        <el-table-column prop="purpose" label="用途" />
        <el-table-column prop="recordDate" label="记录日期" />
        <el-table-column prop="remark" label="备注" show-overflow-tooltip />
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" title="新增资金记录" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="记录类型" prop="recordType">
          <el-radio-group v-model="form.recordType">
            <el-radio label="income">收入</el-radio>
            <el-radio label="expense">支出</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="金额" prop="amount">
          <el-input v-model.number="form.amount" />
        </el-form-item>
        <el-form-item label="用途" prop="purpose">
          <el-input v-model="form.purpose" />
        </el-form-item>
        <el-form-item label="记录日期" prop="recordDate">
          <el-date-picker v-model="form.recordDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getFundList, addFund } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  recordType: '',
  startDate: '',
  endDate: ''
})

const form = reactive({
  recordType: 'income',
  amount: null,
  purpose: '',
  recordDate: '',
  remark: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFundList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    recordType: 'income',
    amount: null,
    purpose: '',
    recordDate: '',
    remark: ''
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addFund(form)
  ElMessage.success('添加成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}
</style>
