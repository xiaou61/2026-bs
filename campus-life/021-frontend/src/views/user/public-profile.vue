<template>
  <div class="page">
    <section class="panel">
      <div class="user-top" v-loading="loading">
        <div class="user-card">
          <el-avatar :size="88" :src="profile.avatar || ''">
            {{ (profile.realName || profile.username || '同').slice(0, 1) }}
          </el-avatar>
          <div>
            <h2>{{ profile.realName || profile.username || '校园用户' }}</h2>
            <p>{{ profile.username || '--' }}</p>
          </div>
        </div>
        <el-tag :type="creditMeta.type">{{ creditMeta.label }}</el-tag>
      </div>

      <div class="info-grid">
        <div>
          <label>用户名</label>
          <span>{{ profile.username || '--' }}</span>
        </div>
        <div>
          <label>学院</label>
          <span>{{ profile.college || '--' }}</span>
        </div>
        <div>
          <label>信用分</label>
          <span>{{ profile.creditScore ?? 100 }}</span>
        </div>
        <div>
          <label>加入时间</label>
          <span>{{ formatTime(profile.createTime) }}</span>
        </div>
      </div>

      <div class="hint">
        当前公开资料仅展示基础信息，若想进一步沟通，可从商品详情页直接发起聊天。
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { userApi } from '@/api/user'
import { formatTime, getCreditMeta } from '@/utils/market'

const route = useRoute()
const loading = ref(false)
const profile = ref({})

const creditMeta = computed(() => getCreditMeta(profile.value.creditScore ?? 100))

const loadProfile = async () => {
  loading.value = true
  try {
    const response = await userApi.getUserProfile(route.params.id)
    profile.value = response.data || {}
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadProfile()
})
</script>

<style scoped>
.panel {
  background: rgba(255, 255, 255, 0.92);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.user-top {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  margin-bottom: 22px;
}

.user-card {
  display: flex;
  align-items: center;
  gap: 18px;
}

.user-card h2 {
  font-size: 32px;
  margin-bottom: 8px;
}

.user-card p {
  color: #64748b;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.info-grid div {
  padding: 18px;
  border-radius: 22px;
  background: #f8fafc;
}

.info-grid label {
  display: block;
  font-size: 12px;
  color: #64748b;
  margin-bottom: 8px;
}

.hint {
  margin-top: 20px;
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(249, 115, 22, 0.08);
  color: #9a3412;
  line-height: 1.8;
}

@media (max-width: 720px) {
  .user-top {
    flex-direction: column;
    align-items: flex-start;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }
}
</style>
