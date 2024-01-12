<script>
    // @ts-nocheck

    import NavBar from "../components/NavBar.svelte";
    import { onMount } from "svelte";
    import { TRANSLATION_API } from "../main";


    let translationHistory = [];
    let error = null;
    let order = "desc";
    let arrowRotationClass = "rotate-down";

    const fetchData = async () => {
        try {
            const response = await fetch(
                `${TRANSLATION_API}/api/translate/history?order=${order}`,
            );
            const result = await response.json();

            if (response.ok) {
                translationHistory = result.body.data.translationHistory;
            } else {
                // Set error message if there is an error
                error = "An error has occurred.";
            }
        } catch (error) {
            console.error("Error during data fetching:", error.message);
            // Set error message if there is an exception
            error = "An error has occurred.";
        }
    };

    // Simulating initial fetch on component mount
    onMount(fetchData);

    const toggleOrder = () => {
        order = order === "asc" ? "desc" : "asc";
        arrowRotationClass = order === "desc" ? "rotate-down" : "rotate-up";
        fetchData();
    };
</script>

<div>
    <NavBar />
    <div class="history__outer">
        <div
            class="history-title w-full flex flex-col justify-center items-center mt-5"
        >
            <div>
                History
                <button on:click={toggleOrder}>
                    <svg
                        class={arrowRotationClass}
                        xmlns="http://www.w3.org/2000/svg"
                        height="16"
                        width="12"
                        viewBox="0 0 384 512"
                        ><path
                            d="M169.4 470.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 370.8 224 64c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 306.7L54.6 265.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"
                        /></svg
                    >
                </button>
            </div>
        </div>
        <div>
            {#if error}
                <div
                    class="flex w-full flex-col justify-center items-center mt-[50%]"
                >
                    <p>{error}</p>
                </div>
            {:else if translationHistory.length > 0}
                <div>
                    {#each translationHistory as { id, inputText, translation, sourceLang, targetLang, createdAt }}
                        <div key={id} class="translation-box flex flex-col">
                            <div class="flex flex-row">
                                <div class="flex-1 flex justify-center">
                                    {sourceLang}
                                </div>
                                <div class="flex flex-col justify-center">
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        height="16"
                                        width="14"
                                        viewBox="0 0 448 512"
                                        ><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path
                                            d="M438.6 278.6c12.5-12.5 12.5-32.8 0-45.3l-160-160c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L338.8 224 32 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l306.7 0L233.4 393.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0l160-160z"
                                        /></svg
                                    >
                                </div>
                                <div class="flex-1 flex justify-center">
                                    {targetLang}
                                </div>
                            </div>

                            <div
                                class="flex flex-wrap w-full flex-col input-text"
                            >
                                {inputText}
                            </div>
                            <div class="translation-text">{translation}</div>
                            <div class="created_at">
                                {new Date(createdAt).toLocaleString()}
                            </div>
                        </div>
                    {/each}
                </div>
            {:else}
                <div
                    class="flex w-full flex-col justify-center items-center mt-[50%]"
                >
                    <p>No translations available.</p>
                </div>
            {/if}
        </div>
    </div>
</div>

<style>
    .rotate-down {
        transform: rotate(0deg);
        transition: transform 0.3s ease-out;
    }

    .rotate-up {
        transform: rotate(180deg);
        transition: transform 0.3s ease-out;
    }
    .created_at {
        padding-left: 15px;
        font-size: 10px;
        color: grey;
    }

    .input-text {
        font-size: 16px;
        font-weight: bold;
        word-break: normal;
        text-overflow: ellipsis;
        padding: 20px;
        text-align: justify;
    }

    .translation-text {
        font-size: 14px;
        font-weight: 400;
        word-break: normal;
        text-overflow: ellipsis;
        padding: 20px;
        text-align: justify;
    }

    .translation-box {
        display: flex;
        flex-direction: column;
        font-size: 14px;
        line-height: 27px;
        min-height: 164px;
        position: relative;
        border-bottom: 1px solid rgba(0, 0, 0, 0.556);
    }

    .translation-box:hover {
        background-color: #e0e0e0b2;
    }

    .history-title {
        font-size: 45px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.12);
    }

    .history__outer {
        display: flex;
        flex-direction: column;
        min-height: 100%;
        transition: 0.6s all cubic-bezier(0.4, 0, 0.2, 1);
        width: auto;
    }
</style>
