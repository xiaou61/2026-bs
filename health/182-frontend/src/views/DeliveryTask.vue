<template><DataPage title="配送任务" description="任务编号、助餐订单、配送类型、配送人员、配送时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DISPATCH', 'COURIER', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDeliveryTask, deleteDeliveryTask, finishDeliveryTask, getDeliveryTaskPage, processDeliveryTask, updateDeliveryTask } from '../api'
const api = { page: getDeliveryTaskPage, add: addDeliveryTask, update: updateDeliveryTask, delete: deleteDeliveryTask, process: processDeliveryTask, finish: finishDeliveryTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '助餐订单' }, { prop: 'category', label: '配送类型' }, { prop: 'ownerName', label: '配送人员' }, { prop: 'planTime', label: '配送时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '助餐订单' }, { prop: 'category', label: '配送类型' }, { prop: 'ownerName', label: '配送人员' }, { prop: 'planTime', label: '配送时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>
