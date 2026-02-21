package com.classagendaprofessor;

import com.classagendaprofessor.shared.http.HttpServerBootstrap;

public final class App {
    public static void main(String[] args) throws Exception{
        new HttpServerBootstrap().start();
    }
}
