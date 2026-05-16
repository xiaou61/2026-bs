<template><DataPage title="导师匹配" description="导师匹配编号、导师匹配名称、业务类型、负责人、计划时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBizRecord02, deleteBizRecord02, finishBizRecord02, getBizRecord02Page, processBizRecord02, updateBizRecord02 } from '../api'
const api = { page: getBizRecord02Page, add: addBizRecord02, update: updateBizRecord02, delete: deleteBizRecord02, process: processBizRecord02, finish: finishBizRecord02 }
const columns = [{ prop: 'recordNo', label: '编号' }, { prop: 'recordName', label: '名称' }, { prop: 'category', label: '类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '状态' }, { prop: 'remark', label: '备注' }]
const formFields = [{ prop: 'recordNo', label: '编号' }, { prop: 'recordName', label: '名称' }, { prop: 'category', label: '类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已提交', value: 'SUBMITTED' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }, { prop: 'remark', label: '备注', type: 'textarea' }]
</script>
