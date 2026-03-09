package com.omer;

import eu.midnightdust.lib.config.MidnightConfig;

public class OmersClockConfig extends MidnightConfig {
    

    @Entry
    public static boolean visible = true;

    @Entry(isColor = true)
    public static String color = "#add8e6";

    @Entry
    public static boolean showBackground = true;

    @Entry(isSlider = true, min = 0, max = 100)
    public static int backgroundOpacity = 50;

    @Entry(isSlider = true, min = 0, max = 1000)
    public static int posX = 10;

    @Entry(isSlider = true, min = 0, max = 1000)
    public static int posY = 10;

}
