<template><DataPage title="通知公告" description="通知公告编号、通知公告名称、业务类型、负责人、计划时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBizRecord12, deleteBizRecord12, finishBizRecord12, getBizRecord12Page, processBizRecord12, updateBizRecord12 } from '../api'
const api = { page: getBizRecord12Page, add: addBizRecord12, update: updateBizRecord12, delete: deleteBizRecord12, process: processBizRecord12, finish: finishBizRecord12 }
const columns = [{ prop: 'recordNo', label: '编号' }, { prop: 'recordName', label: '名称' }, { prop: 'category', label: '类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '状态' }, { prop: 'remark', label: '备注' }]
const formFields = [{ prop: 'recordNo', label: '编号' }, { prop: 'recordName', label: '名称' }, { prop: 'category', label: '类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已提交', value: 'SUBMITTED' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }, { prop: 'remark', label: '备注', type: 'textarea' }]
</script>
