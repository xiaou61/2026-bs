<template><DataPage title="管线区段" description="区段编号、区段名称、管线类型、责任班组、启用时间和区段状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPipelineSection, deletePipelineSection, finishPipelineSection, getPipelineSectionPage, processPipelineSection, updatePipelineSection } from '../api'
const api = { page: getPipelineSectionPage, add: addPipelineSection, update: updatePipelineSection, delete: deletePipelineSection, process: processPipelineSection, finish: finishPipelineSection }
const columns = [{ prop: 'recordNo', label: '区段编号' }, { prop: 'recordName', label: '区段名称' }, { prop: 'category', label: '管线类型' }, { prop: 'ownerName', label: '责任班组' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区段状态' }, { prop: 'remark', label: '区段说明' }]
const formFields = [{ prop: 'recordNo', label: '区段编号' }, { prop: 'recordName', label: '区段名称' }, { prop: 'category', label: '管线类型' }, { prop: 'ownerName', label: '责任班组' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区段状态', type: 'select' }, { prop: 'remark', label: '区段说明', type: 'textarea' }]
</script>
