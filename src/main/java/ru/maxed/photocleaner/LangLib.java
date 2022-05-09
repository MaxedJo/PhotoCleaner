package ru.maxed.photocleaner;

import ru.maxed.photocleaner.core.utility.Settings;

public enum LangLib {
    CONFIRMATION("confirmation"),
    DELETE_ERROR("error.file.delete"),
    CONFIG_MESSAGE("config.message"),
    CONFIG_DESTINATION_ERROR("config.error.destinationCreation"),
    ERROR_WRONG_PATH("error.path.wrong"),
    PATH_PLACEHOLDER("promptText.path"),
    ORIGIN_EXTENSION_PLACEHOLDER("promptText.extension.origin"),
    PROCESSED_EXTENSION_PLACEHOLDER("promptText.extension.processed"),
    COUNTER_LABEL("counter.label"),
    ERROR("error"),
    TITLE("title"),
    CANCEL("cancel"),
    FILES_NOT_FOUND_ERROR("error.filesNotFound"),
    NEED_EXTENSIONS_ERROR("error.extensions.need"),
    SAME_EXTENSIONS_ERROR("error.extensions.same"),
    WRONG_EXTENSIONS_ERROR("error.extensions.wrong"),
    DELETE_CONFIRM_MESSAGE("delete.confirm.message");

    private final String key;

    LangLib(String key) {
        this.key = Settings.getLocalized(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
