<script lang="ts">
    // @ts-nocheck

    import { onMount } from "svelte";
    import { sourceLanguage, targetLanguage, updateSourceLanguage, updateTargetLanguage } from '../stores/availableLanguages';


    let targetDropdownVisible = false;
    let sourceDropdownVisible = false;

    let targetLanguages = [];
    let sourceLanguages = [];

    onMount(async () => {
        const targetResponse = await fetch(
            "http://localhost:8070/api/translate/languages?type=target",
        );
        const sourceResponse = await fetch(
            "http://localhost:8070/api/translate/languages?type=source",
        );

        const targetData = await targetResponse.json();
        const sourceData = await sourceResponse.json();

        if (targetResponse.ok && sourceResponse.ok) {
            targetLanguages = targetData.body.data.languages
                .map((lang) => ({
                    name: lang.name,
                    code: lang.code.toUpperCase(),
                }))
                .sort((a, b) => a.name.localeCompare(b.name));

            sourceLanguages = sourceData.body.data.languages
                .map((lang) => ({
                    name: lang.name,
                    code: lang.code.toUpperCase(),
                }))
                .sort((a, b) => a.name.localeCompare(b.name));
        } else {
            console.error(
                "Failed to fetch languages:",
                targetData.body.details,
                sourceData.body.details,
            );
        }
    });

    function toggleSourceDropdown() {
        sourceDropdownVisible = !sourceDropdownVisible;
        targetDropdownVisible = false;
    }

    function toggleTargetDropdown() {
        targetDropdownVisible = !targetDropdownVisible;
        sourceDropdownVisible = false; 
    }

    function selectTargetLanguage(lang: { name: string; code: string; }) {
        updateTargetLanguage(lang);
        sourceDropdownVisible = false;
    }

    function selectSourceLanguage(lang: { name: string; code: string; }) {
        updateSourceLanguage(lang);
        targetDropdownVisible = false;
    }
</script>

<div class="language-selector-container">
    <div class="flex items-center whitespace-nowrap h-[48px]">
        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div class="language" on:click={toggleSourceDropdown}>
            <div>
                {($sourceLanguage).name}
                 <button>
                    <svg
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

            {#if sourceDropdownVisible}
                <div class="dropdown-content">
                    {#each sourceLanguages as lang (lang.code)}
                        <div on:click={() => {selectSourceLanguage({name: lang.name, code: lang.code})}}
                            class="dropdown-item flex justify-center items-center"
                        >
                            {lang.name}
                        </div>
                    {/each}
                </div>
            {/if}
        </div>

        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div class="language" on:click={toggleTargetDropdown}>
            <div>
                {($targetLanguage).name} <button>
                    <svg
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

            {#if targetDropdownVisible}
                <div class="dropdown">
                    <div class="dropdown-content">
                        {#each targetLanguages as lang (lang.code)}
                        <div on:click={() => {selectTargetLanguage({name: lang.name, code: lang.code})}}
                            class="dropdown-item flex justify-center items-center"
                        >
                            {lang.name}
                        </div>
                        {/each}
                    </div>
                </div>
            {/if}
        </div>
    </div>
</div>


<style>
    .dropdown-item {
        height: 50px;
    }

    .dropdown-item:hover {
        background-color: #e0e0e0b2;
    }

    .dropdown {
        position: absolute;
    }

    .dropdown-content {
        position: absolute;
        background-color: #f9f9f9;
        border: 1px solid #ccc;
        padding: 10px;
        z-index: 1;
        width: 300px;
        height: 300px;
        overflow: auto;
    }

    .dropdown:hover .dropdown-content {
        display: block;
    }

    .language {
        cursor: pointer;
        flex: 1;
        font-size: 16px;
        font-weight: 400;
        overflow: hidden;
        text-align: center;
        text-overflow: ellipsis;
        white-space: pre;
        width: auto;
        word-break: normal;
    }

    .language-selector-container {
        border-bottom: 1px solid rgba(0, 0, 0, 0.12);
    }
</style>
