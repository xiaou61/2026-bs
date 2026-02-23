import request from './request'

export const userApi = {
  register: (data) => request.post('/user/register', data),
  login: (data) => request.post('/user/login', data),
  getUserInfo: () => request.get('/user/info'),
  updateUser: (data) => request.put('/user/update', data),
  getUserList: (params) => request.get('/user/list', { params }),
  updateStatus: (id, status) => request.put(`/user/status/${id}`, null, { params: { status } })
}

export const movieApi = {
  getMovieList: (params) => request.get('/movie/list', { params }),
  getMovieById: (id) => request.get(`/movie/${id}`),
  addMovie: (data) => request.post('/movie/add', data),
  updateMovie: (data) => request.put('/movie/update', data),
  deleteMovie: (id) => request.delete(`/movie/${id}`),
  getRecommendMovies: () => request.get('/movie/recommend')
}

export const cinemaApi = {
  getCinemaList: (params) => request.get('/cinema/list', { params }),
  getCinemaById: (id) => request.get(`/cinema/${id}`),
  addCinema: (data) => request.post('/cinema/add', data),
  updateCinema: (data) => request.put('/cinema/update', data),
  deleteCinema: (id) => request.delete(`/cinema/${id}`)
}

export const hallApi = {
  getHallList: (params) => request.get('/hall/list', { params }),
  getHallById: (id) => request.get(`/hall/${id}`),
  addHall: (data) => request.post('/hall/add', data),
  updateHall: (data) => request.put('/hall/update', data),
  deleteHall: (id) => request.delete(`/hall/${id}`)
}

export const showtimeApi = {
  getShowtimeList: (params) => request.get('/showtime/list', { params }),
  getShowtimeById: (id) => request.get(`/showtime/${id}`),
  addShowtime: (data) => request.post('/showtime/add', data),
  updateShowtime: (data) => request.put('/showtime/update', data),
  deleteShowtime: (id) => request.delete(`/showtime/${id}`)
}

export const seatApi = {
  getSeatsByShowtime: (showtimeId) => request.get(`/seat/showtime/${showtimeId}`),
  lockSeats: (seatIds) => request.post('/seat/lock', seatIds)
}

export const orderApi = {
  createOrder: (data) => request.post('/order/create', data),
  getOrderList: (params) => request.get('/order/list', { params }),
  getOrderById: (id) => request.get(`/order/${id}`),
  cancelOrder: (id) => request.put(`/order/cancel/${id}`),
  getMyOrders: (params) => request.get('/order/my', { params })
}

export const ticketApi = {
  getMyTickets: (params) => request.get('/ticket/my', { params }),
  getTicketById: (id) => request.get(`/ticket/${id}`),
  verifyTicket: (ticketNo) => request.post('/ticket/verify', null, { params: { ticketNo } })
}

export const paymentApi = {
  createPayment: (orderId, payType) => request.post('/payment/create', null, { params: { orderId, payType } }),
  balancePay: (orderId) => request.post('/payment/balance', null, { params: { orderId } }),
  recharge: (amount) => request.post('/payment/recharge', null, { params: { amount } })
}

export const commentApi = {
  getMovieComments: (movieId, params) => request.get(`/comment/movie/${movieId}`, { params }),
  addComment: (data) => request.post('/comment/add', data),
  deleteComment: (id) => request.delete(`/comment/${id}`),
  getCommentList: (params) => request.get('/comment/list', { params }),
  updateStatus: (id, status) => request.put(`/comment/status/${id}`, null, { params: { status } })
}

export const couponApi = {
  getCouponList: (params) => request.get('/coupon/list', { params }),
  getAvailableCoupons: () => request.get('/coupon/available'),
  receiveCoupon: (id) => request.post(`/coupon/receive/${id}`),
  getMyCoupons: (params) => request.get('/coupon/my', { params }),
  addCoupon: (data) => request.post('/coupon/add', data),
  updateCoupon: (data) => request.put('/coupon/update', data),
  deleteCoupon: (id) => request.delete(`/coupon/${id}`)
}

export const statisticsApi = {
  getDashboardStats: () => request.get('/statistics/dashboard'),
  getSalesTrend: (days) => request.get('/statistics/sales-trend', { params: { days } }),
  getBoxOfficeRank: () => request.get('/statistics/box-office')
}
