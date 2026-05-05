import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3132, proxy: { '/api': { target: 'http://localhost:8132', changeOrigin: true } } }
})
