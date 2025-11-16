import request from '@/utils/request'

// 获取自习室列表
export function getStudyRooms(params) {
  return request({
    url: '/studyroom/list',
    method: 'get',
    params
  })
}

// 获取自习室详情
export function getRoomDetail(roomId) {
  return request({
    url: `/studyroom/${roomId}`,
    method: 'get'
  })
}

// 创建自习室（管理员）
export function createStudyRoom(data) {
  return request({
    url: '/studyroom/create',
    method: 'post',
    data
  })
}

// 更新自习室（管理员）
export function updateStudyRoom(roomId, data) {
  return request({
    url: `/studyroom/${roomId}`,
    method: 'put',
    data
  })
}

// 删除自习室（管理员）
export function deleteStudyRoom(roomId) {
  return request({
    url: `/studyroom/${roomId}`,
    method: 'delete'
  })
}

// 获取自习室统计信息
export function getRoomStatistics(roomId) {
  return request({
    url: `/studyroom/${roomId}/statistics`,
    method: 'get'
  })
}