// stores/languageStore.js
import { writable } from 'svelte/store';

export const sourceLanguage = writable({ name: 'Estonian', code: 'ET' });
export const targetLanguage = writable({ name: 'French', code: 'FR' });

export const updateSourceLanguage = (lang: { name: string; code: string; }) => {
  sourceLanguage.set(lang);
};

export const updateTargetLanguage = (lang: { name: string; code: string; }) => {
  targetLanguage.set(lang);
};
