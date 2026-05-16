<template><DataPage title="学院专业" description="学院编号、学院名称、专业方向、负责老师、启用时间和学院状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCollegeMajor, deleteCollegeMajor, finishCollegeMajor, getCollegeMajorPage, processCollegeMajor, updateCollegeMajor } from '../api'
const api = { page: getCollegeMajorPage, add: addCollegeMajor, update: updateCollegeMajor, delete: deleteCollegeMajor, process: processCollegeMajor, finish: finishCollegeMajor }
const columns = [{ prop: 'recordNo', label: '学院编号' }, { prop: 'recordName', label: '学院名称' }, { prop: 'category', label: '专业方向' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '学院状态' }, { prop: 'remark', label: '学院说明' }]
const formFields = [{ prop: 'recordNo', label: '学院编号' }, { prop: 'recordName', label: '学院名称' }, { prop: 'category', label: '专业方向' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '学院状态', type: 'select' }, { prop: 'remark', label: '学院说明', type: 'textarea' }]
</script>
