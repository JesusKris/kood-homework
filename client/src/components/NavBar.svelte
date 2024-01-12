<script lang="ts">
    import { isModalShown } from "../stores/modal";
    import BurgerModal from "./BurgerModal.svelte";
    import { link } from "svelte-spa-router";

    function toggleModal() {
        isModalShown.set(!$isModalShown);
    }
</script>

<div class="navbar w-full relative flex flex-row mb-4 px-2 pr-5">
    <div class="flex flex-1 justify-start items-center">
        <img
            src="/images/kood_translate.png"
            alt="kood/Translate logo"
            class="select-none"
        />
    </div>
    <div class="flex flex-1 justify-end items-center">
        <a href="/" use:link>
            <button class="menu-button hide">Translate</button>
        </a>
        <a href="/history" use:link>
            <button class="menu-button hide">History</button>
        </a>

        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div class="burger-menu" on:click={toggleModal}>
            <svg
                xmlns="http://www.w3.org/2000/svg"
                height="16"
                width="14"
                viewBox="0 0 448 512"
                ><path
                    d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"
                /></svg
            >
        </div>
        <BurgerModal />
    </div>
</div>

<style>
    .burger-menu {
        color: black;
        cursor: pointer;
        padding: 10px;
        display: none;
        transition: 0.3s all cubic-bezier(0.4, 0, 0.2, 1);
    }

    .burger-menu:hover {
        background-color: #dcf900;
        border-radius: 0px 10px 0px 10px;
        transition: 0.3s all cubic-bezier(0.4, 0, 0.2, 1);
    }

    @media (max-width: 600px) {
        .burger-menu {
            display: unset;
        }

        .hide {
            display: none;
        }
    }

    .navbar {
        height: 56px;
    }

    @media (min-width: 600px) {
        .navbar {
            height: 64px;
        }
    }
</style>
