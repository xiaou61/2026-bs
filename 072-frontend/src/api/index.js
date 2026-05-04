import request from './request'

const favoriteParams = (targetIdOrParams, targetType) =>
  typeof targetIdOrParams === 'object' ? targetIdOrParams : { targetId: targetIdOrParams, targetType }

const normalizeSpot = (spot) => ({
  ...spot,
  address: spot.location,
  coverImage: spot.coverImg
})

const normalizeRoute = (route) => ({
  ...route,
  name: route.title,
  coverImage: route.coverImg,
  totalDistance: route.totalDistance || 0
})

export const login = data => request.post('/auth/login', data)
export const register = data => request.post('/auth/register', data)
export const logout = () => request.post('/auth/logout')

export const getSpotList = params => request.get('/spots/list', { params })
export const getSpotDetail = id => request.get(`/spots/detail/${id}`).then(res => {
  if (res.data?.spot) {
    return { ...res, data: normalizeSpot(res.data.spot), rawData: res.data }
  }
  return res
})
export const getAllSpots = () => request.get('/spots/all')
export const getTop10Spots = () => request.get('/spots/top10')
export const addSpot = data => request.post('/spots', data)
export const createSpot = addSpot
export const updateSpot = (idOrData, data) => request.put('/spots', data ? { ...data, id: idOrData } : idOrData)
export const deleteSpot = id => request.delete(`/spots/${id}`)
export const deleteSpotById = deleteSpot

export const getRouteList = params => request.get('/routes/list', { params })
export const getRouteDetail = id => request.get(`/routes/detail/${id}`).then(res => {
  if (res.data?.route) {
    return { ...res, data: normalizeRoute(res.data.route), rawData: res.data }
  }
  return res
})
export const getRouteSpots = id => request.get(`/routes/detail/${id}`).then(res => {
  const spots = (res.data?.spots || []).map((item, index) => ({
    id: item.spot?.id || index,
    spotId: item.spot?.id,
    spotName: item.spot?.name,
    coverImage: item.spot?.coverImg,
    address: item.spot?.location,
    orderNum: item.orderNum,
    dayNum: 1,
    stayMinutes: Number(item.stayHours || 0) * 60
  }))
  return { ...res, data: spots }
})
export const addRoute = data => request.post('/routes', data)
export const updateRoute = data => request.put('/routes', data)
export const deleteRoute = id => request.delete(`/routes/${id}`)
export const likeRoute = id => request.post(`/routes/like/${id}`)

export const getTicketList = params => request.get('/tickets/list', { params })
export const getTicketTypes = spotId => request.get(`/tickets/types/${spotId}`)
export const getTicketsBySpot = getTicketTypes
export const addTicketType = data => request.post('/tickets/types', data)
export const updateTicketType = data => request.put('/tickets/types', data)
export const deleteTicketType = id => request.delete(`/tickets/types/${id}`)
export const createTicketOrder = data => request.post('/tickets/order', {
  ...data,
  ticketDate: data.ticketDate || data.visitDate
})
export const getMyOrders = params => request.get('/tickets/orders/my', { params })
export const getAllOrders = params => request.get('/tickets/orders', { params })
export const getOrderDetail = id => request.get(`/tickets/orders/${id}`)
export const refundOrder = id => request.post(`/tickets/orders/${id}/refund`)
export const refundTicketOrder = refundOrder
export const updateOrderStatus = (id, data) => request.put(`/tickets/orders/${id}/status`, data)
export const payTicketOrder = id => updateOrderStatus(id, { status: 'paid' })
export const cancelTicketOrder = id => updateOrderStatus(id, { status: 'cancelled' })

export const getHotelList = params => request.get('/hotels/list', { params })
export const getHotelById = id => request.get(`/hotels/${id}`)
export const getAllHotels = () => request.get('/hotels/all')
export const addHotel = data => request.post('/hotels', data)
export const updateHotel = data => request.put('/hotels', data)
export const deleteHotel = id => request.delete(`/hotels/${id}`)

export const getRestaurantList = params => request.get('/restaurants/list', { params })
export const getRestaurantById = id => request.get(`/restaurants/${id}`)
export const getAllRestaurants = () => request.get('/restaurants/all')
export const addRestaurant = data => request.post('/restaurants', data)
export const updateRestaurant = data => request.put('/restaurants', data)
export const deleteRestaurant = id => request.delete(`/restaurants/${id}`)

export const getActivityList = params => request.get('/activities/list', { params })
export const getActivityById = id => request.get(`/activities/${id}`)
export const addActivity = data => request.post('/activities', data)
export const updateActivity = data => request.put('/activities', data)
export const deleteActivity = id => request.delete(`/activities/${id}`)
export const registerActivity = id => request.post(`/activities/${id}/register`)
export const cancelRegistration = id => request.post(`/activities/${id}/cancel`)
export const getMyRegistrations = () => request.get('/activities/registrations/my')
export const checkRegistered = id => request.get(`/activities/${id}/registered`)

export const getNoteList = params => request.get('/notes/list', { params })
export const getMyNotes = params => request.get('/notes/my', { params })
export const getNoteDetail = id => request.get(`/notes/detail/${id}`)
export const addNote = data => request.post('/notes', data)
export const createNote = addNote
export const updateNote = (idOrData, data) => request.put('/notes', data ? { ...data, id: Number(idOrData) } : idOrData)
export const deleteNote = id => request.delete(`/notes/${id}`)
export const likeNote = id => request.post(`/notes/like/${id}`)
export const auditNote = (id, data) => request.put(`/notes/audit/${id}`, data)

export const getReviewList = params => request.get('/reviews/list', { params })
export const getReviewsByTarget = params => request.get('/reviews/target', { params })
export const getReviewsBySpot = id => getReviewsByTarget({ targetType: 'spot', targetId: id })
export const addReview = data => request.post('/reviews', {
  ...data,
  targetId: data.targetId || data.spotId,
  targetType: data.targetType || 'spot'
})
export const createReview = addReview
export const deleteReview = id => request.delete(`/reviews/${id}`)
export const updateReviewStatus = (id, data) => request.put(`/reviews/${id}/status`, data)
export const getCommentsByNote = id => getReviewsByTarget({ targetType: 'note', targetId: id })
export const addComment = data => addReview({
  targetId: data.noteId,
  targetType: 'note',
  rating: 5,
  content: data.content
})

export const getAnnouncementList = params => request.get('/announcements/list', { params })
export const getTopAnnouncements = () => request.get('/announcements/top')
export const getAnnouncementById = id => request.get(`/announcements/${id}`)
export const addAnnouncement = data => request.post('/announcements', data)
export const createAnnouncement = addAnnouncement
export const updateAnnouncement = (idOrData, data) => request.put('/announcements', data ? { ...data, id: idOrData } : idOrData)
export const deleteAnnouncement = id => request.delete(`/announcements/${id}`)
export const deleteAnnouncementById = deleteAnnouncement

export const getUserInfo = () => request.get('/user/info')
export const updateUserInfo = data => request.put('/user/info', data)
export const updatePassword = data => request.put('/user/password', data)
export const recharge = data => request.post('/user/recharge', data)
export const rechargeWallet = amount => request.post('/user/recharge', { amount })
export const getUserList = params => request.get('/user/list', { params })
export const updateUserStatus = (id, data) => request.put(`/user/${id}/status`, data)
export const deleteUserById = id => request.delete(`/user/${id}`)

export const addFavorite = data => request.post('/user/favorite', data)
export const removeFavorite = (targetIdOrParams, targetType) => request.delete('/user/favorite', { params: favoriteParams(targetIdOrParams, targetType) })
export const checkFavorite = (targetIdOrParams, targetType) => request.get('/user/favorite/check', { params: favoriteParams(targetIdOrParams, targetType) })
export const getFavorites = params => request.get('/user/favorites', { params })
export const getMyFavorites = params => {
  const targetType = params?.targetType || params?.type || undefined
  return getFavorites({ targetType }).then(res => {
    const records = Array.isArray(res.data) ? res.data : []
    return { ...res, data: { records, total: records.length } }
  })
}

export const getStatistics = () => request.get('/admin/statistics')
export const getSpotRanking = () => request.get('/spots/top10')
