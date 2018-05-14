package pl.edu.wat.wcy.isi.pz.battleship.h2;

import javafx.concurrent.Task;
import pl.edu.wat.wcy.isi.pz.battleship.common.Handler;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class GameResult {
    private final ExecutorService executorService;

    public GameResult() {
        executorService = RootController.getInstance().getExecutorService();
    }

    public void save(Object obj) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(ProgramSettings.getInstance().property("database.name"));
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(obj);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return null;
            }
        };

        executorService.submit(task);
    }


    public void getGames(Handler<Result> handler, String tr) {

        Task<Result> task = new Task<Result>() {
            @Override
            protected Result call() throws Exception {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(ProgramSettings.getInstance().property("database.name"));
                EntityManager em = emf.createEntityManager();
                Result result = new Result();
                result.setWinnings((Long) em.createQuery("select count (g) from Game g where g.win = " + tr).getSingleResult());
                result.setLosses((Long) em.createQuery("select count (g) from Game g where g.win = false").getSingleResult());
                em.close();
                emf.close();
                return result;
            }
        };

        task.setOnSucceeded(event -> handler.handle(task.getValue()));
        executorService.submit(task);
    }
}
