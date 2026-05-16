<template><DataPage title="技能项目" description="技能编号、技能名称、技能类型、维护人员、更新时间和技能状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSkillItem, deleteSkillItem, finishSkillItem, getSkillItemPage, processSkillItem, updateSkillItem } from '../api'
const api = { page: getSkillItemPage, add: addSkillItem, update: updateSkillItem, delete: deleteSkillItem, process: processSkillItem, finish: finishSkillItem }
const columns = [{ prop: 'recordNo', label: '技能编号' }, { prop: 'recordName', label: '技能名称' }, { prop: 'category', label: '技能类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '技能状态' }, { prop: 'remark', label: '技能说明' }]
const formFields = [{ prop: 'recordNo', label: '技能编号' }, { prop: 'recordName', label: '技能名称' }, { prop: 'category', label: '技能类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '技能状态', type: 'select' }, { prop: 'remark', label: '技能说明', type: 'textarea' }]
</script>
