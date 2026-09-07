<template>
  <div class="admin-auth-list">
    <el-card>
      <template #header>
        <span>实名认证审核</span>
      </template>

      <el-table :data="authList" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="realName" label="真实姓名" width="120" />
        <el-table-column prop="idCard" label="身份证号" width="180" />
        <el-table-column label="学生证照片" width="120">
          <template #default="{ row }">
            <el-button size="small" @click="viewImage(row.studentCardImg)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="人脸照片" width="120">
          <template #default="{ row }">
            <el-button size="small" @click="viewImage(row.faceImg)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" width="180" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleAudit(row.id, 1)">
              通过
            </el-button>
            <el-button type="danger" size="small" @click="showRejectDialog(row.id)">
              拒绝
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadAuthList"
        style="margin-top: 20px; justify-content: center;"
      />
    </el-card>

    <el-dialog v-model="rejectDialogVisible" title="拒绝原因" width="400px">
      <el-input type="textarea" v-model="rejectReason" :rows="4" placeholder="请输入拒绝原因" />
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmReject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAuthList, auditAuth } from '@/api/admin'
import { ElMessage } from 'element-plus'

const page = ref(1)
const size = ref(10)
const total = ref(0)
const authList = ref([])
const rejectDialogVisible = ref(false)
const rejectReason = ref('')
const currentAuthId = ref(null)

const loadAuthList = async () => {
  try {
    const res = await getAuthList({ page: page.value, size: size.value })
    authList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const viewImage = (url) => {
  window.open(url, '_blank')
}

const handleAudit = async (id, status) => {
  try {
    await auditAuth(id, { status })
    ElMessage.success('审核成功')
    loadAuthList()
  } catch (error) {
    console.error(error)
  }
}

const showRejectDialog = (id) => {
  currentAuthId.value = id
  rejectDialogVisible.value = true
}

const confirmReject = async () => {
  try {
    await auditAuth(currentAuthId.value, { 
      status: 2, 
      rejectReason: rejectReason.value 
    })
    ElMessage.success('已拒绝')
    rejectDialogVisible.value = false
    rejectReason.value = ''
    loadAuthList()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadAuthList()
})
</script>

<style scoped>
</style>

