import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3190, proxy: { '/api': { target: 'http://localhost:8190', changeOrigin: true } } }
})
