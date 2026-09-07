<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-button type="success" @click="handleAdd">新增协议</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="agreementNo" label="协议编号" min-width="160" />
        <el-table-column prop="childName" label="儿童" min-width="100" />
        <el-table-column prop="applicantName" label="申请人" min-width="100" />
        <el-table-column label="签署状态" min-width="100">
          <template #default="{ row }">{{ signStatusMap[row.signStatus] }}</template>
        </el-table-column>
        <el-table-column prop="signDate" label="签署日期" min-width="120" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="form.id ? '编辑协议' : '新增协议'" width="620px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="申请ID"><el-input-number v-model="form.applicationId" :min="1" /></el-form-item>
        <el-form-item label="签署状态">
          <el-select v-model="form.signStatus">
            <el-option :value="0" label="待签署" />
            <el-option :value="1" label="已签署" />
          </el-select>
        </el-form-item>
        <el-form-item label="签署日期"><el-date-picker v-model="form.signDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="协议内容"><el-input v-model="form.agreementContent" type="textarea" :rows="5" /></el-form-item>
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
import { addAgreement, getAgreementList, updateAgreement } from '../../../api'
import { signStatusMap } from '../../../utils'

const list = ref([])
const visible = ref(false)
const form = reactive({})

const loadData = async () => {
  const res = await getAgreementList({ pageNum: 1, pageSize: 30 })
  list.value = res.data?.list || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, applicationId: 1, signStatus: 0, signDate: '', agreementContent: '' })
  visible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row })
  visible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateAgreement(form)
  } else {
    await addAgreement(form)
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
