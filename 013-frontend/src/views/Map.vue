<template>
  <div class="map-container">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>附近共享物品</span>
              <el-radio-group v-model="itemType" @change="loadItems">
                <el-radio-button label="">全部</el-radio-button>
                <el-radio-button label="BIKE">单车</el-radio-button>
                <el-radio-button label="CHARGER">充电宝</el-radio-button>
                <el-radio-button label="UMBRELLA">雨伞</el-radio-button>
              </el-radio-group>
            </div>
          </el-template>

          <div class="map-placeholder">
            <el-icon :size="80"><Location /></el-icon>
            <p>地图区域</p>
            <small>可集成高德地图或百度地图API显示附近物品位置</small>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <span>附近物品列表</span>
          </template>

          <div class="item-list">
            <div v-for="item in items" :key="item.id" class="item-card" @click="handleRent(item)">
              <div class="item-icon">
                <el-icon v-if="item.itemType === 'BIKE'" :size="30" color="#409eff"><Bicycle /></el-icon>
                <el-icon v-else-if="item.itemType === 'CHARGER'" :size="30" color="#67c23a"><CharginPile /></el-icon>
                <el-icon v-else :size="30" color="#e6a23c"><Umbrella /></el-icon>
              </div>
              <div class="item-info">
                <h4>{{ getItemTypeName(item.itemType) }} {{ item.itemNo }}</h4>
                <p>{{ item.locationName }}</p>
                <div class="item-meta">
                  <el-tag size="small" :type="item.status === 0 ? 'success' : 'info'">
                    {{ item.status === 0 ? '可用' : '使用中' }}
                  </el-tag>
                  <span class="price">¥{{ item.hourlyPrice }}/小时</span>
                </div>
              </div>
            </div>

            <el-empty v-if="items.length === 0" description="附近暂无可用物品" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="rentDialogVisible" title="租借确认" width="500px">
      <div v-if="selectedItem">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="物品类型">{{ getItemTypeName(selectedItem.itemType) }}</el-descriptions-item>
          <el-descriptions-item label="物品编号">{{ selectedItem.itemNo }}</el-descriptions-item>
          <el-descriptions-item label="位置">{{ selectedItem.locationName }}</el-descriptions-item>
          <el-descriptions-item label="租金">¥{{ selectedItem.hourlyPrice }}/小时</el-descriptions-item>
          <el-descriptions-item label="押金">¥{{ selectedItem.depositAmount }}</el-descriptions-item>
        </el-descriptions>

        <el-alert v-if="userStore.userInfo?.depositBalance < selectedItem.depositAmount" 
                  title="押金余额不足，请先充值押金" 
                  type="warning" 
                  style="margin-top: 15px" 
                  :closable="false" />
      </div>

      <template #footer>
        <el-button @click="rentDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="rentLoading" @click="confirmRent">确认租借</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getNearbyItems, rentItem } from '@/api/shared'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const itemType = ref('')
const items = ref([])
const selectedItem = ref(null)
const rentDialogVisible = ref(false)
const rentLoading = ref(false)

const getItemTypeName = (type) => {
  const map = {
    'BIKE': '共享单车',
    'CHARGER': '共享充电宝',
    'UMBRELLA': '共享雨伞'
  }
  return map[type] || type
}

const loadItems = async () => {
  try {
    const res = await getNearbyItems({ itemType: itemType.value })
    items.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleRent = (item) => {
  if (item.status !== 0) {
    ElMessage.warning('该物品当前不可用')
    return
  }
  selectedItem.value = item
  rentDialogVisible.value = true
}

const confirmRent = async () => {
  if (!userStore.token) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  if (userStore.userInfo?.authStatus !== 2) {
    ElMessage.warning('请先完成实名认证')
    router.push('/auth')
    return
  }

  rentLoading.value = true
  try {
    const res = await rentItem({
      itemId: selectedItem.value.id,
      orderType: 'SHARED'
    })
    ElMessage.success('租借成功')
    rentDialogVisible.value = false
    router.push(`/order/${res.data.id}`)
  } catch (error) {
    console.error(error)
  } finally {
    rentLoading.value = false
  }
}

onMounted(() => {
  loadItems()
})
</script>

<style scoped>
.map-placeholder {
  height: 500px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  color: #909399;
}

.map-placeholder p {
  margin: 15px 0 5px;
  font-size: 16px;
}

.item-list {
  max-height: 600px;
  overflow-y: auto;
}

.item-card {
  display: flex;
  gap: 15px;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  margin-bottom: 10px;
  cursor: pointer;
  transition: all 0.3s;
}

.item-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 12px rgba(64, 158, 255, 0.2);
}

.item-icon {
  flex-shrink: 0;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
}

.item-info {
  flex: 1;
}

.item-info h4 {
  margin: 0 0 5px;
  color: #303133;
}

.item-info p {
  margin: 0 0 10px;
  color: #909399;
  font-size: 14px;
}

.item-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  color: #f56c6c;
  font-weight: bold;
}
</style>


