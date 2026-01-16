<template>
  <div class="audit-detail-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>管理后台 - 审核详情</h2>
          <div class="user-info">
            <span>管理员：{{ userStore.userInfo.realName || userStore.userInfo.username }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu :default-active="activeMenu" router>
            <el-menu-item index="/admin/dashboard">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据看板</span>
            </el-menu-item>
            <el-menu-item index="/admin/audit">
              <el-icon><DocumentChecked /></el-icon>
              <span>举报审核</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-main>
          <el-card v-loading="loading">
            <template #header>
              <div class="card-header">
                <span>举报详情</span>
                <el-button size="small" @click="$router.back()">返回</el-button>
              </div>
            </template>

            <el-descriptions :column="2" border v-if="report">
              <el-descriptions-item label="举报编号">
                {{ report.reportNo }}
              </el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag v-if="report.status === 'PENDING'" type="warning">待审核</el-tag>
                <el-tag v-else-if="report.status === 'APPROVED'" type="success">已通过</el-tag>
                <el-tag v-else type="danger">已拒绝</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="车牌号">
                {{ report.plateNumber }}
              </el-descriptions-item>
              <el-descriptions-item label="违停类型">
                {{ report.violationType?.name || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="违停位置" :span="2">
                {{ report.location }}
              </el-descriptions-item>
              <el-descriptions-item label="详细描述" :span="2">
                {{ report.description || '无' }}
              </el-descriptions-item>
              <el-descriptions-item label="举报人">
                {{ report.reporter?.username || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="举报人手机">
                {{ report.reporter?.phone || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="提交时间">
                {{ formatTime(report.createdAt) }}
              </el-descriptions-item>
              <el-descriptions-item label="更新时间">
                {{ formatTime(report.updatedAt) }}
              </el-descriptions-item>
            </el-descriptions>

            <div v-if="report?.images && report.images.length > 0" style="margin-top: 20px">
              <el-divider content-position="left">证据照片</el-divider>
              <el-image 
                v-for="(img, index) in report.images" 
                :key="index"
                :src="img"
                :preview-src-list="report.images"
                :initial-index="index"
                fit="cover"
                style="width: 200px; height: 150px; margin-right: 10px; cursor: pointer"
              />
            </div>

            <div v-if="report?.status === 'PENDING'" style="margin-top: 30px">
              <el-divider content-position="left">审核操作</el-divider>
              <el-form :model="auditForm" label-width="100px">
                <el-form-item label="审核结果" required>
                  <el-radio-group v-model="auditForm.status">
                    <el-radio value="APPROVED">通过</el-radio>
                    <el-radio value="REJECTED">拒绝</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="审核意见">
                  <el-input 
                    v-model="auditForm.auditComment"
                    type="textarea"
                    :rows="4"
                    placeholder="请输入审核意见（选填）"
                  />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleAudit" :loading="submitting">
                    提交审核
                  </el-button>
                  <el-button @click="$router.back()">取消</el-button>
                </el-form-item>
              </el-form>
            </div>

            <div v-else-if="report?.auditComment" style="margin-top: 20px">
              <el-divider content-position="left">审核意见</el-divider>
              <el-alert :type="report.status === 'APPROVED' ? 'success' : 'error'" :closable="false">
                {{ report.auditComment }}
              </el-alert>
            </div>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { DataAnalysis, DocumentChecked } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getReportDetail, auditReport } from '@/api/report'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const activeMenu = ref('/admin/audit')

const loading = ref(false)
const submitting = ref(false)
const report = ref(null)

const auditForm = ref({
  status: 'APPROVED',
  auditComment: ''
})

const loadReport = async () => {
  loading.value = true
  try {
    const res = await getReportDetail(route.params.id)
    report.value = res.data
  } catch (error) {
    console.error('加载举报详情失败', error)
    ElMessage.error('加载举报详情失败')
  } finally {
    loading.value = false
  }
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const handleAudit = async () => {
  if (!auditForm.value.status) {
    ElMessage.warning('请选择审核结果')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要${auditForm.value.status === 'APPROVED' ? '通过' : '拒绝'}此举报吗？`,
      '确认审核',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    submitting.value = true
    await auditReport(route.params.id, {
      status: auditForm.value.status,
      reason: auditForm.value.auditComment
    })

    ElMessage.success('审核成功')
    router.push('/admin/audit')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('审核失败', error)
      ElMessage.error('审核失败')
    }
  } finally {
    submitting.value = false
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/admin/login')
}

onMounted(() => {
  loadReport()
})
</script>

<style scoped>
.audit-detail-container {
  min-height: 100vh;
  background: #f0f2f5;
}

.el-header {
  background: white;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.user-info {
  display: flex;
  align-items: center;
}

.el-aside {
  background: white;
  box-shadow: 2px 0 8px rgba(0,0,0,0.1);
}

.el-menu {
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
