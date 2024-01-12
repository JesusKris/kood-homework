import { wrap } from 'svelte-spa-router/wrap'

export const routes = {

    '/': wrap({
        asyncComponent: () => import("../views/HomeView.svelte")
    }),

    '/history': wrap({
        asyncComponent: () => import("../views/HistoryView.svelte")
    }),

    '*': wrap({
        asyncComponent: () => import("../views/404View.svelte")
    }),
};