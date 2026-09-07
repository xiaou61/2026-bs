import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3138, proxy: { '/api': { target: 'http://localhost:8138', changeOrigin: true } } }
})


