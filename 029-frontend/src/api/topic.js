import request from './request'

// 获取话题列表
export function getTopicList(params) {
  return request.get('/topic/list', { params })
}

// 获取话题详情
export function getTopicDetail(id) {
  return request.get(`/topic/${id}/detail`)
}

// 创建话题
export function createTopic(data) {
  return request.post('/topic/create', data)
}

// 更新话题
export function updateTopic(id, data) {
  return request.put(`/topic/${id}`, data)
}

// 删除话题
export function deleteTopic(id) {
  return request.delete(`/topic/${id}`)
}

// 搜索话题
export function searchTopics(keyword) {
  return request.get('/topic/search', { params: { keyword } })
}

// 获取话题评论列表
export function getTopicComments(topicId, params) {
  return request.get(`/comment/topic/${topicId}`, { params })
}

// 添加评论
export function addComment(data) {
  return request.post('/comment/create', data)
}

// 删除评论
export function deleteComment(id) {
  return request.delete(`/comment/${id}`)
}
