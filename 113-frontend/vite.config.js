import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3113, proxy: { '/api': { target: 'http://localhost:8113', changeOrigin: true } } }
})
