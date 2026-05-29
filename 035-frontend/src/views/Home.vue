<template>
  <div class="home-page">
    <!-- 顶部横幅 -->
    <div class="hero-banner">
      <div class="banner-content">
        <div class="banner-icon">🌾</div>
        <h1 class="banner-title">欢迎使用水稻收割预约系统</h1>
        <p class="banner-desc">智慧农业 · 高效调度 · 精准收割</p>
      </div>
      <div class="banner-decoration">
        <span class="deco-1">🚜</span>
        <span class="deco-2">🌾</span>
        <span class="deco-3">☀️</span>
      </div>
    </div>

    <!-- 功能卡片 -->
    <div class="feature-grid">
      <el-card class="feature-card" shadow="hover">
        <div class="feature-icon">📅</div>
        <h3>预约下单</h3>
        <p>在线预约收割时间，方便快捷</p>
      </el-card>

      <el-card class="feature-card" shadow="hover">
        <div class="feature-icon">👨‍🌾</div>
        <h3>机手调度</h3>
        <p>智能分配收割机手，高效作业</p>
      </el-card>

      <el-card class="feature-card" shadow="hover">
        <div class="feature-icon">📍</div>
        <h3>作业跟踪</h3>
        <p>实时追踪收割进度，全程可视</p>
      </el-card>

      <el-card class="feature-card" shadow="hover">
        <div class="feature-icon">💰</div>
        <h3>结算管理</h3>
        <p>透明计费，快速结算</p>
      </el-card>
    </div>

    <!-- 操作区域 -->
    <el-card class="action-card" shadow="hover">
      <div class="action-content">
        <div class="action-info">
          <h2>开始使用</h2>
          <p>登录后即可体验完整功能，管理您的收割预约</p>
        </div>
        <div class="action-buttons">
          <el-button type="primary" @click="$router.push('/health')" size="large" icon="CircleCheck">
            后端健康检查
          </el-button>
          <el-button
            v-if="!store.isAuthed"
            type="success"
            @click="$router.push('/login')"
            size="large"
            icon="Key"
          >
            立即登录
          </el-button>
          <el-button v-else type="success" size="large" icon="User" plain>
            已登录：{{ store.user?.username }}
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 统计信息 -->
    <div class="stats-section">
      <div class="stat-item">
        <div class="stat-value">1000+</div>
        <div class="stat-label">服务农户</div>
      </div>
      <div class="stat-item">
        <div class="stat-value">500+</div>
        <div class="stat-label">合作机手</div>
      </div>
      <div class="stat-item">
        <div class="stat-value">98%</div>
        <div class="stat-label">满意度</div>
      </div>
      <div class="stat-item">
        <div class="stat-value">24h</div>
        <div class="stat-label">快速响应</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '../stores/user'
const store = useUserStore()
</script>

<style scoped>
.home-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

/* 横幅区域 */
.hero-banner {
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 50%, #66BB6A 100%);
  border-radius: 24px;
  padding: 48px;
  color: white;
  position: relative;
  overflow: hidden;
  margin-bottom: 32px;
}

.banner-content {
  position: relative;
  z-index: 2;
}

.banner-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.banner-title {
  font-size: 36px;
  font-weight: 800;
  margin: 0 0 12px 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.banner-desc {
  font-size: 18px;
  margin: 0;
  opacity: 0.9;
  letter-spacing: 4px;
}

.banner-decoration {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  width: 50%;
  pointer-events: none;
}

.deco-1, .deco-2, .deco-3 {
  position: absolute;
  opacity: 0.3;
  animation: float 4s ease-in-out infinite;
}

.deco-1 { top: 20%; right: 10%; font-size: 64px; animation-delay: 0s; }
.deco-2 { top: 50%; right: 25%; font-size: 48px; animation-delay: 1s; }
.deco-3 { top: 15%; right: 35%; font-size: 56px; animation-delay: 2s; }

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-15px) rotate(5deg); }
}

/* 功能卡片 */
.feature-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 32px;
}

.feature-card {
  border-radius: 20px !important;
  border: none !important;
  text-align: center;
  padding: 24px;
  transition: all 0.3s ease !important;
  cursor: pointer;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 32px rgba(76, 175, 80, 0.2) !important;
}

.feature-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.feature-card h3 {
  font-size: 18px;
  font-weight: 700;
  color: #2E7D32;
  margin: 0 0 8px 0;
}

.feature-card p {
  color: #666;
  font-size: 14px;
  margin: 0;
}

/* 操作卡片 */
.action-card {
  border-radius: 20px !important;
  border: none !important;
  margin-bottom: 32px;
  background: linear-gradient(135deg, #E8F5E9 0%, #C8E6C9 100%) !important;
}

.action-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
}

.action-info h2 {
  font-size: 24px;
  font-weight: 700;
  color: #1B5E20;
  margin: 0 0 8px 0;
}

.action-info p {
  color: #4CAF50;
  margin: 0;
  font-size: 15px;
}

.action-buttons {
  display: flex;
  gap: 16px;
}

.action-buttons .el-button {
  border-radius: 12px !important;
  font-weight: 600;
  padding: 12px 28px;
}

/* 统计区域 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-item {
  background: white;
  border-radius: 20px;
  padding: 32px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.stat-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 28px rgba(76, 175, 80, 0.15);
}

.stat-value {
  font-size: 36px;
  font-weight: 800;
  background: linear-gradient(135deg, #2E7D32, #4CAF50);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 8px;
}

.stat-label {
  color: #666;
  font-size: 14px;
  font-weight: 500;
}

/* 响应式 */
@media (max-width: 768px) {
  .hero-banner {
    padding: 32px 24px;
  }

  .banner-title {
    font-size: 24px;
  }

  .feature-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .action-content {
    flex-direction: column;
    text-align: center;
    gap: 24px;
  }

  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
