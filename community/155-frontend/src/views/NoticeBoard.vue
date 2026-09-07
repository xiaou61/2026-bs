<template><DataPage title="通知公告" description="公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'ORGANIZER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addNoticeBoard, deleteNoticeBoard, finishNoticeBoard, getNoticeBoardPage, processNoticeBoard, updateNoticeBoard } from '../api'
const api = { page: getNoticeBoardPage, add: addNoticeBoard, update: updateNoticeBoard, delete: deleteNoticeBoard, process: processNoticeBoard, finish: finishNoticeBoard }
const columns = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告标题' }, { prop: 'category', label: '公告类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态' }, { prop: 'remark', label: '公告内容' }]
const formFields = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告标题' }, { prop: 'category', label: '公告类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态', type: 'select' }, { prop: 'remark', label: '公告内容', type: 'textarea' }]
</script>
