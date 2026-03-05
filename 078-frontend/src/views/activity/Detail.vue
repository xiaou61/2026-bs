<template>
  <div class="page-container">
    <el-card v-if="activity">
      <div class="detail-header">
        <el-image :src="activity.product?.cover" style="width: 300px; height: 300px" fit="cover" />
        <div class="detail-info">
          <h2>{{ activity.product?.name }}</h2>
          <div class="price">
            <span class="group-price">¥{{ activity.groupPrice }}</span>
            <span class="original-price">¥{{ activity.product?.originalPrice }}</span>
          </div>
          <div class="meta">
            <span>{{ activity.minCount }}人成团</span>
            <span>剩余{{ activity.limitHours }}小时</span>
          </div>
          <p class="desc">{{ activity.product?.description }}</p>
          <div class="actions">
            <el-button type="danger" size="large" @click="handleOpenGroup">发起拼团</el-button>
            <el-button type="primary" size="large" @click="showOngoing = true">参与拼团</el-button>
          </div>
        </div>
      </div>
    </el-card>
    <el-card style="margin-top: 20px">
      <template #header>商品评价</template>
      <div v-for="review in reviews" :key="review.id" class="review-item">
        <el-avatar :size="40">{{ review.username?.charAt(0) }}</el-avatar>
        <div class="review-content">
          <div class="review-header">
            <span class="nickname">{{ review.username }}</span>
            <el-rate v-model="review.rating" disabled />
          </div>
          <p>{{ review.content }}</p>
        </div>
      </div>
    </el-card>
    <el-dialog v-model="showAddress" title="选择收货地址">
      <el-radio-group v-model="selectedAddress" style="display: block">
        <div v-for="addr in addressList" :key="addr.id" class="address-item">
          <el-radio :label="addr.id">{{ addr.name }} {{ addr.phone }} {{ addr.province }}{{ addr.city }}{{ addr.district }}{{ addr.detail }}</el-radio>
        </div>
      </el-radio-group>
      <template #footer>
        <el-button @click="showAddress = false">取消</el-button>
        <el-button type="primary" @click="confirmGroup">确定</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="showOngoing" title="正在进行的拼团">
      <div v-for="g in ongoingGroups" :key="g.id" class="group-item">
        <el-avatar :size="40">{{ g.leaderName?.charAt(0) }}</el-avatar>
        <div class="group-info">
          <span>{{ g.leaderName }}的团</span>
          <span>还差{{ g.targetCount - g.currentCount }}人</span>
        </div>
        <el-button type="primary" size="small" @click="handleJoinGroup(g.id)">参团</el-button>
      </div>
      <el-empty v-if="!ongoingGroups.length" description="暂无进行中的拼团" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getActivityDetail, getOngoingGroups, getProductReviews, getAddressList, createGroup, joinGroup } from '../../api'

const route = useRoute()
const router = useRouter()
const activity = ref(null)
const reviews = ref([])
const ongoingGroups = ref([])
const addressList = ref([])
const showAddress = ref(false)
const showOngoing = ref(false)
const selectedAddress = ref(null)
const groupAction = ref('create')
const joinGroupId = ref(null)

onMounted(async () => {
  const res = await getActivityDetail(route.params.id)
  activity.value = res.data
  const reviewRes = await getProductReviews(res.data.productId, { pageNum: 1, pageSize: 10 })
  reviews.value = reviewRes.data.records
  const groupRes = await getOngoingGroups(route.params.id)
  ongoingGroups.value = groupRes.data
  const addrRes = await getAddressList()
  addressList.value = addrRes.data
  if (addrRes.data.length) selectedAddress.value = addrRes.data[0].id
})

const handleOpenGroup = () => {
  groupAction.value = 'create'
  showAddress.value = true
}

const handleJoinGroup = (id) => {
  groupAction.value = 'join'
  joinGroupId.value = id
  showOngoing.value = false
  showAddress.value = true
}

const confirmGroup = async () => {
  if (!selectedAddress.value) {
    ElMessage.warning('请选择收货地址')
    return
  }
  if (groupAction.value === 'create') {
    await createGroup({ activityId: activity.value.id, addressId: selectedAddress.value })
    ElMessage.success('开团成功')
  } else {
    await joinGroup({ groupOrderId: joinGroupId.value, addressId: selectedAddress.value })
    ElMessage.success('参团成功')
  }
  showAddress.value = false
  router.push('/group')
}
</script>

<style scoped>
.page-container { padding: 20px; }
.detail-header { display: flex; gap: 30px; }
.detail-info { flex: 1; }
.detail-info h2 { margin-bottom: 15px; }
.price { margin: 20px 0; }
.group-price { font-size: 32px; color: #f56c6c; font-weight: bold; }
.original-price { font-size: 16px; color: #999; text-decoration: line-through; margin-left: 10px; }
.meta span { margin-right: 20px; color: #666; }
.desc { color: #666; margin: 20px 0; }
.actions { margin-top: 30px; }
.review-item { display: flex; gap: 15px; padding: 15px 0; border-bottom: 1px solid #eee; }
.review-content { flex: 1; }
.review-header { display: flex; align-items: center; gap: 10px; margin-bottom: 8px; }
.nickname { font-weight: bold; }
.address-item { padding: 10px 0; border-bottom: 1px solid #eee; }
.group-item { display: flex; align-items: center; gap: 15px; padding: 10px 0; border-bottom: 1px solid #eee; }
.group-info { flex: 1; display: flex; justify-content: space-between; }
</style>
