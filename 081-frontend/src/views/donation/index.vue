<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model.number="query.donorId" placeholder="捐赠人ID" style="width: 150px" clearable />
        <el-input v-model.number="query.childId" placeholder="儿童ID" style="width: 150px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增捐赠</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="donorId" label="捐赠人ID" />
        <el-table-column prop="childId" label="儿童ID" />
        <el-table-column prop="projectId" label="项目ID" />
        <el-table-column prop="amount" label="捐赠金额" />
        <el-table-column prop="donationType" label="捐赠类型">
          <template #default="{ row }">
            {{ row.donationType === 'money' ? '资金' : '物资' }}
          </template>
        </el-table-column>
        <el-table-column prop="donationDate" label="捐赠日期" />
        <el-table-column prop="paymentMethod" label="支付方式" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">
              {{ row.status === 1 ? '已确认' : '待确认' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleConfirm(row.id)" v-if="row.status === 0">确认</el-button>
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

    <el-dialog v-model="dialogVisible" title="新增捐赠" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="捐赠人ID" prop="donorId">
          <el-input v-model.number="form.donorId" />
        </el-form-item>
        <el-form-item label="儿童ID" prop="childId">
          <el-input v-model.number="form.childId" />
        </el-form-item>
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model.number="form.projectId" />
        </el-form-item>
        <el-form-item label="捐赠类型" prop="donationType">
          <el-radio-group v-model="form.donationType">
            <el-radio label="money">资金</el-radio>
            <el-radio label="material">物资</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="捐赠金额" prop="amount" v-if="form.donationType === 'money'">
          <el-input v-model.number="form.amount" />
        </el-form-item>
        <el-form-item label="物资描述" prop="materialDesc" v-if="form.donationType === 'material'">
          <el-input v-model="form.materialDesc" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="捐赠日期" prop="donationDate">
          <el-date-picker v-model="form.donationDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="支付方式" prop="paymentMethod">
          <el-select v-model="form.paymentMethod">
            <el-option label="支付宝" value="alipay" />
            <el-option label="微信" value="wechat" />
            <el-option label="银行转账" value="bank" />
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
import { getDonationList, addDonation, confirmDonation } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  donorId: null,
  childId: null
})

const form = reactive({
  donorId: null,
  childId: null,
  projectId: null,
  amount: null,
  donationType: 'money',
  materialDesc: '',
  donationDate: '',
  paymentMethod: 'alipay'
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDonationList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    donorId: null,
    childId: null,
    projectId: null,
    amount: null,
    donationType: 'money',
    materialDesc: '',
    donationDate: '',
    paymentMethod: 'alipay'
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addDonation(form)
  ElMessage.success('添加成功')
  dialogVisible.value = false
  loadData()
}

const handleConfirm = async (id) => {
  await confirmDonation(id)
  ElMessage.success('确认成功')
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
