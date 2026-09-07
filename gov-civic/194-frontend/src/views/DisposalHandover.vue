<template><DataPage title="处置交接" description="交接编号、转运联单、接收单位、交接人员、交接时间和交接状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'TRANSPORT', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDisposalHandover, deleteDisposalHandover, finishDisposalHandover, getDisposalHandoverPage, processDisposalHandover, updateDisposalHandover } from '../api'
const api = { page: getDisposalHandoverPage, add: addDisposalHandover, update: updateDisposalHandover, delete: deleteDisposalHandover, process: processDisposalHandover, finish: finishDisposalHandover }
const columns = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '转运联单' }, { prop: 'category', label: '接收单位' }, { prop: 'ownerName', label: '交接人员' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态' }, { prop: 'remark', label: '交接说明' }]
const formFields = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '转运联单' }, { prop: 'category', label: '接收单位' }, { prop: 'ownerName', label: '交接人员' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态', type: 'select' }, { prop: 'remark', label: '交接说明', type: 'textarea' }]
</script>
