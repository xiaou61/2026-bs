<template><DataPage title="康复中心" description="中心编号、中心名称、服务方向、负责人、建档时间和中心状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRehabCenter, deleteRehabCenter, finishRehabCenter, getRehabCenterPage, processRehabCenter, updateRehabCenter } from '../api'
const api = { page: getRehabCenterPage, add: addRehabCenter, update: updateRehabCenter, delete: deleteRehabCenter, process: processRehabCenter, finish: finishRehabCenter }
const columns = [{ prop: 'recordNo', label: '中心编号' }, { prop: 'recordName', label: '中心名称' }, { prop: 'category', label: '服务方向' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '中心状态' }, { prop: 'remark', label: '中心说明' }]
const formFields = [{ prop: 'recordNo', label: '中心编号' }, { prop: 'recordName', label: '中心名称' }, { prop: 'category', label: '服务方向' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '中心状态', type: 'select' }, { prop: 'remark', label: '中心说明', type: 'textarea' }]
</script>
