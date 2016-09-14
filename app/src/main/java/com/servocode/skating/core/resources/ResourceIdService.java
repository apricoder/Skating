package com.servocode.skating.core.resources;

import android.content.Context;
import android.content.res.Resources;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class ResourceIdService {
    @NonNull
    private Context context;
    private String type = "drawable";

    public int getDrawableIdByName(String name) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        return resources.getIdentifier(name, type, packageName);
    }
}