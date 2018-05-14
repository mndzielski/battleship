package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.h2.GameResult;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatisticsMenuController extends AbstractMenuController {

    public ListView listView;

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        new GameResult().getGames(result -> {
            final ProgramSettings settings = ProgramSettings.getInstance();

            PieChart.Data win = new PieChart.Data("", result.getWinnings());
            PieChart.Data lose = new PieChart.Data("", result.getLosses());
            settings.i18n(win.nameProperty(), "statistics.win", String.valueOf(result.getWinnings()));
            settings.i18n(lose.nameProperty(), "statistics.lose", String.valueOf(result.getLosses()));

            PieChart pieChart = new PieChart();
            pieChart.setData(FXCollections.observableArrayList(win, lose));

            settings.i18n(pieChart.titleProperty(), "statistics.balance");
            pieChart.setPrefWidth(300);
            pieChart.setStartAngle(0);
            pieChart.setLabelsVisible(true);
            pieChart.setClockwise(false);
            pieChart.setLabelsVisible(false);
            listView.setItems(FXCollections.observableArrayList(pieChart));
        }, "true");
    }

    @FXML
    private void handleMainMenu(ActionEvent event) {
        menuController.loadMainMenu();
    }
}
