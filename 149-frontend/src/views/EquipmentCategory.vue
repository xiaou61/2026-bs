<template>
  <DataPage
    title="设备分类"
    description="分类编号、分类名称、设备类型、存放区域、状态标签和状态维护"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEquipmentCategoryPage, addEquipmentCategory, updateEquipmentCategory, deleteEquipmentCategory, activateEquipmentCategory, finishEquipmentCategory } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getEquipmentCategoryPage, add: addEquipmentCategory, update: updateEquipmentCategory, delete: deleteEquipmentCategory }
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'equipmentType', label: '设备类型' },
  { prop: 'storageArea', label: '存放区域' },
  { prop: 'statusTag', label: '状态标签' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'equipmentType', label: '设备类型' },
  { prop: 'storageArea', label: '存放区域' },
  { prop: 'statusTag', label: '状态标签' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用中', value: 'ACTIVE' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEquipmentCategory(row.id)
  if (command === 'finish') await finishEquipmentCategory(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
