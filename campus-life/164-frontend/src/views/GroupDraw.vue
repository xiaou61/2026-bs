<template><DataPage title="报名分组" description="分组编号、分组名称、竞赛项目、分组负责人、抽签时间和分组状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGroupDraw, deleteGroupDraw, finishGroupDraw, getGroupDrawPage, processGroupDraw, updateGroupDraw } from '../api'
const api = { page: getGroupDrawPage, add: addGroupDraw, update: updateGroupDraw, delete: deleteGroupDraw, process: processGroupDraw, finish: finishGroupDraw }
const columns = [{ prop: 'recordNo', label: '分组编号' }, { prop: 'recordName', label: '分组名称' }, { prop: 'category', label: '竞赛项目' }, { prop: 'ownerName', label: '分组负责人' }, { prop: 'planTime', label: '抽签时间' }, { prop: 'status', label: '分组状态' }, { prop: 'remark', label: '分组说明' }]
const formFields = [{ prop: 'recordNo', label: '分组编号' }, { prop: 'recordName', label: '分组名称' }, { prop: 'category', label: '竞赛项目' }, { prop: 'ownerName', label: '分组负责人' }, { prop: 'planTime', label: '抽签时间' }, { prop: 'status', label: '分组状态', type: 'select' }, { prop: 'remark', label: '分组说明', type: 'textarea' }]
</script>
