<script lang="ts">
    import NavBar from "../components/NavBar.svelte";
</script>

<div>
    <NavBar />
    <div class="w-screen h-[91vh] flex flex-col justify-center items-center pb-[10%]">
        <div class="c404">404</div>
        <div class="got-lost">Seems like you got lost..</div>
    </div>
</div>

<style>
    .c404 {
        font-weight: bold;
        font-size: 15vw;
    }

    .got-lost {
        font-size: 2vw
    }
</style>