<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-button type="success" @click="handleAdd">新增回访</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="agreementId" label="协议ID" min-width="100" />
        <el-table-column prop="applicationId" label="申请ID" min-width="100" />
        <el-table-column prop="followDate" label="回访日期" min-width="120" />
        <el-table-column prop="followStatus" label="回访状态" min-width="100" />
        <el-table-column prop="nextFollowDate" label="下次回访" min-width="120" />
        <el-table-column prop="followRemark" label="备注" min-width="220" show-overflow-tooltip />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="form.id ? '编辑回访' : '新增回访'" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="协议ID"><el-input-number v-model="form.agreementId" :min="1" /></el-form-item>
        <el-form-item label="申请ID"><el-input-number v-model="form.applicationId" :min="1" /></el-form-item>
        <el-form-item label="回访日期"><el-date-picker v-model="form.followDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="回访状态"><el-input v-model="form.followStatus" /></el-form-item>
        <el-form-item label="下次回访"><el-date-picker v-model="form.nextFollowDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.followRemark" type="textarea" :rows="4" /></el-form-item>
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
import { ElMessage } from 'element-plus'
import { addFollow, getFollowList, updateFollow } from '../../../api'

const list = ref([])
const visible = ref(false)
const form = reactive({})

const loadData = async () => {
  const res = await getFollowList({ pageNum: 1, pageSize: 30 })
  list.value = res.data?.list || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, agreementId: 1, applicationId: 1, followDate: '', followStatus: '正常', nextFollowDate: '', followRemark: '' })
  visible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row })
  visible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateFollow(form)
  } else {
    await addFollow(form)
  }
  ElMessage.success('保存成功')
  visible.value = false
  loadData()
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
