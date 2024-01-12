<script lang="ts">
  import { push } from "svelte-spa-router";
  import { isModalShown } from "../stores/modal";

  let showModal = false;

  isModalShown.subscribe((value) => {
    showModal = value;
  });

  function toggleModal(): void {
    isModalShown.set(!showModal);
  }

  function handleModalAction(type: string): void {
    isModalShown.set(!showModal);

    switch (type) {
      case "history":
        push("/history");
        break;
      case "translate":
        push("/");
        break;
    }
  }
</script>

{#if showModal}
  <div class="modal flex flex-col">
    <div
      class="h-[56px] flex flex-col justify-center items-end w-full px-2 pr-5"
    >
      <!-- svelte-ignore a11y-click-events-have-key-events -->
      <!-- svelte-ignore a11y-no-static-element-interactions -->
      <div class="close-modal" on:click={toggleModal}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="16"
          width="12"
          viewBox="0 0 384 512"
          ><path
            d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"
          /></svg
        >
      </div>
    </div>

    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-no-static-element-interactions -->
    <div
      class="flex flex-col w-full h-[100px] justify-center px-5 modal-item"
      on:click={() => {
        handleModalAction("translate");
      }}
    >
      Translate
    </div>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-no-static-element-interactions -->
    <div
      class="flex flex-col w-full h-[100px] justify-center px-5 modal-item"
      on:click={() => {
        handleModalAction("history");
      }}
    >
      History
    </div>
  </div>
{/if}

<style>
  .modal-item {
    font-size: 8vw;
    cursor: pointer;
  }

  .modal-item:hover {
    background-color: #dcf900;
  }

  .close-modal {
    color: black;
    cursor: pointer;
    padding: 10px;
    transition: 0.3s all cubic-bezier(0.4, 0, 0.2, 1);
  }

  .close-modal:hover {
    background-color: #dcf900;
    border-radius: 0px 10px 0px 10px;
    transition: 0.3s all cubic-bezier(0.4, 0, 0.2, 1);
  }

  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: white;
    align-items: center;
    z-index: 1;
  }
</style>
