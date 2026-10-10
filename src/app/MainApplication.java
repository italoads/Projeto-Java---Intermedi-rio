package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlLocation = getClass().getResource("/view/Principal.fxml");

            if (fxmlLocation == null) {
                System.err.println("ERRO: O arquivo '/view/Principal.fxml' não foi encontrado!");
                System.err.println("Certifique-se de que a pasta 'view' está no diretório 'resources' do projeto.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            stage.setTitle("TaskManager - Gerenciador de Tarefas");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(500);
            stage.show();

        } catch (Exception e) {
            System.err.println("Erro ao carregar a interface gráfica:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}