<script lang="ts">
  import Router from "svelte-spa-router";
  import { routes } from "./routes/routes";
</script>

<main class="default-background__dots w-full h-full">
  <Router {routes} />
</main>