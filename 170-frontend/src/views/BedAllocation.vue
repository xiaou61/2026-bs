<template><DataPage title="床位分配" description="分配编号、分配床位、分配类型、办理人员、分配时间和分配状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBedAllocation, deleteBedAllocation, finishBedAllocation, getBedAllocationPage, processBedAllocation, updateBedAllocation } from '../api'
const api = { page: getBedAllocationPage, add: addBedAllocation, update: updateBedAllocation, delete: deleteBedAllocation, process: processBedAllocation, finish: finishBedAllocation }
const columns = [{ prop: 'recordNo', label: '分配编号' }, { prop: 'recordName', label: '分配床位' }, { prop: 'category', label: '分配类型' }, { prop: 'ownerName', label: '办理人员' }, { prop: 'planTime', label: '分配时间' }, { prop: 'status', label: '分配状态' }, { prop: 'remark', label: '分配说明' }]
const formFields = [{ prop: 'recordNo', label: '分配编号' }, { prop: 'recordName', label: '分配床位' }, { prop: 'category', label: '分配类型' }, { prop: 'ownerName', label: '办理人员' }, { prop: 'planTime', label: '分配时间' }, { prop: 'status', label: '分配状态', type: 'select' }, { prop: 'remark', label: '分配说明', type: 'textarea' }]
</script>
