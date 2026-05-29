import DefaultTheme from 'vitepress/theme'
import './style.css'
import ProjectCompare from './components/ProjectCompare.vue'
import TechFilter from './components/TechFilter.vue'
import ScenarioFilter from './components/ScenarioFilter.vue'

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    app.component('ProjectCompare', ProjectCompare)
    app.component('TechFilter', TechFilter)
    app.component('ScenarioFilter', ScenarioFilter)
  }
}
