<template><DataPage title="留样登记" description="留样编号、关联菜品、留样类型、留样人员、留样时间和留样状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSampleRetention, deleteSampleRetention, finishSampleRetention, getSampleRetentionPage, processSampleRetention, updateSampleRetention } from '../api'
const api = { page: getSampleRetentionPage, add: addSampleRetention, update: updateSampleRetention, delete: deleteSampleRetention, process: processSampleRetention, finish: finishSampleRetention }
const columns = [{ prop: 'recordNo', label: '留样编号' }, { prop: 'recordName', label: '关联菜品' }, { prop: 'category', label: '留样类型' }, { prop: 'ownerName', label: '留样人员' }, { prop: 'planTime', label: '留样时间' }, { prop: 'status', label: '留样状态' }, { prop: 'remark', label: '留样说明' }]
const formFields = [{ prop: 'recordNo', label: '留样编号' }, { prop: 'recordName', label: '关联菜品' }, { prop: 'category', label: '留样类型' }, { prop: 'ownerName', label: '留样人员' }, { prop: 'planTime', label: '留样时间' }, { prop: 'status', label: '留样状态', type: 'select' }, { prop: 'remark', label: '留样说明', type: 'textarea' }]
</script>
