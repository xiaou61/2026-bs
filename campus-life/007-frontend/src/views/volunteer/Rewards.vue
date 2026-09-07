<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="reward in rewards" :key="reward.id">
        <el-card class="reward-card" shadow="hover">
          <div class="reward-image">
            <el-icon :size="80"><Present /></el-icon>
          </div>
          <h3>{{ reward.name }}</h3>
          <p class="description">{{ reward.description }}</p>
          <div class="points">
            <el-icon><Coin /></el-icon>
            <span>{{ reward.points }} 积分</span>
          </div>
          <div class="stock">库存: {{ reward.stock }}</div>
          <el-button type="primary" style="width: 100%; margin-top: 10px" :disabled="reward.stock <= 0" @click="handleExchange(reward)">
            {{ reward.stock > 0 ? '立即兑换' : '已售罄' }}
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="showDialog" title="兑换商品" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="商品名称">
          <span>{{ currentReward.name }}</span>
        </el-form-item>
        <el-form-item label="所需积分">
          <span style="color: #409eff; font-weight: bold">{{ currentReward.points }} 积分</span>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="收货地址">
          <el-input v-model="form.address" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmExchange">确定兑换</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAvailableRewards } from '@/api/reward'
import { exchangeReward } from '@/api/exchange'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const rewards = ref([])
const showDialog = ref(false)
const currentReward = ref({})

const form = reactive({
  phone: '',
  address: '',
  remark: ''
})

const loadRewards = async () => {
  const res = await getAvailableRewards()
  rewards.value = res.data
}

const handleExchange = (reward) => {
  if (userStore.userInfo.availablePoints < reward.points) {
    ElMessage.warning('积分不足')
    return
  }
  currentReward.value = reward
  form.phone = userStore.userInfo.phone
  showDialog.value = true
}

const confirmExchange = async () => {
  try {
    await ElMessageBox.confirm(`确定要兑换${currentReward.value.name}吗? 将消耗${currentReward.value.points}积分`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await exchangeReward({
      rewardId: currentReward.value.id,
      phone: form.phone,
      address: form.address,
      remark: form.remark
    })
    ElMessage.success('兑换成功')
    showDialog.value = false
    loadRewards()
    userStore.userInfo.availablePoints -= currentReward.value.points
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadRewards()
})
</script>

<style scoped>
.reward-card {
  margin-bottom: 20px;
  text-align: center;
}

.reward-image {
  padding: 20px;
  color: #409eff;
}

.reward-card h3 {
  margin: 10px 0;
  color: #303133;
}

.description {
  color: #606266;
  margin: 10px 0;
  min-height: 40px;
  font-size: 14px;
}

.points {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  color: #409eff;
  font-weight: bold;
  font-size: 18px;
  margin: 10px 0;
}

.stock {
  color: #909399;
  font-size: 14px;
}
</style>

