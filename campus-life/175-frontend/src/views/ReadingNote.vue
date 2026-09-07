<template><DataPage title="读书笔记" description="笔记编号、阅读图书、笔记类型、提交读者、提交时间和笔记状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STUDENT', 'CLUB', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReadingNote, deleteReadingNote, finishReadingNote, getReadingNotePage, processReadingNote, updateReadingNote } from '../api'
const api = { page: getReadingNotePage, add: addReadingNote, update: updateReadingNote, delete: deleteReadingNote, process: processReadingNote, finish: finishReadingNote }
const columns = [{ prop: 'recordNo', label: '笔记编号' }, { prop: 'recordName', label: '阅读图书' }, { prop: 'category', label: '笔记类型' }, { prop: 'ownerName', label: '提交读者' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '笔记状态' }, { prop: 'remark', label: '笔记说明' }]
const formFields = [{ prop: 'recordNo', label: '笔记编号' }, { prop: 'recordName', label: '阅读图书' }, { prop: 'category', label: '笔记类型' }, { prop: 'ownerName', label: '提交读者' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '笔记状态', type: 'select' }, { prop: 'remark', label: '笔记说明', type: 'textarea' }]
</script>
