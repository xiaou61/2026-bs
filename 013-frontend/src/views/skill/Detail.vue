<template>
  <div class="skill-detail">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div class="skill-header">
            <h2>{{ skill?.title }}</h2>
            <div class="skill-meta">
              <el-tag>{{ skill?.category }}</el-tag>
              <span>{{ skill?.viewCount }}次浏览</span>
              <span>{{ skill?.orderCount }}次成交</span>
              <el-rate v-if="skill" v-model="skill.rating" disabled show-score text-color="#ff9900" />
            </div>
          </div>

          <el-divider />

          <el-descriptions :column="2" border>
            <el-descriptions-item label="服务时长">{{ skill?.serviceDuration }}分钟</el-descriptions-item>
            <el-descriptions-item label="服务价格">¥{{ skill?.hourlyPrice }}/小时</el-descriptions-item>
            <el-descriptions-item label="服务地点" :span="2">
              {{ skill?.serviceLocation }}
              ({{ skill?.locationType === 0 ? '上门' : skill?.locationType === 1 ? '指定地点' : '线上' }})
            </el-descriptions-item>
          </el-descriptions>

          <el-divider />

          <h3>服务介绍</h3>
          <p class="description">{{ skill?.description }}</p>

          <el-divider />

          <h3>个人简介</h3>
          <p class="introduction">{{ skill?.introduction }}</p>

          <el-divider />

          <h3>作品展示</h3>
          <div class="portfolio">
            <el-empty description="暂无作品" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <span>服务者信息</span>
          </template>
          <div class="provider-info">
            <el-avatar :size="60">{{ provider?.nickname?.charAt(0) }}</el-avatar>
            <h4>{{ provider?.nickname }}</h4>
            <p>信用分：{{ provider?.creditScore }}</p>
            <el-rate v-if="skill" v-model="skill.rating" disabled show-score text-color="#ff9900" />
          </div>
        </el-card>

        <el-card style="margin-top: 20px;">
          <template #header>
            <span>预约服务</span>
          </template>
          
          <div class="book-section">
            <div class="price-info">
              <span>服务价格：</span>
              <span class="price">¥{{ skill?.hourlyPrice }}/小时</span>
            </div>

            <el-divider />

            <div class="total-info">
              <span>预约订金（30%）：</span>
              <span class="price">¥{{ (skill?.hourlyPrice * 0.3).toFixed(2) }}</span>
            </div>

            <el-button type="primary" size="large" @click="dialogVisible = true" style="width: 100%">
              立即预约
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="预约服务" width="500px">
      <el-form :model="bookForm" label-width="100px">
        <el-form-item label="预约时间">
          <el-date-picker v-model="bookForm.appointmentTime" type="datetime" placeholder="选择预约时间" style="width: 100%" />
        </el-form-item>
        <el-form-item label="需求描述">
          <el-input type="textarea" v-model="bookForm.requirement" :rows="4" placeholder="请描述您的需求" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleBook">确认预约</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getSkillDetail } from '@/api/skill'
import { ElMessage } from 'element-plus'

const route = useRoute()

const skill = ref(null)
const provider = ref(null)
const dialogVisible = ref(false)

const bookForm = reactive({
  appointmentTime: '',
  requirement: ''
})

const loadDetail = async () => {
  try {
    const res = await getSkillDetail(route.params.id)
    skill.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleBook = () => {
  ElMessage.success('预约成功，请联系服务者确认时间')
  dialogVisible.value = false
}

onMounted(() => {
  loadDetail()
})
</script>

<style scoped>
.skill-header h2 {
  margin-bottom: 15px;
  color: #303133;
}

.skill-meta {
  display: flex;
  align-items: center;
  gap: 20px;
  color: #909399;
  font-size: 14px;
}

.description,
.introduction {
  line-height: 1.8;
  color: #606266;
  margin-top: 15px;
}

.portfolio {
  margin-top: 15px;
}

.provider-info {
  text-align: center;
  padding: 20px;
}

.provider-info h4 {
  margin: 15px 0 5px;
}

.provider-info p {
  color: #909399;
  margin-bottom: 10px;
}

.book-section {
  padding: 10px 0;
}

.price-info,
.total-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.price {
  color: #f56c6c;
  font-size: 20px;
  font-weight: bold;
}
</style>


