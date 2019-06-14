package com.sq.common.feignclient;

import com.sq.common.feign.springboot.FeignClient;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@FeignClient("https://api.github.com")
public interface GitHub {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    class Contributor {
        String login;
        int contributions;

        public Contributor() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getContributions() {
            return contributions;
        }

        public void setContributions(int contributions) {
            this.contributions = contributions;
        }
    }
}
