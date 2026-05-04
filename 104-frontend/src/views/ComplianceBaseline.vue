<template>
  <DataPage title="合规基线" description="合规基线数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getComplianceBaselinePage, addComplianceBaseline, updateComplianceBaseline, deleteComplianceBaseline } from '../api'
const api = { page: getComplianceBaselinePage, add: addComplianceBaseline, update: updateComplianceBaseline, delete: deleteComplianceBaseline }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'baselineName', label: '基线名称' }, { prop: 'repositoryId', label: '项目ID' }, { prop: 'licenseScope', label: '许可证范围' }, { prop: 'requiredLevel', label: '要求等级' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'baselineName', label: '基线名称' }, { prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'licenseScope', label: '许可证范围' }, { prop: 'requiredLevel', label: '要求等级' }, { prop: 'effectiveDate', label: '生效日期' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
