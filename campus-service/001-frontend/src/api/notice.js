import request from '@/utils/request'

// 添加公告
export const addNotice = (data) => {
  return request({
    url: '/notice/add',
    method: 'post',
    data
  })
}

// 获取公告列表
export const getNoticeList = (params) => {
  return request({
    url: '/notice/list',
    method: 'get',
    params
  })
}

// 获取公告详情
export const getNoticeDetail = (id) => {
  return request({
    url: `/notice/detail/${id}`,
    method: 'get'
  })
}

// 更新公告
export const updateNotice = (data) => {
  return request({
    url: '/notice/update',
    method: 'put',
    data
  })
}

// 删除公告
export const deleteNotice = (id) => {
  return request({
    url: `/notice/${id}`,
    method: 'delete'
  })
}

// 置顶公告
export const toggleTopNotice = (id) => {
  return request({
    url: `/notice/toggle-top/${id}`,
    method: 'post'
  })
}

