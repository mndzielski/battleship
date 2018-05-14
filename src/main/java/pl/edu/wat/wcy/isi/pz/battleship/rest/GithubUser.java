package pl.edu.wat.wcy.isi.pz.battleship.rest;

import lombok.Data;

@Data
public class GithubUser {
    private final String login;
    private final String html_url;
    private final String avatar_url;
    private final String bio;
}
