<script>
    // @ts-nocheck
    import {
        sourceLanguage,
        targetLanguage,
    } from "../stores/availableLanguages";

    import LanguageSelectorBar from "./LanguageSelectorBar.svelte";

    let debounceTimer;

    function debounce(func, delay) {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(func, delay);
    }

    let sourceText = "";
    let translationResult = "";

    async function translate() {
        try {
            if (!sourceText.trim()) {
                translationResult = "";
                return;
            }

            const apiEndpoint = `http://localhost:8070/api/translate?input_text=${sourceText}&source_lang=${$sourceLanguage.code}&target_lang=${$targetLanguage.code}`;
            const response = await fetch(apiEndpoint, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            const result = await response.json();

            if (response.ok) {
                translationResult = result.body.data.translation;
            } else {
                console.error("Failed to translate:", result.error);
            }
        } catch (error) {
            console.error("Error during translation:", error.message);
        }
    }

    sourceLanguage.subscribe(() => {
        translate();
    });

    targetLanguage.subscribe(() => {
        translate();
    });
</script>

<div>
    <div class="translator-container__outer">
        <LanguageSelectorBar />

        <div class="translation-input-box flex relative px-5 pt-5">
            <textarea
                class="input-textfield"
                aria-label="surce text"
                autocapitalize="off"
                autocomplete="off"
                autocorrect="off"
                rows="1"
                maxlength="1500"
                bind:value={sourceText}
                on:input={() => debounce(translate, 650)}
                placeholder="Start typing.."
            ></textarea>
        </div>
        <div class="translation-result-box px-5 pt-5">{translationResult}</div>
    </div>
</div>

<style>
    .input-textfield {
        overflow-x: hidden;
        font-size: 24px;
        border: none;
        color: rgb(60, 64, 67);
        resize: none;
        width: 100%;
        height: auto;
        overflow-wrap: break-word;
        overflow-y: visible;
    }

    .translation-input-box {
        display: flex;
        font-size: 14px;
        line-height: 27px;
        min-height: 164px;
        position: relative;
        background-color: #f5f5f597;
        height: auto;
    }

    .translation-result-box {
        display: flex;
        flex-direction: column;
        font-size: 24px;
        line-height: 27px;
        min-height: 164px;
        position: relative;
        background-color: #e0e0e0b2;
        overflow-wrap: break-word;
    }

    .translator-container__outer {
        display: flex;
        flex-direction: column;
        min-height: 100%;
        transition: 0.6s all cubic-bezier(0.4, 0, 0.2, 1);
        width: auto;
    }
</style>
