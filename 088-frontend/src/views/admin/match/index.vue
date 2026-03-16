<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-button type="success" @click="handleAdd">新增匹配</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="applicationId" label="申请ID" min-width="100" />
        <el-table-column prop="childName" label="儿童" min-width="100" />
        <el-table-column prop="reviewerName" label="审核员" min-width="100" />
        <el-table-column prop="matchScore" label="匹配分数" min-width="100" />
        <el-table-column prop="matchRemark" label="说明" min-width="220" show-overflow-tooltip />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="success" @click="handleApprove(row, 1)">通过</el-button>
            <el-button link type="danger" @click="handleApprove(row, 2)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" title="新增匹配" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="申请ID"><el-input-number v-model="form.applicationId" :min="1" /></el-form-item>
        <el-form-item label="儿童ID"><el-input-number v-model="form.childId" :min="1" /></el-form-item>
        <el-form-item label="匹配分数"><el-input-number v-model="form.matchScore" :min="0" :max="100" /></el-form-item>
        <el-form-item label="匹配说明"><el-input v-model="form.matchRemark" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addMatch, approveMatch, getMatchList } from '../../../api'

const list = ref([])
const visible = ref(false)
const form = reactive({
  applicationId: 1,
  childId: 1,
  matchScore: 90,
  matchRemark: ''
})

const loadData = async () => {
  const res = await getMatchList({ pageNum: 1, pageSize: 30 })
  list.value = res.data?.list || []
}

const handleAdd = () => {
  Object.assign(form, { applicationId: 1, childId: 1, matchScore: 90, matchRemark: '' })
  visible.value = true
}

const handleSubmit = async () => {
  await addMatch(form)
  ElMessage.success('保存成功')
  visible.value = false
  loadData()
}

const handleApprove = (row, status) => {
  ElMessageBox.prompt('请输入审批意见', '匹配审批', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(async ({ value }) => {
    await approveMatch({ matchId: row.id, status, remark: value })
    ElMessage.success('处理成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}
</style>
