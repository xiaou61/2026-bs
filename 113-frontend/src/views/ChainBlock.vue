<template>
  <DataPage title="区块存证" description="溯源数据哈希、区块高度、交易编号和存证状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChainBlockPage, addChainBlock, updateChainBlock, deleteChainBlock, confirmChainBlock, failChainBlock } from '../api'
const api = { page: getChainBlockPage, add: addChainBlock, update: updateChainBlock, delete: deleteChainBlock }
const columns = [{"prop": "blockNo", "label": "区块编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "txHash", "label": "交易哈希"}, {"prop": "dataHash", "label": "数据哈希"}, {"prop": "blockHeight", "label": "区块高度"}, {"prop": "chainTime", "label": "上链时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "blockNo", "label": "区块编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "txHash", "label": "交易哈希"}, {"prop": "dataHash", "label": "数据哈希"}, {"prop": "blockHeight", "label": "区块高度", "type": "number"}, {"prop": "chainTime", "label": "上链时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "fail", "label": "失败", "type": "primary"}]
const defaults = {"status": "CONFIRMED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmChainBlock(row.id)
  if (command === 'fail') await failChainBlock(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
