import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3153, proxy: { '/api': { target: 'http://localhost:8153', changeOrigin: true } } }
})
