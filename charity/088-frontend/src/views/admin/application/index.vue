<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-select v-model="query.status" placeholder="申请状态" clearable>
          <el-option v-for="(label, value) in applicationStatusMap" :key="value" :label="label" :value="Number(value)" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="applicationNo" label="申请编号" min-width="160" />
        <el-table-column prop="applicantName" label="申请人" min-width="100" />
        <el-table-column prop="childName" label="儿童" min-width="100" />
        <el-table-column label="状态" min-width="100">
          <template #default="{ row }">{{ applicationStatusMap[row.status] }}</template>
        </el-table-column>
        <el-table-column prop="submitTime" label="提交时间" min-width="160" />
        <el-table-column label="材料" min-width="100">
          <template #default="{ row }">
            <el-button link @click="openMaterial(row)">查看材料</el-button>
          </template>
        </el-table-column>
        <el-table-column label="审核" width="180">
          <template #default="{ row }">
            <el-button link type="success" @click="handleReview(row, 1)">通过</el-button>
            <el-button link type="danger" @click="handleReview(row, 2)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="materialVisible" title="申请材料" width="680px">
      <el-table :data="materials">
        <el-table-column prop="materialName" label="材料名称" min-width="140" />
        <el-table-column prop="materialType" label="类型" min-width="120" />
        <el-table-column prop="fileUrl" label="链接" min-width="240" show-overflow-tooltip />
        <el-table-column prop="remark" label="备注" min-width="120" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getApplicationList, getMaterialList, reviewApplication } from '../../../api'
import { applicationStatusMap } from '../../../utils'

const list = ref([])
const materialVisible = ref(false)
const materials = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  status: null
})

const loadData = async () => {
  const res = await getApplicationList(query)
  list.value = res.data?.list || []
}

const openMaterial = async (row) => {
  const res = await getMaterialList({ applicationId: row.id })
  materials.value = res.data || []
  materialVisible.value = true
}

const handleReview = (row, reviewStatus) => {
  ElMessageBox.prompt('请输入审核意见', '审核申请', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(async ({ value }) => {
    await reviewApplication({ applicationId: row.id, reviewStatus, reviewRemark: value })
    ElMessage.success('处理成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
