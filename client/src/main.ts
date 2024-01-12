import './assets/index.css'
import App from './App.svelte'

export const TRANSLATION_API = "http://localhost:8070"

const app = new App({
  //@ts-expect-error
  target: document.getElementById('app'),
})

export default app
