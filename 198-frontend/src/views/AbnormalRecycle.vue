<template><DataPage title="异常回收" description="回收编号、异常设备、异常类型、回收人员、回收时间和回收状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAbnormalRecycle, deleteAbnormalRecycle, finishAbnormalRecycle, getAbnormalRecyclePage, processAbnormalRecycle, updateAbnormalRecycle } from '../api'
const api = { page: getAbnormalRecyclePage, add: addAbnormalRecycle, update: updateAbnormalRecycle, delete: deleteAbnormalRecycle, process: processAbnormalRecycle, finish: finishAbnormalRecycle }
const columns = [{ prop: 'recordNo', label: '回收编号' }, { prop: 'recordName', label: '异常设备' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '回收人员' }, { prop: 'planTime', label: '回收时间' }, { prop: 'status', label: '回收状态' }, { prop: 'remark', label: '回收说明' }]
const formFields = [{ prop: 'recordNo', label: '回收编号' }, { prop: 'recordName', label: '异常设备' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '回收人员' }, { prop: 'planTime', label: '回收时间' }, { prop: 'status', label: '回收状态', type: 'select' }, { prop: 'remark', label: '回收说明', type: 'textarea' }]
</script>
