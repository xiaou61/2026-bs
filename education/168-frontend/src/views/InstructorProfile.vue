<template><DataPage title="讲师档案" description="讲师编号、讲师姓名、授课方向、负责人员、入驻时间和讲师状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'INSTRUCTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInstructorProfile, deleteInstructorProfile, finishInstructorProfile, getInstructorProfilePage, processInstructorProfile, updateInstructorProfile } from '../api'
const api = { page: getInstructorProfilePage, add: addInstructorProfile, update: updateInstructorProfile, delete: deleteInstructorProfile, process: processInstructorProfile, finish: finishInstructorProfile }
const columns = [{ prop: 'recordNo', label: '讲师编号' }, { prop: 'recordName', label: '讲师姓名' }, { prop: 'category', label: '授课方向' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '讲师状态' }, { prop: 'remark', label: '讲师说明' }]
const formFields = [{ prop: 'recordNo', label: '讲师编号' }, { prop: 'recordName', label: '讲师姓名' }, { prop: 'category', label: '授课方向' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '讲师状态', type: 'select' }, { prop: 'remark', label: '讲师说明', type: 'textarea' }]
</script>
