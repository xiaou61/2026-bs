<template><DataPage title="成绩公示" description="公示编号、公示项目、名次组别、发布人员、公示时间和公示状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'REFEREE', 'COMMITTEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addResultPublish, deleteResultPublish, finishResultPublish, getResultPublishPage, processResultPublish, updateResultPublish } from '../api'
const api = { page: getResultPublishPage, add: addResultPublish, update: updateResultPublish, delete: deleteResultPublish, process: processResultPublish, finish: finishResultPublish }
const columns = [{ prop: 'recordNo', label: '公示编号' }, { prop: 'recordName', label: '公示项目' }, { prop: 'category', label: '名次组别' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '公示时间' }, { prop: 'status', label: '公示状态' }, { prop: 'remark', label: '公示说明' }]
const formFields = [{ prop: 'recordNo', label: '公示编号' }, { prop: 'recordName', label: '公示项目' }, { prop: 'category', label: '名次组别' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '公示时间' }, { prop: 'status', label: '公示状态', type: 'select' }, { prop: 'remark', label: '公示说明', type: 'textarea' }]
</script>
