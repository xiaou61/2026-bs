<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model.number="query.childId" placeholder="儿童ID" style="width: 150px" clearable />
        <el-input v-model.number="query.donorId" placeholder="捐赠人ID" style="width: 150px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="childId" label="儿童ID" />
        <el-table-column prop="donorId" label="捐赠人ID" />
        <el-table-column prop="sponsorType" label="资助类型">
          <template #default="{ row }">
            {{ row.sponsorType === 'one_time' ? '一次性' : row.sponsorType === 'monthly' ? '按月' : '按年' }}
          </template>
        </el-table-column>
        <el-table-column prop="sponsorAmount" label="资助金额" />
        <el-table-column prop="startDate" label="开始日期" />
        <el-table-column prop="endDate" label="结束日期" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'info' : 'warning'">
              {{ ['待确认', '进行中', '已完成', '已终止'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资助关系' : '新增资助关系'" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="儿童ID" prop="childId">
          <el-input v-model.number="form.childId" />
        </el-form-item>
        <el-form-item label="捐赠人ID" prop="donorId">
          <el-input v-model.number="form.donorId" />
        </el-form-item>
        <el-form-item label="资助类型" prop="sponsorType">
          <el-select v-model="form.sponsorType">
            <el-option label="一次性" value="one_time" />
            <el-option label="按月" value="monthly" />
            <el-option label="按年" value="yearly" />
          </el-select>
        </el-form-item>
        <el-form-item label="资助金额" prop="sponsorAmount">
          <el-input v-model.number="form.sponsorAmount" />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="状态" prop="status" v-if="form.id">
          <el-select v-model="form.status">
            <el-option label="待确认" :value="0" />
            <el-option label="进行中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已终止" :value="3" />
          </el-select>
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
import { getSponsorList, addSponsor, updateSponsor } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  childId: null,
  donorId: null
})

const form = reactive({
  id: null,
  childId: null,
  donorId: null,
  sponsorType: 'yearly',
  sponsorAmount: null,
  startDate: '',
  endDate: '',
  status: 0
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSponsorList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    childId: null,
    donorId: null,
    sponsorType: 'yearly',
    sponsorAmount: null,
    startDate: '',
    endDate: '',
    status: 0
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateSponsor(form)
  } else {
    await addSponsor(form)
  }
  ElMessage.success('操作成功')
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
