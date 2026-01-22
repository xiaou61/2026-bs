const { get, post } = require('../../utils/request')

Page({
  data: {
    questions: [],
    currentIndex: 0,
    currentQuestion: null,
    selectedAnswer: '',
    showAnswer: false,
    correctCount: 0,
    totalCount: 0
  },

  onLoad(options) {
    this.options = options
    this.loadQuestions()
  },

  async loadQuestions() {
    try {
      wx.showLoading({ title: '加载中...' })
      let res
      if (this.options.random) {
        res = await get('/question/random', {
          subjectId: this.options.subjectId,
          count: 10
        })
      } else {
        res = await get('/question/list', {
          subjectId: this.options.subjectId,
          categoryId: this.options.categoryId,
          type: this.options.type,
          size: 20
        })
      }
      const questions = this.options.random ? res.data : (res.data.records || [])
      if (questions.length) {
        this.setData({
          questions,
          totalCount: questions.length,
          currentQuestion: questions[0]
        })
      } else {
        wx.showToast({ title: '暂无题目', icon: 'none' })
      }
    } catch (e) {}
    wx.hideLoading()
  },

  selectOption(e) {
    if (this.data.showAnswer) return
    const option = e.currentTarget.dataset.option
    let selected = this.data.selectedAnswer
    
    if (this.data.currentQuestion.type === 2) {
      // 多选
      if (selected.includes(option)) {
        selected = selected.replace(option, '')
      } else {
        selected += option
      }
      selected = selected.split('').sort().join('')
    } else {
      selected = option
    }
    this.setData({ selectedAnswer: selected })
  },

  async submitAnswer() {
    if (!this.data.selectedAnswer) {
      wx.showToast({ title: '请选择答案', icon: 'none' })
      return
    }
    
    try {
      const res = await post('/question/submit', {
        questionId: this.data.currentQuestion.id,
        answer: this.data.selectedAnswer
      })
      
      const isCorrect = res.data.isCorrect
      if (isCorrect) {
        this.setData({ correctCount: this.data.correctCount + 1 })
      }
      this.setData({ showAnswer: true })
    } catch (e) {}
  },

  nextQuestion() {
    const nextIndex = this.data.currentIndex + 1
    if (nextIndex >= this.data.questions.length) {
      this.showResult()
      return
    }
    this.setData({
      currentIndex: nextIndex,
      currentQuestion: this.data.questions[nextIndex],
      selectedAnswer: '',
      showAnswer: false
    })
  },

  showResult() {
    const { correctCount, totalCount } = this.data
    wx.showModal({
      title: '练习结束',
      content: `共${totalCount}题，正确${correctCount}题，正确率${Math.round(correctCount * 100 / totalCount)}%`,
      showCancel: false,
      success: () => {
        wx.navigateBack()
      }
    })
  }
})
