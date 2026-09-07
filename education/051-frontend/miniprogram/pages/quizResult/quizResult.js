const api = require('../../api/index')

Page({
  data: {
    type: 'daily',
    categoryId: null,
    questions: [],
    currentIndex: 0,
    currentQuestion: null,
    selectedAnswer: '',
    showResult: false,
    result: null,
    finished: false,
    correctCount: 0,
    totalPoints: 0
  },

  onLoad(options) {
    this.setData({
      type: options.type || 'daily',
      categoryId: options.categoryId
    })
    if (options.name) {
      wx.setNavigationBarTitle({ title: options.name + '测验' })
    }
    this.loadQuestions()
  },

  async loadQuestions() {
    let questions
    if (this.data.type === 'daily') {
      questions = await api.getDailyQuestions()
    } else {
      questions = await api.getCategoryQuestions(this.data.categoryId)
    }
    this.setData({
      questions,
      currentQuestion: questions[0]
    })
  },

  selectOption(e) {
    if (this.data.showResult) return
    const answer = e.currentTarget.dataset.option
    this.setData({ selectedAnswer: answer })
    this.submitAnswer(answer)
  },

  async submitAnswer(answer) {
    const answerLetter = answer.charAt(0)
    const result = await api.submitAnswer({
      questionId: this.data.currentQuestion.id,
      answer: answerLetter
    })
    this.setData({
      showResult: true,
      result,
      correctCount: this.data.correctCount + (result.isCorrect ? 1 : 0),
      totalPoints: this.data.totalPoints + result.points
    })
  },

  nextQuestion() {
    const nextIndex = this.data.currentIndex + 1
    if (nextIndex >= this.data.questions.length) {
      this.setData({ finished: true })
    } else {
      this.setData({
        currentIndex: nextIndex,
        currentQuestion: this.data.questions[nextIndex],
        selectedAnswer: '',
        showResult: false,
        result: null
      })
    }
  },

  goBack() {
    wx.navigateBack()
  }
})
