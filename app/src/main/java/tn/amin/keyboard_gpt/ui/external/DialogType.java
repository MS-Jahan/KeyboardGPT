package tn.amin.keyboard_gpt.ui.external;

public enum DialogType {
    ChoseModel(true),
    ConfigureModel(true),
    WebSearch(false),
    EditCommandsList(false),
    EditCommand(false),
    ;

    final boolean isModelConfig;

    DialogType (boolean isModelConfig) {
        this.isModelConfig = isModelConfig;
    }
}
