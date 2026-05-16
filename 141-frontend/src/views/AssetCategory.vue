<template>
  <DataPage title="资产分类" description="维护资产分类编号、折旧方法、寿命规则和分类负责人，支撑固定资产口径统一" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAssetCategoryPage, addAssetCategory, updateAssetCategory, deleteAssetCategory, activateAssetCategory, finishAssetCategory } from '../api'

const api = { page: getAssetCategoryPage, add: addAssetCategory, update: updateAssetCategory, delete: deleteAssetCategory }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'depreciationMethod', label: '折旧方法' },
  { prop: 'usefulLife', label: '使用年限' },
  { prop: 'managerName', label: '负责人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'depreciationMethod', label: '折旧方法', type: 'select', options: [{ label: '年限平均法', value: '年限平均法' }, { label: '双倍余额递减法', value: '双倍余额递减法' }, { label: '工作量法', value: '工作量法' }] },
  { prop: 'usefulLife', label: '使用年限' },
  { prop: 'managerName', label: '负责人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateAssetCategory(row.id)
  }
  if (command === 'finish') {
    await finishAssetCategory(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
