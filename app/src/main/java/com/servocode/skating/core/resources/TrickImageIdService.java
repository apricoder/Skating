package com.servocode.skating.core.resources;

import com.servocode.skating.core.app.SkatingApplication;

import lombok.Setter;

@Setter
public class TrickImageIdService {

    public ResourceIdService resourceIdService;
    private String prefix = "trick_";

    public TrickImageIdService() {
        SkatingApplication.getInstance().inject(this);
    }

    public int getTrickImageIdByShortName(String langShortName) {
        String fileName = getFileName(langShortName);
        return resourceIdService.getDrawableIdByName(fileName);
    }

    String getFileName(String langShortName) {
        return prefix + langShortName;
    }

}