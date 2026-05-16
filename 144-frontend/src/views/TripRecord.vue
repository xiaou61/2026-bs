<template>
  <DataPage title="行程记录" description="维护行程编号、用户编号、出行日期、路线轨迹和完成情况，支撑出行过程留痕与复盘" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getTripRecordPage, addTripRecord, updateTripRecord, deleteTripRecord, processTripRecord, finishTripRecord } from '../api'

const api = { page: getTripRecordPage, add: addTripRecord, update: updateTripRecord, delete: deleteTripRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAVELER', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'tripNo', label: '行程编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'travelDate', label: '出行日期', width: 140 },
  { prop: 'travelRoute', label: '路线轨迹', width: 220 },
  { prop: 'completionStatus', label: '完成情况', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'tripNo', label: '行程编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'travelDate', label: '出行日期' },
  { prop: 'travelRoute', label: '路线轨迹', type: 'textarea' },
  { prop: 'completionStatus', label: '完成情况' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '处理', type: 'warning' },
  { command: 'finish', label: '完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processTripRecord(row.id)
  if (command === 'finish') await finishTripRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
