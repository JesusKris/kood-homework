import HomeView from "../views/HomeView.svelte"
import {wrap} from 'svelte-spa-router/wrap'

export const routes = {
    // Exact path
    '/':wrap({
        asyncComponent: () => import("../views/HomeView.svelte")
    }),
};