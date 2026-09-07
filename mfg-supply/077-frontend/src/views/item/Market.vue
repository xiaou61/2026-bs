<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.title" placeholder="标题关键词" style="width: 220px" clearable />
      <el-input v-model="query.serviceName" placeholder="任务编码" style="width: 180px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 140px" clearable>
        <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
      </el-select>
      <el-button type="primary" @click="loadData">搜索</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="生产任务" min-width="240" />
      <el-table-column prop="serviceName" label="任务编码" width="140" />
      <el-table-column prop="storeName" label="生产车间" width="130" />
      <el-table-column prop="vehicleType" label="工艺类型" width="120" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="stock" label="产能上限" width="100" />
      <el-table-column label="操作" width="240" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openDetail(row)">详情</el-button>
          <el-button link @click="openOrder(row)">创建工单</el-button>
          <el-button link :type="favMap[row.id] ? 'warning' : 'primary'" @click="toggleFav(row)">
            {{ favMap[row.id] ? '取消收藏' : '收藏' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="query.pageNum"
      v-model:page-size="query.pageSize"
      :total="total"
      layout="total, prev, pager, next"
      style="margin-top: 12px"
      @current-change="loadData"
    />
  </el-card>

  <el-dialog v-model="orderVisible" title="创建工单" width="560px">
    <el-form :model="orderForm" label-width="92px">
      <el-form-item label="生产任务">{{ currentItem?.title }}</el-form-item>
      <el-form-item label="单价">¥{{ currentItem?.price }}</el-form-item>
      <el-form-item label="投产数量">
        <el-input-number v-model="orderForm.quantity" :min="1" :max="currentItem?.stock || 1" />
      </el-form-item>
      <el-form-item label="计划日期">
        <el-date-picker v-model="orderForm.appointmentDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择日期" style="width: 100%" />
      </el-form-item>
      <el-form-item label="计划时间">
        <el-time-picker v-model="orderForm.appointmentTime" value-format="HH:mm:ss" format="HH:mm" placeholder="请选择时间" style="width: 100%" />
      </el-form-item>
      <el-form-item label="批次号">
        <el-input v-model="orderForm.plateNo" maxlength="20" show-word-limit />
      </el-form-item>
      <el-form-item label="产品型号">
        <el-input v-model="orderForm.vehicleModel" maxlength="80" show-word-limit />
      </el-form-item>
      <el-form-item label="工艺说明">
        <el-input v-model="orderForm.faultDesc" type="textarea" :rows="3" maxlength="500" show-word-limit />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="orderForm.remark" maxlength="255" show-word-limit />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="orderVisible = false">取消</el-button>
      <el-button type="primary" @click="submitOrder">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="detailVisible" title="任务详情" width="720px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="标题">{{ detail.title }}</el-descriptions-item>
      <el-descriptions-item label="任务编码">{{ detail.serviceName }}</el-descriptions-item>
      <el-descriptions-item label="生产车间">{{ detail.storeName }}</el-descriptions-item>
      <el-descriptions-item label="工艺类型">{{ detail.vehicleType }}</el-descriptions-item>
      <el-descriptions-item label="价格">¥{{ detail.price }}</el-descriptions-item>
      <el-descriptions-item label="产能上限">{{ detail.stock }}</el-descriptions-item>
      <el-descriptions-item label="执行方式">{{ detail.bookingType }}</el-descriptions-item>
      <el-descriptions-item label="完工量">{{ detail.bookedCount }}</el-descriptions-item>
      <el-descriptions-item label="描述" :span="2">{{ detail.description }}</el-descriptions-item>
    </el-descriptions>
    <el-divider>最新评价</el-divider>
    <el-empty v-if="!reviews.length" description="暂无评价" />
    <el-table v-else :data="reviews" size="small">
      <el-table-column prop="userName" label="用户" width="120" />
      <el-table-column prop="rating" label="评分" width="80" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="createTime" label="时间" width="180" />
    </el-table>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { checkFavorite, createOrder, getCategoryList, getItemById, getItemList, getReviewByItem, toggleFavorite } from '../../api'

const loading = ref(false)
const tableData = ref([])
const categories = ref([])
const total = ref(0)
const favMap = reactive({})

const query = reactive({ pageNum: 1, pageSize: 10, title: '', serviceName: '', categoryId: null })
const orderVisible = ref(false)
const detailVisible = ref(false)
const currentItem = ref(null)
const detail = ref({})
const reviews = ref([])
const orderForm = reactive({
  quantity: 1,
  appointmentDate: '',
  appointmentTime: '',
  plateNo: '',
  vehicleModel: '',
  faultDesc: '',
  remark: ''
})

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getItemList(query)
    tableData.value = res.data.records
    total.value = res.data.total
    const checks = await Promise.all(tableData.value.map(item => checkFavorite(item.id)))
    tableData.value.forEach((item, index) => {
      favMap[item.id] = !!checks[index].data
    })
  } finally {
    loading.value = false
  }
}

const openOrder = (row) => {
  currentItem.value = row
  Object.assign(orderForm, {
    quantity: 1,
    appointmentDate: '',
    appointmentTime: '',
    plateNo: '',
    vehicleModel: '',
    faultDesc: '',
    remark: ''
  })
  orderVisible.value = true
}

const submitOrder = async () => {
  if (!currentItem.value) {
    ElMessage.warning('未找到任务详情')
    return
  }
  if (!orderForm.quantity || orderForm.quantity < 1) {
    ElMessage.warning('投产数量必须大于0')
    return
  }
  if (!orderForm.appointmentDate) {
    ElMessage.warning('请选择计划日期')
    return
  }
  if (!orderForm.appointmentTime) {
    ElMessage.warning('请选择计划时间')
    return
  }
  if (!orderForm.plateNo || !orderForm.plateNo.trim()) {
    ElMessage.warning('请输入批次号')
    return
  }
  if (!orderForm.vehicleModel || !orderForm.vehicleModel.trim()) {
    ElMessage.warning('请输入产品型号')
    return
  }
  await createOrder({
    serviceId: currentItem.value.id,
    quantity: orderForm.quantity,
    appointmentDate: orderForm.appointmentDate,
    appointmentTime: orderForm.appointmentTime,
    plateNo: orderForm.plateNo.trim(),
    vehicleModel: orderForm.vehicleModel.trim(),
    faultDesc: orderForm.faultDesc ? orderForm.faultDesc.trim() : '',
    remark: orderForm.remark ? orderForm.remark.trim() : ''
  })
  ElMessage.success('执行工单创建成功，请在我的工单中支付')
  orderVisible.value = false
}

const openDetail = async (row) => {
  const [dRes, rRes] = await Promise.all([getItemById(row.id), getReviewByItem(row.id)])
  detail.value = dRes.data
  reviews.value = rRes.data || []
  detailVisible.value = true
}

const toggleFav = async (row) => {
  const res = await toggleFavorite(row.id)
  favMap[row.id] = !!res.data
  ElMessage.success(res.data ? '已收藏' : '已取消收藏')
}

onMounted(async () => {
  await loadCategories()
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






