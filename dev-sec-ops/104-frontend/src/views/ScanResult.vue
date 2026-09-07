<template>
  <DataPage title="扫描结果" description="扫描结果数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getScanResultPage, addScanResult, updateScanResult, deleteScanResult } from '../api'
const api = { page: getScanResultPage, add: addScanResult, update: updateScanResult, delete: deleteScanResult }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'taskId', label: '任务ID' }, { prop: 'packageName', label: '依赖名称' }, { prop: 'packageVersion', label: '版本号' }, { prop: 'licenseName', label: '许可证' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'taskId', label: '任务ID', type: 'number' }, { prop: 'packageName', label: '依赖名称' }, { prop: 'packageVersion', label: '版本号' }, { prop: 'licenseName', label: '许可证' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'ruleMatched', label: '命中规则' }, { prop: 'evidence', label: '证据说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
