import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const register = (data) => request.post('/api/auth/register', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const updatePassword = (data) => request.put('/api/auth/password', data)

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const updateUser = (data) => request.put('/api/user', data)
export const updateUserStatus = (data) => request.put('/api/user/status', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCategoryPage = (params) => request.get('/api/movieCategory/page', { params })
export const getCategoryList = () => request.get('/api/movieCategory/list')
export const addCategory = (data) => request.post('/api/movieCategory', data)
export const updateCategory = (data) => request.put('/api/movieCategory', data)
export const deleteCategory = (id) => request.delete(`/api/movieCategory/${id}`)

export const getMoviePage = (params) => request.get('/api/movie/page', { params })
export const getMovieById = (id) => request.get(`/api/movie/${id}`)
export const getHotMovies = () => request.get('/api/movie/hot')
export const addMovie = (data) => request.post('/api/movie', data)
export const updateMovie = (data) => request.put('/api/movie', data)
export const deleteMovie = (id) => request.delete(`/api/movie/${id}`)

export const getCinemaPage = (params) => request.get('/api/cinema/page', { params })
export const getCinemaList = () => request.get('/api/cinema/list')
export const addCinema = (data) => request.post('/api/cinema', data)
export const updateCinema = (data) => request.put('/api/cinema', data)
export const deleteCinema = (id) => request.delete(`/api/cinema/${id}`)

export const getHallPage = (params) => request.get('/api/hall/page', { params })
export const getHallList = (cinemaId) => request.get('/api/hall/list', { params: { cinemaId } })
export const addHall = (data) => request.post('/api/hall', data)
export const updateHall = (data) => request.put('/api/hall', data)
export const deleteHall = (id) => request.delete(`/api/hall/${id}`)

export const getShowtimePage = (params) => request.get('/api/showtime/page', { params })
export const getShowtimeByMovie = (movieId) => request.get(`/api/showtime/movie/${movieId}`)
export const addShowtime = (data) => request.post('/api/showtime', data)
export const updateShowtime = (data) => request.put('/api/showtime', data)
export const deleteShowtime = (id) => request.delete(`/api/showtime/${id}`)

export const createOrder = (data) => request.post('/api/order', data)
export const getOrderPage = (params) => request.get('/api/order/page', { params })
export const getMyOrders = () => request.get('/api/order/my')
export const payOrder = (id) => request.put(`/api/order/pay/${id}`)
export const cancelOrder = (id) => request.put(`/api/order/cancel/${id}`)
export const completeOrder = (id) => request.put(`/api/order/complete/${id}`)

export const getReviewsByMovie = (movieId) => request.get(`/api/review/movie/${movieId}`)
export const getReviewPage = (params) => request.get('/api/review/page', { params })
export const addReview = (data) => request.post('/api/review', data)
export const deleteReview = (id) => request.delete(`/api/review/${id}`)

export const toggleFavorite = (movieId) => request.post(`/api/favorite/${movieId}`)
export const getMyFavorites = () => request.get('/api/favorite/my')
export const checkFavorite = (movieId) => request.get(`/api/favorite/check/${movieId}`)

export const getAnnouncementPage = (params) => request.get('/api/announcement/page', { params })
export const getLatestAnnouncements = () => request.get('/api/announcement/list')
export const addAnnouncement = (data) => request.post('/api/announcement', data)
export const updateAnnouncement = (data) => request.put('/api/announcement', data)
export const deleteAnnouncement = (id) => request.delete(`/api/announcement/${id}`)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getCategoryStats = () => request.get('/api/dashboard/categoryStats')
export const getOrderTrend = () => request.get('/api/dashboard/orderTrend')
export const getBoxOfficeRank = () => request.get('/api/dashboard/boxOfficeRank')
