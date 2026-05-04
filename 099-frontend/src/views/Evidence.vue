<template>
  <DataPage
    ref="pageRef"
    title="电子存证"
    description="对已通过的版权登记生成存证编号和哈希凭证。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :tool-actions="toolActions"
    :defaults="{ evidenceStatus: 1, platformName: '校级版权存证链' }"
    @tool-action="handleTool"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addEvidence, deleteEvidence, generateEvidence, getEvidencePage, updateEvidence } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待存证', type: 'warning' },
  1: { label: '已存证', type: 'success' }
}
const api = { page: getEvidencePage, add: addEvidence, update: updateEvidence, delete: deleteEvidence }
const statusOptions = [
  { label: '待存证', value: 0 },
  { label: '已存证', value: 1 }
]
const filters = [
  { type: 'input', prop: 'registerId', label: '登记ID' },
  { type: 'select', prop: 'evidenceStatus', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'registerId', label: '登记ID' },
  { prop: 'evidenceNo', label: '存证编号', minWidth: 190 },
  { prop: 'hashValue', label: '哈希值', minWidth: 260, long: true },
  { prop: 'platformName', label: '存证平台', minWidth: 160 },
  { prop: 'evidenceStatus', label: '状态', map: statusMap },
  { prop: 'createTime', label: '存证时间', minWidth: 170 }
]
const formFields = [
  { prop: 'registerId', label: '登记ID', type: 'number', min: 1, required: true },
  { prop: 'evidenceNo', label: '存证编号' },
  { prop: 'hashValue', label: '哈希值', type: 'textarea', rows: 3 },
  { prop: 'platformName', label: '存证平台', required: true },
  { prop: 'evidenceStatus', label: '状态', type: 'select', options: statusOptions, required: true }
]
const toolActions = [
  { name: 'generate', label: '生成存证', type: 'success' }
]

const handleTool = async (name) => {
  if (name !== 'generate') return
  const { value } = await ElMessageBox.prompt('请输入已通过登记ID', '生成电子存证', {
    inputPattern: /^[1-9]\d*$/,
    inputErrorMessage: '请输入正确的登记ID',
    confirmButtonText: '生成',
    cancelButtonText: '取消'
  })
  await generateEvidence(value)
  ElMessage.success('生成成功')
  pageRef.value.loadData()
}
</script>
