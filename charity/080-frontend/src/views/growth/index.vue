<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model.number="query.childId" placeholder="儿童ID" style="width: 150px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="childId" label="儿童ID" />
        <el-table-column prop="recordDate" label="记录日期" />
        <el-table-column prop="recordType" label="记录类型">
          <template #default="{ row }">
            {{ row.recordType === 'study' ? '学习' : row.recordType === 'life' ? '生活' : '健康' }}
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" />
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

    <el-dialog v-model="dialogVisible" title="新增成长记录" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="儿童ID" prop="childId">
          <el-input v-model.number="form.childId" />
        </el-form-item>
        <el-form-item label="记录日期" prop="recordDate">
          <el-date-picker v-model="form.recordDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="记录类型" prop="recordType">
          <el-select v-model="form.recordType">
            <el-option label="学习" value="study" />
            <el-option label="生活" value="life" />
            <el-option label="健康" value="health" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="5" />
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
import { getGrowthList, addGrowth } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  childId: null
})

const form = reactive({
  childId: null,
  recordDate: '',
  recordType: 'study',
  content: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getGrowthList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    childId: null,
    recordDate: '',
    recordType: 'study',
    content: ''
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addGrowth(form)
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
