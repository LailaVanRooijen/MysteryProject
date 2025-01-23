package com.mystery.project.mainconfiguration;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class CustomBanner implements Banner {
    private static final String seaGreen = "\u001B[38;2;0;145;110m";

    private static final String powderBlue = "\u001B[38;2;160;185;198m";
    private static final String chinaRose = "\u001B[38;2;171;79;104m";

    private static final String BANNER =
            powderBlue
                    + "\n"
                    +"    __  ___           __                 \n"
                    +"   /  |/  /_  _______/ /____  _______  __\n"
                    +"  / /|_/ / / / / ___/ __/ _ \\/ ___/ / / /\n"
                    +" / /  / / /_/ (__  ) /_/  __/ /  / /_/ / \n"
                    +"/_/  /_/\\__, /____/\\__/\\___/_/   \\__, /  \n"
                    +"    ___/____/          _        /____/   \n"
                    +"   / __ \\_________    (_)__  _____/ /_   \n"
                    +"  / /_/ / ___/ __ \\  / / _ \\/ ___/ __/   \n"
                    +" / ____/ /  / /_/ / / /  __/ /__/ /_     \n"
                    +"/_/   /_/   \\____/_/ /\\___/\\___/\\__/     \n"
                    +"                /___/                    \n"
                    + chinaRose
                    + "Don't forget to have fun! \n\n"
                    + seaGreen;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, java.io.PrintStream out) {
        out.println(BANNER);
    }
}
