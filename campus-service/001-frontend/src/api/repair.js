import request from '@/utils/request'

// 提交报修申请
export const submitRepair = (data) => {
  return request({
    url: '/repair/submit',
    method: 'post',
    data
  })
}

// 获取报修列表
export const getRepairList = (params) => {
  return request({
    url: '/repair/list',
    method: 'get',
    params
  })
}

// 处理报修
export const handleRepair = (data) => {
  return request({
    url: '/repair/handle',
    method: 'post',
    data
  })
}

// 获取报修统计
export const getRepairStatistics = () => {
  return request({
    url: '/repair/statistics',
    method: 'get'
  })
}

