package com.sq.common.feign;

import com.sq.common.feign.coder.FastjsonDecoder;
import com.sq.common.feign.coder.FastjsonEncoder;
import feign.Feign;

import java.util.List;

public class MyApp {
    public static void main(String... args) {
        GitHub github = Feign.builder()
                .encoder(new FastjsonEncoder())
                .decoder(new FastjsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        List<GitHub.Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (GitHub.Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
