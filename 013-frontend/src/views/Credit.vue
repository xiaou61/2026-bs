<template>
  <div class="credit-page">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="credit-score">
            <div class="score-circle">
              <div class="score-value">{{ userInfo?.creditScore }}</div>
              <div class="score-label">信用分</div>
            </div>
            
            <div class="credit-level">
              <el-tag :type="getLevelType(userInfo?.creditScore)" size="large">
                {{ getLevelText(userInfo?.creditScore) }}
              </el-tag>
            </div>

            <div class="credit-desc">
              <p>{{ getLevelDesc(userInfo?.creditScore) }}</p>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="16">
        <el-card>
          <template #header>
            <span>信用记录</span>
          </template>

          <el-timeline>
            <el-timeline-item
              v-for="log in creditLogs"
              :key="log.id"
              :timestamp="log.createTime"
              placement="top"
            >
              <el-card>
                <div class="log-item">
                  <div class="log-content">
                    <span>{{ log.reason }}</span>
                  </div>
                  <div class="log-score" :class="log.changeScore > 0 ? 'positive' : 'negative'">
                    {{ log.changeScore > 0 ? '+' : '' }}{{ log.changeScore }}分
                  </div>
                </div>
                <div class="log-balance">
                  {{ log.beforeScore }} → {{ log.afterScore }}
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>

          <el-empty v-if="creditLogs.length === 0" description="暂无记录" />
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <template #header>
        <span>信用规则说明</span>
      </template>

      <el-row :gutter="20">
        <el-col :span="12">
          <h4>正向行为</h4>
          <ul class="rule-list">
            <li>完成实名认证：+10分</li>
            <li>按时归还：+2分</li>
            <li>获得好评：+1分</li>
          </ul>
        </el-col>
        <el-col :span="12">
          <h4>负向行为</h4>
          <ul class="rule-list">
            <li>超时归还：-5分</li>
            <li>损坏物品：-10分</li>
            <li>违约不还：-50分</li>
            <li>获得差评：-3分</li>
          </ul>
        </el-col>
      </el-row>

      <el-divider />

      <h4>信用等级</h4>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="level-card excellent">
            <h4>优秀（90-100分）</h4>
            <p>享受押金减免50%</p>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="level-card good">
            <h4>良好（70-89分）</h4>
            <p>正常使用全部功能</p>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="level-card normal">
            <h4>一般（50-69分）</h4>
            <p>限制高价值物品租借</p>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="level-card poor">
            <h4>差（0-49分）</h4>
            <p>禁止租借物品</p>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCredit, getCreditLog } from '@/api/user'

const userInfo = ref(null)
const creditLogs = ref([])

const getLevelText = (score) => {
  if (score >= 90) return '优秀'
  if (score >= 70) return '良好'
  if (score >= 50) return '一般'
  return '差'
}

const getLevelType = (score) => {
  if (score >= 90) return 'success'
  if (score >= 70) return ''
  if (score >= 50) return 'warning'
  return 'danger'
}

const getLevelDesc = (score) => {
  if (score >= 90) return '您的信用非常好，可享受押金减免优惠'
  if (score >= 70) return '您的信用良好，可正常使用全部功能'
  if (score >= 50) return '您的信用一般，部分高价值物品无法租借'
  return '您的信用较差，请提升信用分后再使用'
}

const loadCredit = async () => {
  try {
    const res = await getCredit()
    userInfo.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const loadCreditLog = async () => {
  try {
    const res = await getCreditLog()
    creditLogs.value = res.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadCredit()
  loadCreditLog()
})
</script>

<style scoped>
.credit-score {
  text-align: center;
  padding: 40px 20px;
}

.score-circle {
  width: 150px;
  height: 150px;
  margin: 0 auto 30px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.score-value {
  font-size: 48px;
  font-weight: bold;
}

.score-label {
  font-size: 16px;
  margin-top: 5px;
}

.credit-level {
  margin-bottom: 20px;
}

.credit-desc p {
  color: #606266;
  line-height: 1.6;
}

.log-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.log-score.positive {
  color: #67c23a;
  font-weight: bold;
}

.log-score.negative {
  color: #f56c6c;
  font-weight: bold;
}

.log-balance {
  font-size: 12px;
  color: #909399;
}

.rule-list {
  padding-left: 20px;
  line-height: 2;
  color: #606266;
}

.level-card {
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  color: #fff;
}

.level-card.excellent {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.level-card.good {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.level-card.normal {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}

.level-card.poor {
  background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
}

.level-card h4 {
  margin-bottom: 10px;
  font-size: 16px;
}

.level-card p {
  font-size: 14px;
  opacity: 0.9;
}
</style>

