package com.codecool.masonrySystem.controller;

import java.io.IOException;

public class RootController {
    HttpController httpController = new HttpController();

    public void run() {
        try {
            httpController.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
