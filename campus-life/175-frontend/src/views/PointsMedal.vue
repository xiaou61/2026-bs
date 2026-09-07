<template><DataPage title="积分勋章" description="勋章编号、获奖读者、积分类型、评定人员、获得时间和勋章状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPointsMedal, deletePointsMedal, finishPointsMedal, getPointsMedalPage, processPointsMedal, updatePointsMedal } from '../api'
const api = { page: getPointsMedalPage, add: addPointsMedal, update: updatePointsMedal, delete: deletePointsMedal, process: processPointsMedal, finish: finishPointsMedal }
const columns = [{ prop: 'recordNo', label: '勋章编号' }, { prop: 'recordName', label: '获奖读者' }, { prop: 'category', label: '积分类型' }, { prop: 'ownerName', label: '评定人员' }, { prop: 'planTime', label: '获得时间' }, { prop: 'status', label: '勋章状态' }, { prop: 'remark', label: '勋章说明' }]
const formFields = [{ prop: 'recordNo', label: '勋章编号' }, { prop: 'recordName', label: '获奖读者' }, { prop: 'category', label: '积分类型' }, { prop: 'ownerName', label: '评定人员' }, { prop: 'planTime', label: '获得时间' }, { prop: 'status', label: '勋章状态', type: 'select' }, { prop: 'remark', label: '勋章说明', type: 'textarea' }]
</script>
