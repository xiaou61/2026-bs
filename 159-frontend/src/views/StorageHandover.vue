<template><DataPage title="暂存交接" description="交接编号、暂存点位、交接类型、交接人、交接时间和交接状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStorageHandover, deleteStorageHandover, finishStorageHandover, getStorageHandoverPage, processStorageHandover, updateStorageHandover } from '../api'
const api = { page: getStorageHandoverPage, add: addStorageHandover, update: updateStorageHandover, delete: deleteStorageHandover, process: processStorageHandover, finish: finishStorageHandover }
const columns = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '暂存点位' }, { prop: 'category', label: '交接类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态' }, { prop: 'remark', label: '交接说明' }]
const formFields = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '暂存点位' }, { prop: 'category', label: '交接类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态', type: 'select' }, { prop: 'remark', label: '交接说明', type: 'textarea' }]
</script>
