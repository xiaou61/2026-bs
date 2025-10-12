<template>
  <div class="batch-import-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">批量导入快递</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-steps :active="activeStep" align-center>
        <el-step title="下载模板" />
        <el-step title="填写数据" />
        <el-step title="上传文件" />
        <el-step title="导入完成" />
      </el-steps>

      <div class="step-content">
        <div v-if="activeStep === 0" class="step-1">
          <el-result icon="info" title="第一步：下载导入模板">
            <template #sub-title>
              <p>请先下载Excel模板，然后在模板中填写快递信息</p>
            </template>
            <template #extra>
              <el-button type="primary" @click="downloadTemplate">
                <el-icon><Download /></el-icon>
                下载模板
              </el-button>
              <el-button @click="activeStep = 1">下一步</el-button>
            </template>
          </el-result>
        </div>

        <div v-if="activeStep === 1" class="step-2">
          <el-result icon="info" title="第二步：填写快递信息">
            <template #sub-title>
              <div>
                <p>在模板中填写以下必填字段：</p>
                <el-descriptions :column="1" border style="margin-top: 20px; max-width: 600px; margin: 20px auto">
                  <el-descriptions-item label="快递单号">必填，快递的唯一标识</el-descriptions-item>
                  <el-descriptions-item label="快递公司">必填，如：顺丰速运、圆通速递</el-descriptions-item>
                  <el-descriptions-item label="收件人手机号">必填，用于匹配收件人</el-descriptions-item>
                  <el-descriptions-item label="代收点ID">必填，代收点编号</el-descriptions-item>
                  <el-descriptions-item label="货架位置">必填，如：A-01-01</el-descriptions-item>
                </el-descriptions>
              </div>
            </template>
            <template #extra>
              <el-button @click="activeStep = 0">上一步</el-button>
              <el-button type="primary" @click="activeStep = 2">下一步</el-button>
            </template>
          </el-result>
        </div>

        <div v-if="activeStep === 2" class="step-3">
          <el-result icon="info" title="第三步：上传Excel文件">
            <template #sub-title>
              <el-upload
                ref="uploadRef"
                class="upload-demo"
                drag
                :action="uploadUrl"
                :headers="uploadHeaders"
                :on-success="handleSuccess"
                :on-error="handleError"
                :before-upload="beforeUpload"
                :limit="1"
                accept=".xlsx,.xls"
                :auto-upload="false"
              >
                <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
                <div class="el-upload__text">
                  将文件拖到此处，或<em>点击上传</em>
                </div>
                <template #tip>
                  <div class="el-upload__tip">
                    只能上传xlsx/xls文件
                  </div>
                </template>
              </el-upload>
            </template>
            <template #extra>
              <el-button @click="activeStep = 1">上一步</el-button>
              <el-button type="primary" @click="submitUpload" :loading="uploading">
                开始导入
              </el-button>
            </template>
          </el-result>
        </div>

        <div v-if="activeStep === 3" class="step-4">
          <el-result v-if="importResult.successCount > 0" icon="success" title="导入完成">
            <template #sub-title>
              <div>
                <p>成功导入 {{ importResult.successCount }} 条，失败 {{ importResult.failCount }} 条</p>
              </div>
            </template>
            <template #extra>
              <el-button type="primary" @click="viewDetails">查看详情</el-button>
              <el-button @click="reset">继续导入</el-button>
            </template>
          </el-result>
          <el-result v-else icon="error" title="导入失败">
            <template #sub-title>
              <p>{{ errorMessage }}</p>
            </template>
            <template #extra>
              <el-button type="primary" @click="activeStep = 2">重新上传</el-button>
            </template>
          </el-result>
        </div>
      </div>
    </el-card>

    <el-dialog v-model="showDetailDialog" title="导入结果详情" width="800px">
      <el-tabs>
        <el-tab-pane label="成功列表" :badge="importResult.successCount">
          <el-table :data="importResult.successList" max-height="400">
            <el-table-column prop="trackingNumber" label="快递单号" />
            <el-table-column prop="pickupCode" label="取件码" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="失败列表" :badge="importResult.failCount">
          <el-table :data="importResult.failList" max-height="400">
            <el-table-column prop="row" label="行号" width="80" />
            <el-table-column prop="trackingNumber" label="快递单号" />
            <el-table-column prop="reason" label="失败原因" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Download, UploadFilled } from '@element-plus/icons-vue'

const activeStep = ref(0)
const uploading = ref(false)
const uploadRef = ref()
const showDetailDialog = ref(false)
const errorMessage = ref('')

const uploadUrl = computed(() => {
  return '/api/express/batch-import'
})

const uploadHeaders = computed(() => {
  return {
    'Authorization': localStorage.getItem('token') || ''
  }
})

const importResult = reactive({
  successCount: 0,
  failCount: 0,
  successList: [],
  failList: []
})

const downloadTemplate = () => {
  window.open('/api/express/download-template', '_blank')
  ElMessage.success('模板下载中...')
}

const beforeUpload = (file) => {
  const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || 
                  file.type === 'application/vnd.ms-excel'
  if (!isExcel) {
    ElMessage.error('只能上传Excel文件')
    return false
  }
  return true
}

const submitUpload = () => {
  uploadRef.value.submit()
  uploading.value = true
}

const handleSuccess = (response) => {
  uploading.value = false
  if (response.code === 200) {
    Object.assign(importResult, response.data)
    activeStep.value = 3
    ElMessage.success('导入完成')
  } else {
    errorMessage.value = response.message
    activeStep.value = 3
  }
}

const handleError = (error) => {
  uploading.value = false
  errorMessage.value = '导入失败，请检查文件格式'
  activeStep.value = 3
  console.error(error)
}

const viewDetails = () => {
  showDetailDialog.value = true
}

const reset = () => {
  activeStep.value = 0
  uploadRef.value.clearFiles()
  Object.assign(importResult, {
    successCount: 0,
    failCount: 0,
    successList: [],
    failList: []
  })
}
</script>

<style scoped>
.batch-import-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}

.step-content {
  margin-top: 40px;
}

.upload-demo {
  text-align: center;
}

.el-icon--upload {
  font-size: 67px;
  color: #409EFF;
}
</style>

