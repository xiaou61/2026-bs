const creditRanges = [
  { min: 90, label: '信用优秀', type: 'success' },
  { min: 70, label: '信用良好', type: 'primary' },
  { min: 60, label: '信用一般', type: 'warning' },
  { min: 0, label: '信用较低', type: 'danger' }
]

const productStatusMap = {
  on_sale: '在售中',
  sold: '已售出',
  off_shelf: '已下架'
}

const orderStatusMap = {
  pending: '待完成',
  completed: '已完成',
  cancelled: '已取消'
}

const bargainStatusMap = {
  pending: '待处理',
  accepted: '已接受',
  rejected: '已拒绝'
}

export const normalizeImageList = (value) => {
  if (Array.isArray(value) && value.length) {
    return value.filter(Boolean)
  }

  if (typeof value === 'string' && value.trim()) {
    return value
      .split(',')
      .map((item) => item.trim())
      .filter(Boolean)
  }

  return []
}

export const fallbackImage = (title = '校园二手商品') => {
  return `https://placehold.co/720x520?text=${encodeURIComponent(title)}`
}

export const getProductStatusLabel = (status) => productStatusMap[status] || status || '未知状态'

export const getOrderStatusLabel = (status) => orderStatusMap[status] || status || '未知状态'

export const getBargainStatusLabel = (status) => bargainStatusMap[status] || status || '未知状态'

export const getCreditMeta = (score = 0) => {
  return creditRanges.find((item) => score >= item.min) || creditRanges[creditRanges.length - 1]
}

export const formatPrice = (value) => {
  if (value === null || value === undefined || value === '') {
    return '--'
  }

  const amount = Number(value)
  if (Number.isNaN(amount)) {
    return value
  }

  return `￥${amount.toFixed(2)}`
}

export const formatTime = (value) => {
  if (!value) {
    return '--'
  }

  const date = new Date(value)
  if (Number.isNaN(date.getTime())) {
    return value
  }

  const year = date.getFullYear()
  const month = `${date.getMonth() + 1}`.padStart(2, '0')
  const day = `${date.getDate()}`.padStart(2, '0')
  const hour = `${date.getHours()}`.padStart(2, '0')
  const minute = `${date.getMinutes()}`.padStart(2, '0')

  return `${year}-${month}-${day} ${hour}:${minute}`
}
