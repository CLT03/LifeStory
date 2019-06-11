package com.vivwe.video;

import com.vivwe.video.model.MediaUiModel;
import com.vivwe.video.model.TextUiModel;

public interface AssetDelegate {
    void pickMedia(MediaUiModel model);

    void editText(TextUiModel model);
}
