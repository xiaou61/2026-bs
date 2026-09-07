<template>
  <DataPage
    ref="pageRef"
    title="版权登记"
    description="提交图片版权登记申请，审核员可通过或驳回登记。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ registerStatus: 0, rightType: '原创著作权' }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addRegister, approveRegister, deleteRegister, getRegisterPage, rejectRegister, updateRegister } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待审核', type: 'warning' },
  1: { label: '已通过', type: 'success' },
  2: { label: '已驳回', type: 'danger' }
}
const api = { page: getRegisterPage, add: addRegister, update: updateRegister, delete: deleteRegister }
const rightOptions = [
  { label: '原创著作权', value: '原创著作权' },
  { label: '商业授权', value: '商业授权' },
  { label: '课程素材权', value: '课程素材权' }
]
const statusOptions = [
  { label: '待审核', value: 0 },
  { label: '已通过', value: 1 },
  { label: '已驳回', value: 2 }
]
const filters = [
  { type: 'input', prop: 'assetId', label: '作品ID' },
  { type: 'select', prop: 'registerStatus', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'assetId', label: '作品ID' },
  { prop: 'authorName', label: '作者' },
  { prop: 'rightType', label: '权利类型' },
  { prop: 'declaration', label: '声明', minWidth: 240, long: true },
  { prop: 'registerStatus', label: '登记状态', map: statusMap },
  { prop: 'reviewComment', label: '审核意见', minWidth: 180, long: true },
  { prop: 'createTime', label: '提交时间', minWidth: 170 }
]
const formFields = [
  { prop: 'assetId', label: '作品ID', type: 'number', min: 1, required: true },
  { prop: 'authorName', label: '作者姓名', required: true },
  { prop: 'rightType', label: '权利类型', type: 'select', options: rightOptions, required: true },
  { prop: 'declaration', label: '权利声明', type: 'textarea', rows: 4, required: true },
  { prop: 'registerStatus', label: '登记状态', type: 'select', options: statusOptions, required: true },
  { prop: 'reviewComment', label: '审核意见', type: 'textarea', rows: 3 }
]
const rowActions = [
  { name: 'approve', label: '通过', type: 'success' },
  { name: 'reject', label: '驳回', type: 'danger' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入审核意见', '登记审核', {
    inputValue: name === 'approve' ? '登记通过' : '登记驳回',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  if (name === 'approve') await approveRegister(row.id, value)
  if (name === 'reject') await rejectRegister(row.id, value)
  ElMessage.success('审核成功')
  pageRef.value.loadData()
}
</script>
