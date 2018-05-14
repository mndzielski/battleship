package pl.edu.wat.wcy.isi.pz.battleship.rest;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.wat.wcy.isi.pz.battleship.common.Handler;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Data
public class GithubClient {

    private final ExecutorService executorService;
    private final String JSON_URL = ProgramSettings.getInstance().property("json.url");

    private Task<GithubUser> fetchUser = new Task<GithubUser>() {
        @Override
        protected GithubUser call() throws IOException {
            return new Gson().fromJson(readUrl(JSON_URL), GithubUser.class);
        }
    };

    public GithubClient(Handler<GithubUser> userHandler, Handler<Image> imageHandler) {
        executorService = RootController.getInstance().getExecutorService();
        executorService.submit(fetchUser);

        fetchUser.setOnSucceeded(t -> {
            GithubUser githubUser = fetchUser.getValue();
            userHandler.handle(githubUser);

            FetchImage fetchImage = new FetchImage(githubUser.getAvatar_url());
            fetchImage.setOnSucceeded(s -> imageHandler.handle(fetchImage.getValue()));
            executorService.submit(fetchImage);
        });
    }

    @AllArgsConstructor
    private class FetchImage extends Task<Image> {
        private String imageUrl;

        @Override
        protected Image call() throws Exception {
            return new Image(imageUrl);
        }
    }

    private String readUrl(String urlString) throws IOException {
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
}
