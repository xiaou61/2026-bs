<template>
  <div>
    <el-card class="mb16">
      <template #header>可领取优惠券</template>
      <el-table :data="publicCoupons" border>
        <el-table-column prop="name" label="名称" min-width="180" />
        <el-table-column prop="discountType" label="类型" width="100" />
        <el-table-column prop="discountValue" label="优惠" width="100" />
        <el-table-column prop="minAmount" label="门槛" width="100" />
        <el-table-column prop="endTime" label="截止时间" width="180" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleReceive(row.id)">领取</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card>
      <div class="toolbar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="未使用" value="UNUSED" />
          <el-option label="已使用" value="USED" />
        </el-select>
        <el-button type="primary" @click="loadMyCoupons">查询</el-button>
      </div>
      <el-table :data="myCoupons" border>
        <el-table-column prop="userCouponId" label="编号" width="90" />
        <el-table-column prop="name" label="名称" min-width="180" />
        <el-table-column prop="discountType" label="类型" width="100" />
        <el-table-column prop="discountValue" label="优惠" width="100" />
        <el-table-column prop="minAmount" label="门槛" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="expireTime" label="过期时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyCouponList, getPublicCouponList, receiveCoupon } from '../../api'

const publicCoupons = ref([])
const myCoupons = ref([])
const query = reactive({ status: '' })

const loadPublicCoupons = async () => {
  const res = await getPublicCouponList()
  publicCoupons.value = res.data || []
}

const loadMyCoupons = async () => {
  const res = await getMyCouponList(query)
  myCoupons.value = res.data || []
}

const handleReceive = async id => {
  await receiveCoupon(id)
  ElMessage.success('领取成功')
  await loadMyCoupons()
}

onMounted(async () => {
  await loadPublicCoupons()
  await loadMyCoupons()
})
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}

.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}
</style>
