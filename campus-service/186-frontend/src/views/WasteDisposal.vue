<template><DataPage title="厨余处置" description="处置编号、后厨区域、处置类型、处置人员、处置时间和处置状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'WAREHOUSE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWasteDisposal, deleteWasteDisposal, finishWasteDisposal, getWasteDisposalPage, processWasteDisposal, updateWasteDisposal } from '../api'
const api = { page: getWasteDisposalPage, add: addWasteDisposal, update: updateWasteDisposal, delete: deleteWasteDisposal, process: processWasteDisposal, finish: finishWasteDisposal }
const columns = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态' }, { prop: 'remark', label: '处置说明' }]
const formFields = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态', type: 'select' }, { prop: 'remark', label: '处置说明', type: 'textarea' }]
</script>
